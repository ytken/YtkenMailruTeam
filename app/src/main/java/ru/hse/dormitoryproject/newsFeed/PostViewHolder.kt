package ru.hse.dormitoryproject.newsFeed

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.hse.dormitoryproject.Utils.PostObject
import ru.hse.dormitoryproject.R
import java.lang.IllegalArgumentException

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

        val picture = itemView.findViewById<ImageView>(R.id.post_pic)
        try {
            Picasso.get().load(newObject.storageRef)
                .into(picture)
        }
        catch (e : IllegalArgumentException){
            Toast.makeText(itemView.context, "Невозможно загрузить изображение", Toast.LENGTH_SHORT).show()
            picture.setImageResource(R.drawable.your_advertisement)
        }

        // + Save/remove to local DB

    }
}