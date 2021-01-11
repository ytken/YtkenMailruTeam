package ru.hse.dormitoryproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.hse.dormitoryproject.Utils.DataBase

class FragmentEditProfile : Fragment() {


    lateinit var textName: TextView
    lateinit var textSurname: TextView
    lateinit var textVK: TextView
    lateinit var btnComplete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.profile_edit, container, false)
        setHasOptionsMenu(true)

        textName = v.findViewById(R.id.editTextName)
        textSurname = v.findViewById(R.id.editTextSurname)
        textVK = v.findViewById(R.id.editTextVK)
        btnComplete = v.findViewById(R.id.btnComplete)

        textName.text = arguments?.getCharSequence("name")
        textSurname.text = arguments?.getCharSequence("surname")
        textVK.text = arguments?.getCharSequence("vk")


        btnComplete.setOnClickListener {
            DataBase.getCurrentUser()?.update("name", textName.text.toString())
            DataBase.getCurrentUser()?.update("surname", textSurname.text.toString())
            DataBase.getCurrentUser()?.update("vk", textVK.text.toString())?.addOnSuccessListener {
                findNavController().navigate(R.id.profileFragment)
            }
        }

        return v
    }


}