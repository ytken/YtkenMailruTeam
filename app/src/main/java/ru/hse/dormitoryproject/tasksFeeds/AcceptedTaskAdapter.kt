package ru.hse.dormitoryproject.tasksFeeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R

class AcceptedTaskAdapter(private val data: ArrayList<TaskObject>, private val fManager : FragmentManager?,
                          private val display : (TaskObject, FragmentManager?, ()->Unit)->Unit) :
    RecyclerView.Adapter<AcceptedTaskViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcceptedTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.feed_task_accepted, parent, false)

        return AcceptedTaskViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AcceptedTaskViewHolder, position: Int) {
        val item =data[position]
        holder.itemView.setOnClickListener { display(item, fManager, ::notifyDataSetChanged)} // Нет записи на сревер
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}