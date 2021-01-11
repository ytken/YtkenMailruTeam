package ru.hse.dormitoryproject.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.Utils.PostObject
import ru.hse.dormitoryproject.R

class FavPostAdapter(private var data: ArrayList<PostObject>, private val fManager : FragmentManager?,
                     private val funListener : (PostObject, FragmentManager?, ()->Unit)->Unit) :
    RecyclerView.Adapter<FavPostViewHolder>() {
    lateinit var parent: ViewGroup;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.feed_post, parent, false)

        this.parent = parent;

        return FavPostViewHolder(layout)
    }

    override fun onBindViewHolder(holder: FavPostViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.setOnClickListener { funListener(item, fManager, ::notifyDataSetChanged) } // Нет записи на сревер
        holder.bind(item) { it -> removeItem(it) }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun removeItem(removedObj: PostObject) {
        val position = data.indexOf(removedObj)
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size);
    }
}