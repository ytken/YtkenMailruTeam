package ru.hse.dormitoryproject.newsFeed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ru.hse.dormitoryproject.PostObject
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase
import java.text.SimpleDateFormat
import java.util.*


class FragmentCreatePost(private val updateNewsFeed:()->Unit) : DialogFragment() {


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.create_feed_post, container, false)


        val tittle = view.findViewById<EditText>(R.id.text_tittle)
        val content = view.findViewById<EditText>(R.id.text_content)
        val btnPush = view.findViewById<Button>(R.id.button_push)

        btnPush.setOnClickListener {

            val tittleText = tittle.text.toString()
            val contentText = content.text.toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val author = "Alex"

            if (tittleText.isEmpty()) {
                Toast.makeText(context, TOAST_NULL_TITTLE, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contentText.isEmpty()) {
                Toast.makeText(context, TOAST_NULL_CONTENT, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val post = PostObject(tittleText, "descriptor", contentText, currentDate, false, author)
            DataBase.writeToBase(context, post, NAME_COLLECTION)
            this.dismiss()
            updateNewsFeed.invoke()
        }


        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    companion object {
        private const val NAME_COLLECTION = "PageWork"
        private const val TOAST_NULL_TITTLE = "Please enter the title before clicking the button"
        private const val TOAST_NULL_CONTENT = "Please enter the content before clicking the button"
    }


}