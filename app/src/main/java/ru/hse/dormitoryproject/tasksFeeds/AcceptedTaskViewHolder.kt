package ru.hse.dormitoryproject.tasksFeeds

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase

class AcceptedTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(newObject : TaskObject) {
        DataBase.getNameById(newObject.employee){
            itemView.findViewById<TextView>(R.id.feed_task_accepted_employee_name).text = it
        }
        itemView.findViewById<TextView>(R.id.feed_task_deadline).text = newObject.deadline

        val stateView = itemView.findViewById<TextView>(R.id.feed_task_accepted_is_done)
        if(newObject.checkIt() == TaskObject.Status.READY){
            stateView.text = "Готово"
            stateView.setTextColor(Color.parseColor("#00C724"));
            DataBase.loadPhotoIntoViewByUserId(newObject.employee, itemView.findViewById(R.id.feed_task_accepted_employee_prof_pic))
        }
        else if(newObject.checkIt() == TaskObject.Status.IN_PROGRESS){
            stateView.text = "Выполняется"
            stateView.setTextColor(Color.parseColor("#C70003"));
            DataBase.loadPhotoIntoViewByUserId(newObject.employee, itemView.findViewById(R.id.feed_task_accepted_employee_prof_pic))
        }
        else{
            stateView.text = "Ожидает исполнителя"
            stateView.setTextColor(Color.parseColor("#636363"));
        }

        itemView.findViewById<TextView>(R.id.feed_task_accepted_title).text = newObject.title
    }
}