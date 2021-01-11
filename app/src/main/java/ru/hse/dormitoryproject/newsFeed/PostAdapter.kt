package ru.hse.dormitoryproject.newsFeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.Utils.PostObject
import ru.hse.dormitoryproject.R

class PostAdapter(private val data: ArrayList<PostObject>, private val fManager : FragmentManager?,
                  private val funListener : (PostObject, FragmentManager?, ()->Unit)->Unit) :
    RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.feed_post, parent, false)

        return PostViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item =data[position]
        holder.itemView.setOnClickListener { funListener(item, fManager, ::notifyDataSetChanged) } // Нет записи на сревер
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}