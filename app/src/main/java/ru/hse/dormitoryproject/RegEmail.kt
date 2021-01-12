package ru.hse.dormitoryproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlin.reflect.KFunction2

class RegEmail(private val regEmail: KFunction2<String, String, Unit>,
               private val disFun: () -> Unit) : DialogFragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPass: EditText
    private lateinit var editTextConfPass: EditText
    private lateinit var btnDone: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_reg_email, container, false)

        editTextEmail = v.findViewById(R.id.editText_email_reg)
        editTextPass = v.findViewById(R.id.editText_password_reg)
        editTextConfPass = v.findViewById(R.id.editText_conf_password_reg)
        btnDone = v.findViewById(R.id.btn_done_reg_email)

        btnDone.setOnClickListener {
            val emailText = editTextEmail.text.toString()
            val passText = editTextPass.text.toString()
            val confPassText = editTextConfPass.text.toString()


            if (emailText.isEmpty()) {
                Toast.makeText(it.context,
                    "NULL EMAIL",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (passText.isEmpty()) {
                Toast.makeText(
                    it.context,
                    "NULL PASS",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (confPassText.isEmpty()) {
                Toast.makeText(
                    it.context,
                    "NULL PASS CONF",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if(passText!=confPassText){
                Toast.makeText(
                    it.context,
                    "Password != Conf Password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            dismiss()
            disFun.invoke()
            regEmail.invoke(emailText,passText)

        }


        return v
    }

}