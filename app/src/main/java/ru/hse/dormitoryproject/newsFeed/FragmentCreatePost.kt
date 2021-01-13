package ru.hse.dormitoryproject.newsFeed

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.hse.dormitoryproject.Utils.PostObject
import ru.hse.dormitoryproject.R
import ru.hse.dormitoryproject.Utils.DataBase
import java.text.SimpleDateFormat
import java.util.*


class FragmentCreatePost() : Fragment() {
    private val PICK_IMAGE = 0
    private var uri: Uri? = null

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
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            val author = "Alex"


            if (tittleText.isEmpty()) {
                Toast.makeText(it.context, TOAST_NULL_TITTLE, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contentText.isEmpty()) {
                Toast.makeText(it.context, TOAST_NULL_CONTENT, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = UUID.randomUUID().toString()

            val post =
                PostObject(id, tittleText, "descriptor", contentText, currentDate, false, author)
            DataBase.uploadImage(uri, it.context, post, NAME_COLLECTION)
            findNavController().navigate(R.id.feedFragment)
        }


        view.findViewById<ImageView>(R.id.choose_pic_img)
            .setOnClickListener { getImage(view.context) }

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


    private fun getImage(context: Context) {
        try {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, PICK_IMAGE)
        } catch (e: Exception) {
            Log.e("EXC", e.message ?: "")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == PICK_IMAGE) {
                view?.findViewById<ImageView>(R.id.choose_pic_img)?.setImageURI(data?.data)
                uri = data?.data
            }
        } catch (e: Exception) {
            Log.e("EXC", e.message ?: "")
        }
    }
}
