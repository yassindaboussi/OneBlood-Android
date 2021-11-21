package tn.yassin.oneblood.LoginAndSignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import tn.yassin.oneblood.Home
import tn.yassin.oneblood.R

class Login : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var txtForgetPassword: TextView
    private lateinit var btnSignUp: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        txtForgetPassword = findViewById(R.id.txtForgetPassword)
        val EmailLogin: TextView = findViewById(R.id.EmailLogin)
        val PasswordLogin: TextView = findViewById(R.id.PasswordLogin)

        btnLogin.setOnClickListener {
            Checkinputs(EmailLogin,PasswordLogin)
        //  if ((EmailLogin.text.toString().isEmailValid()) && !(PasswordLogin.text.isEmpty()))//TO DO verif Email
          //  {
                val intent = Intent(this, Home::class.java).apply {
                }
                startActivity(intent)

           // }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, conditionsignup::class.java).apply {
            }
            startActivity(intent)
        }
        txtForgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPassword::class.java).apply {
            }
            startActivity(intent)
        }
    }

    fun Checkinputs(EmailLogin: TextView, PasswordLogin: TextView) {
        if ((EmailLogin.text.toString().isEmailValid()) && !(PasswordLogin.text.isEmpty())
        )
        {
            // SendData(EmailLogin,PasswordLogin)
        }
        if (PasswordLogin.text.isEmpty()) {
            PasswordLogin.setError("Must Not be Empty !")
            val shake: Animation = AnimationUtils.loadAnimation(this@Login, R.anim.shake)
            PasswordLogin.startAnimation(shake)
        } else {
            PasswordLogin.setError(null)
        }

        if (EmailLogin.text.isEmpty()) {
            EmailLogin.setError("Must Not be Empty !")
            val shake: Animation = AnimationUtils.loadAnimation(this@Login, R.anim.shake)
            EmailLogin.startAnimation(shake)
        } else if (!(EmailLogin.text.toString().isEmailValid())) {
            EmailLogin.setError("Check Your Email !")
        }
        else{ EmailLogin.setError(null)}

    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }
}