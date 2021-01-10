package ru.hse.dormitoryproject.tasksFeeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.PostObject
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.newsFeed.PostViewHolder

class AvailableTaskAdapter(private val data: ArrayList<TaskObject>, private val fManager : FragmentManager?,
                           private val display : (TaskObject, FragmentManager?, ()->Unit)->Unit) :
    RecyclerView.Adapter<AvailableTaskViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.feed_task_available, parent, false)

        return AvailableTaskViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AvailableTaskViewHolder, position: Int) {
        val item =data[position]
        holder.itemView.setOnClickListener { display(item, fManager, ::notifyDataSetChanged)} // Нет записи на сревер
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}