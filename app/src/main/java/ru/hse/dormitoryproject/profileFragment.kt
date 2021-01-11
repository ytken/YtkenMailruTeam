package ru.hse.dormitoryproject

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.hse.dormitoryproject.Utils.DataBase
import ru.hse.dormitoryproject.Utils.UserObject
import ru.hse.dormitoryproject.friend_data.FriendItem

class profileFragment : Fragment() {

    var name : String = "Log"
    var surname : String = "Login"
    var currencyNumber : Int = 0
    var rateNumber : Int = 0
    var linkVK : String = ""

    lateinit var textName: TextView
    lateinit var textSurname: TextView
    lateinit var textCurr: TextView
    lateinit var textRate: TextView
    lateinit var textVK: TextView
    lateinit var imageProf: ImageView
    lateinit var btnEdit:Button

    lateinit var buttonFriends: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_profile, container, false)
        setHasOptionsMenu(true)

        textName = v.findViewById(R.id.textViewName)
        textSurname = v.findViewById(R.id.textViewSurname)
        textCurr = v.findViewById(R.id.textViewCurrency)
        textRate = v.findViewById(R.id.textViewRate)
        textVK = v.findViewById(R.id.textViewVKLink)
        imageProf = v.findViewById(R.id.imageViewProfile)
        btnEdit = v.findViewById(R.id.btn_edit)

        DataBase.getCurrentUser()?.get()?.addOnSuccessListener {it->
           val user = it.toObject(UserObject::class.java)
            textName.text = user?.name
            textSurname.text = user?.surname
            textCurr.text = user?.countCoins.toString()
            textVK.text = user?.vk.toString()
            textRate.text = "1"
            val uri:Uri? = user?.photoProfile?.toUri()
            Picasso.get().load(uri).into(imageProf)
        }

        btnEdit.setOnClickListener {
            val bundle:Bundle = Bundle()
            bundle.putCharSequence("name",textName.text)
            bundle.putCharSequence("surname",textSurname.text)
            bundle.putCharSequence("vk",textVK.text)

            findNavController().navigate(R.id.EditProfileFragment,bundle)
        }

        /*buttonFriends = v.findViewById(R.id.button_login_friends)
        buttonFriends.setOnClickListener{
            val searchFragment = SearchProfilesFragment()
            Log.d("my", "Button clicked")
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, searchFragment).addToBackStack(null).commit()
        }*/

        return v
    }

    companion object {
        fun newInstance(friendItem: FriendItem) = profileFragment().apply {
            arguments = Bundle().apply {
                name = friendItem.name
                surname = friendItem.surname
                currencyNumber = friendItem.currency
                rateNumber = friendItem.rate
                linkVK = friendItem.VK
            }
        }
    }

}