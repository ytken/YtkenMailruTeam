package ru.hse.dormitoryproject.newsFeed

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.PostObject
import ru.hse.dormitoryproject.R

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(newObject : PostObject) {
        itemView.findViewById<TextView>(R.id.post_title_name).text = newObject.title
        itemView.findViewById<TextView>(R.id.post_desc_text).text = newObject.content
        itemView.findViewById<TextView>(R.id.post_date_text).text = newObject.dateOfPublish;
        itemView.findViewById<TextView>(R.id.post_author_name).text = newObject.author;
        val btn = itemView.findViewById<ImageButton>(R.id.post_add_to_fav)
        Log.d("ADD_POST", "is fav = "+newObject.isFavorite)

        if(newObject.isFavorite!!){
            btn.setBackgroundResource(R.drawable.ic_baseline_star_chosen);
        }
        else{
            btn.setBackgroundResource(R.drawable.ic_baseline_star_not_chosen);
        }

        btn.setOnClickListener {
            if(newObject.isFavorite!!){
                btn.setBackgroundResource(R.drawable.ic_baseline_star_not_chosen);
                newObject.isFavorite=false;
            }
            else{
                btn.setBackgroundResource(R.drawable.ic_baseline_star_chosen);
                newObject.isFavorite=true;
            }
        }

        // + Save/remove to local DB

    }
}