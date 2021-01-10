package ru.hse.dormitoryproject

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.hse.dormitoryproject.friend_data.FriendItem
import ru.hse.dormitoryproject.friend_data.FriendRepository

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

        textName.text = name
        textSurname.text = surname
        textCurr.text = currencyNumber.toString()
        textVK.text = linkVK
        textRate.text = rateNumber.toString()
        //imageProf.setImageResource(R.drawable.nusha) // Этого ресурса нет, я не знаю, где его взять, пока что заменио на это:
        imageProf.setImageResource(R.drawable.your_advertisement)

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