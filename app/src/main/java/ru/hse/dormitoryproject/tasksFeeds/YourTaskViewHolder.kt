package ru.hse.dormitoryproject.tasksFeeds

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R

class YourTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(newObject : TaskObject) {
        itemView.findViewById<TextView>(R.id.feed_task_your_employer_name).text = newObject.author
        itemView.findViewById<TextView>(R.id.feed_task_your_title).text = newObject.title
        itemView.findViewById<TextView>(R.id.feed_task_your_text).text = newObject.description
        itemView.findViewById<TextView>(R.id.feed_task_deadline).text=newObject.deadline

//        btn.setOnClickListener {
//            // show more info
//        }
    }
}