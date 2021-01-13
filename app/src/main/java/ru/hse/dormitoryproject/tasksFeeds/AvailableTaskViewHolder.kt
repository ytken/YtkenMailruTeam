package ru.hse.dormitoryproject.tasksFeeds

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase

class AvailableTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(newObject : TaskObject) {
        DataBase.getNameById(newObject.author){
            itemView.findViewById<TextView>(R.id.feed_task_available_employer_name).text = it
        }
        itemView.findViewById<TextView>(R.id.feed_task_available_disc_text).text = newObject.description
        itemView.findViewById<TextView>(R.id.feed_task_available_reward_sum).text = newObject.reward.toString()
        itemView.findViewById<TextView>(R.id.feed_task_available_disc_title).text = newObject.title
        itemView.findViewById<TextView>(R.id.feed_task_deadline).text=newObject.deadline
    }
}