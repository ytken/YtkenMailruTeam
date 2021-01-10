package ru.hse.dormitoryproject.newsFeed

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import ru.hse.dormitoryproject.PostObject
import ru.hse.dormitoryproject.R
import java.lang.IllegalArgumentException

class FragmentWholePost(private val currentPost : PostObject, private val notifier : ()->Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.feed_post_whole, container, false)

        view?.findViewById<TextView>(R.id.post_author_name)?.text=currentPost.author
        view?.findViewById<TextView>(R.id.post_date_text)?.text=currentPost.dateOfPublish

        val star = view?.findViewById<ImageView>(R.id.post_add_to_fav)
        if(currentPost.isFavorite!!) {
            star?.setBackgroundResource(R.drawable.ic_baseline_star_chosen)
        }

        view?.findViewById<ImageView>(R.id.post_add_to_fav)?.setOnClickListener {
            if(currentPost.isFavorite!!){
                star?.setBackgroundResource(R.drawable.ic_baseline_star_not_chosen);
                currentPost.isFavorite=false;
            }
            else{
                star?.setBackgroundResource(R.drawable.ic_baseline_star_chosen);
                currentPost.isFavorite=true;
            }
            notifier()
        }
        view?.findViewById<TextView>(R.id.post_title_name)?.text=currentPost.title
        view?.findViewById<TextView>(R.id.post_desc_text)?.text=currentPost.content

        val picture = view?.findViewById<ImageView>(R.id.post_pic)
        try {
            Picasso.get().load(currentPost.storageRef)
                .into(picture)
        }
        catch (e : IllegalArgumentException){
            Toast.makeText(view?.context, "Невозможно загрузить изображение", Toast.LENGTH_SHORT).show()
            picture?.setImageResource(R.drawable.your_advertisement)
        }

        //if(hasPhoto) {
        //add photo
        //}
        //else {
        //view?.findViewById<ImageView>(R.id.post_pic)?.isVisible=false
        //}

        return view
    }

}