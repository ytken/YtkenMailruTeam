package ru.hse.dormitoryproject

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class profileFragment : Fragment() {

    var currencyNumber : Int = 0
    var rateNumber : Int = 0
    var linkVK : String = ""

    lateinit var textCurr: TextView
    lateinit var textRate: TextView
    lateinit var textVK: TextView
    lateinit var imageProf: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyNumber = 10
        rateNumber = 3
        linkVK = "http"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_profile, container, false)
        setHasOptionsMenu(true)

        textCurr = v.findViewById(R.id.textViewCurrency)
        textRate = v.findViewById(R.id.textViewRate)
        textVK = v.findViewById(R.id.textViewVKLink)
        imageProf = v.findViewById(R.id.imageViewProfile)

        textCurr.text = currencyNumber.toString()
        textVK.text = linkVK
        textRate.text = rateNumber.toString()
        imageProf.setImageResource(R.drawable.nusha)

        return v
    }

}