package ru.hse.dormitoryproject.tasksFeeds

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase

class FragmentAcceptedWholeTask(
    private val currentTask: TaskObject,
    private val notifier: (Boolean) -> Unit
) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.feed_accepted_task_whole, container, false)

        val employee = view?.findViewById<TextView>(R.id.post_employee_name)
        val state = view?.findViewById<TextView>(R.id.feed_task_accepted_is_done)

        if(currentTask.employee == null){
            employee?.text = " - "
            state?.text = "Ожидает исполнителя"
            state?.setTextColor(Color.parseColor("#636363"));
        }
        else{
            DataBase.getNameById(currentTask.employee){
                employee?.text = it
            }

            if(currentTask.checkIt() == TaskObject.Status.READY){
                state?.text = "Готово"
                state?.setTextColor(Color.parseColor("#00C724"));

                view?.findViewById<LinearLayout>(R.id.layout_action_bar)?.visibility = View.VISIBLE

                view?.findViewById<Button>(R.id.send_report_btn)?.setOnClickListener {
                    // Кинуть репорт на исполнителя
                    notifier(true) // Удалить пост из списка постов/изменить значение в базе
                    this.dismiss()
                }

                view?.findViewById<Button>(R.id.send_reward_btn)?.setOnClickListener {
                    notifier(false) // Удалить пост из списка постов/изменить значение в базе
                    this.dismiss()
                }

                DataBase.loadPhotoIntoViewByUserId(currentTask.employee, view.findViewById(R.id.feed_task_accepted_employee_prof_pic))
            }
            else if(currentTask.checkIt() == TaskObject.Status.IN_PROGRESS){
                state?.text = "Выполняется"
                state?.setTextColor(Color.parseColor("#C70003"));
                DataBase.loadPhotoIntoViewByUserId(currentTask.employee, view.findViewById(R.id.feed_task_accepted_employee_prof_pic))
            }
        }

        view?.findViewById<TextView>(R.id.feed_task_accepted_disc_title)?.text = currentTask.title

        return view
    }
}