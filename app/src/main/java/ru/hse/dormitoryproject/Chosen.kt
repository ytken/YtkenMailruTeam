package ru.hse.dormitoryproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import kotlin.reflect.KFunction2


class Chosen(
    private val regEmail: KFunction2<String, String, Unit>,
    private val signInEmail: KFunction2<String, String, Unit>
) : DialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_sign_in_email, container, false)

        val disFun = {dismiss()}

        view?.findViewById<Button>(R.id.btn_email_registration)?.setOnClickListener {

            val fragRegEmail = RegEmail(regEmail,disFun)
            fragRegEmail.show(parentFragmentManager, "Show_Email_REG")
        }

        view?.findViewById<Button>(R.id.btn_email_sign_in)?.setOnClickListener {
            val fragSignInEmail = SignInEmail(signInEmail,disFun)
            fragSignInEmail.show(parentFragmentManager, "Show_Email_SIGNIN")
        }

        return view
    }
}
