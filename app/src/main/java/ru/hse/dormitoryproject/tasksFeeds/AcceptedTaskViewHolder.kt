package ru.hse.dormitoryproject.tasksFeeds

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R

class AcceptedTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(newObject : TaskObject) {
        itemView.findViewById<TextView>(R.id.feed_task_accepted_employee_name).text = newObject.author
        itemView.findViewById<TextView>(R.id.feed_task_deadline).text = newObject.deadline

        val stateView = itemView.findViewById<TextView>(R.id.feed_task_accepted_is_done)
        if(newObject.checkIt() == TaskObject.Status.READY){
            stateView.text = "Готово"
            stateView.setTextColor(Color.parseColor("#00C724"));
        }
        else if(newObject.checkIt() == TaskObject.Status.IN_PROGRESS){
            stateView.text = "Выполняется"
            stateView.setTextColor(Color.parseColor("#C70003"));
        }
        else{
            stateView.text = "Ожидает исполнителя"
            stateView.setTextColor(Color.parseColor("#636363"));
        }

        itemView.findViewById<TextView>(R.id.feed_task_accepted_title).text = newObject.title
    }
}