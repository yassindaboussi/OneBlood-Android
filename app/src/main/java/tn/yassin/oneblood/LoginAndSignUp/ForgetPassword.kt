package tn.yassin.oneblood.LoginAndSignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Util.CustomToast

class ForgetPassword : AppCompatActivity() {
    private lateinit var btnContinue: Button
    private lateinit var emailLost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgetpassword)

        btnContinue= findViewById(R.id.btnContinue)
        emailLost = findViewById(R.id.emailLost)
        btnContinue.setOnClickListener{
            //Todo Go TO ANOTHER PAGE TO PUT THE KEY AND RESTOR THE ACCOUNT
            if(emailLost.text.toString().isEmailValid()) {
                CustomToast(this@ForgetPassword, "Check Your Email Now!","GREEN").show()
                //val intent = Intent(this, Login::class.java).apply {}
                //startActivity(intent)
            }
       }
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

}