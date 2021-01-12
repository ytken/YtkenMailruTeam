package ru.hse.dormitoryproject.tasksFeeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase

class YourTaskAdapter(
    private val data: ArrayList<TaskObject>, private val fManager: FragmentManager?,
    private val display: (TaskObject, FragmentManager?, () -> Unit) -> Unit
) :
    RecyclerView.Adapter<YourTaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.feed_task_your, parent, false)

        return YourTaskViewHolder(layout, this::notifyDataSetChanged)
    }

    override fun onBindViewHolder(holder: YourTaskViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.setOnClickListener {
            display(
                item,
                fManager
            ) { DataBase.tellAboutTaskCompletion(item); notifyDataSetChanged() }
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}