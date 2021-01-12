package ru.hse.dormitoryproject.favourites

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.hse.dormitoryproject.PostObject
import ru.hse.dormitoryproject.R

class FavPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(newObject: PostObject, changer: (PostObject) -> Unit) {
        itemView.findViewById<TextView>(R.id.post_title_name).text = newObject.title
        itemView.findViewById<TextView>(R.id.post_desc_text).text = newObject.content
        itemView.findViewById<TextView>(R.id.post_date_text).text = newObject.dateOfPublish;
        itemView.findViewById<TextView>(R.id.post_author_name).text = newObject.author;
        val btn = itemView.findViewById<ImageButton>(R.id.post_add_to_fav)




        if (newObject.isFavorite!!) {
            btn.setBackgroundResource(R.drawable.ic_baseline_star_chosen);
        } else {
            btn.setBackgroundResource(R.drawable.ic_baseline_star_not_chosen);
        }

        btn.setOnClickListener {
            if (newObject.isFavorite!!) {
                btn.setBackgroundResource(R.drawable.ic_baseline_star_not_chosen);
                newObject.isFavorite = false;
            } else {
                btn.setBackgroundResource(R.drawable.ic_baseline_star_chosen);
                newObject.isFavorite = true;
            }
            changer(newObject)
        }

        // + Save/remove to local DB

    }
}