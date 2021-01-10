package ru.hse.dormitoryproject.tasksFeeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.type.DateTime
import ru.hse.dormitoryproject.R
import java.lang.Exception
import java.time.LocalDate
import java.util.*

class FragmentNewTask : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_task, container, false)

        view?.findViewById<Button>(R.id.new_task_btn_done)?.setOnClickListener {
            createNewTask(view)
        }

        return view
    }

    private fun createNewTask(view: View?) {
        val title = view?.findViewById<EditText>(R.id.new_task_title)?.text.toString()
        val description = view?.findViewById<EditText>(R.id.new_task_description)?.text.toString()
        val reward = view?.findViewById<EditText>(R.id.new_task_reward)?.text.toString()
        val datePicker = view?.findViewById<DatePicker>(R.id.new_task_deadline)

        val date: DateTime
        val rewardVal: Int

        if (title.length <= 2) {
            Toast.makeText(context, "Заголовок слишком короткий", Toast.LENGTH_LONG).show()
            return
        }

        if (description.length <= 10) {
            Toast.makeText(context, "Пожалуйста, опишите задачу более подробно", Toast.LENGTH_LONG).show()
            return
        }

        try {
            rewardVal = reward.toInt()
        } catch (e: Exception) {
            Toast.makeText(context, "Награда должна быть целым числом!", Toast.LENGTH_LONG).show()
            return
        }
//            else if(rewardVal <=0 || rewardVal < User.cash){ // Раскомментить, когда у неас будут пользователи
//                Toast.makeText(context, "Награда должна быть положительным числом!", Toast.LENGTH_LONG).show()
//                return
//            }

        val currDate = Date()
        if (datePicker == null || (datePicker.year < LocalDate.now().year) || ((datePicker.month+1) < LocalDate.now().monthValue) || (datePicker.dayOfMonth <= LocalDate.now().dayOfMonth)) {
            Toast.makeText(
                context,
                "Крайний срок выполнения задачи должен быть в будущем! ${datePicker!!.month}",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val newTask = TaskObject(description, rewardVal, "?", "This user", TaskObject.Status.NOT_SELECTED.ordinal,
            "${datePicker.year}-${datePicker.month}-${datePicker.dayOfMonth}", title)
        // load to server
        this.dismiss()
    }
}