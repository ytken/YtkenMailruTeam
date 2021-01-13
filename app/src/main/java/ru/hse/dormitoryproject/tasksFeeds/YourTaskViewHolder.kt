package ru.hse.dormitoryproject.tasksFeeds

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase

class YourTaskViewHolder(itemView: View, val notify: () -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(newObject: TaskObject) {
        DataBase.getNameById(newObject.author){
            itemView.findViewById<TextView>(R.id.feed_task_your_employer_name).text = it
        }
        itemView.findViewById<TextView>(R.id.feed_task_your_title).text = newObject.title
        itemView.findViewById<TextView>(R.id.feed_task_your_text).text = newObject.description
        itemView.findViewById<TextView>(R.id.feed_task_deadline).text = newObject.deadline
        DataBase.loadPhotoIntoViewByUserId(newObject.author, itemView.findViewById(R.id.feed_task_your_employee_prof_pic))
        itemView.findViewById<Button>(R.id.feed_task_is_done_btn)
            .setOnClickListener { DataBase.tellAboutTaskCompletion(newObject);notify() }
    }
}