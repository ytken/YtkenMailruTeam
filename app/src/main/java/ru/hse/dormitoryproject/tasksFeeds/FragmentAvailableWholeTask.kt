package ru.hse.dormitoryproject.tasksFeeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase

class FragmentAvailableWholeTask(
    private val currentTask: TaskObject,
    private val notifier: () -> Unit
) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.feed_available_task_whole, container, false)

        //Set user pic
        view?.findViewById<TextView>(R.id.post_author_name)?.text = currentTask.author
        view?.findViewById<TextView>(R.id.feed_task_available_disc_title)?.text = currentTask.title
        view?.findViewById<TextView>(R.id.feed_task_available_disc_text)?.text =
            currentTask.description
        view?.findViewById<TextView>(R.id.feed_task_reward)?.text = currentTask.reward.toString()
        view?.findViewById<TextView>(R.id.feed_task_available_deadline)?.text = currentTask.deadline

        view?.findViewById<Button>(R.id.feed_task_take_btn)?.setOnClickListener {
            DataBase.takeTaskByUser(currentTask)
            notifier()
            this.dismiss()
        }

        return view
    }
}