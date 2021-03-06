package ru.hse.dormitoryproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ru.hse.dormitoryproject.Utils.DataBase


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private val TAG = "MainActivity"
    private val RC_SIGN_IN = 1

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    fun newIntent(context: Context?): Intent? {
        return Intent(context, MainActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DormitoryProject_Beta)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_selection_sign_in)

        auth = Firebase.auth
        val signInButton = findViewById<SignInButton>(R.id.btn_sign_in_google)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener(this)
        findViewById<Button>(R.id.btn_sign_in_or_reg_email).setOnClickListener(this)


        //google sign in
        // Configure Google Sign In
        //запрос пользовательских данных
        //GoogleSignInOptions для запроса идентификатора пользователя и базовой информации профиля
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_web_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)


    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        //Пользователь был ранее авторизован
        if (currentUser != null) {

                setContentView(R.layout.activity_main)
                val bottomNavigationView =
                    findViewById<BottomNavigationView>(ru.hse.dormitoryproject.R.id.bottom_navigation)
                val navController = findNavController(ru.hse.dormitoryproject.R.id.fragment)
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        ru.hse.dormitoryproject.R.id.feedFragment,
                        ru.hse.dormitoryproject.R.id.tasksFragment,
                        ru.hse.dormitoryproject.R.id.favouritesFragment,
                        ru.hse.dormitoryproject.R.id.profileFragment
                    )
                )
                setupActionBarWithNavController(navController, appBarConfiguration)
                bottomNavigationView.setupWithNavController(navController)

        }
        // Пользоавтель еще не авторизовался
        else {


        }


    }


    override fun onClick(it: View) {
        if (it.id == R.id.btn_sign_in_google) {
            signInGoogle()
        } else if (it.id == R.id.btn_sign_in_or_reg_email) {

            val fragmentChosen = Chosen(::regEmail,::signInEmail)
            fragmentChosen.show(supportFragmentManager, "Show_Email")

        }

    }

    private fun signInEmail(email:String,password:String){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    // ...
                }

                // ...
            }
    }

    private fun regEmail(email:String,password:String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    DataBase.createCurrentUser(this,::updateUI)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
                // ...
            }
    }


    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }

        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser



                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    Toast.makeText(applicationContext, "Authentication Failed.", Toast.LENGTH_SHORT)
                        .show()
                    updateUI(null)
                }
            }
    }


}