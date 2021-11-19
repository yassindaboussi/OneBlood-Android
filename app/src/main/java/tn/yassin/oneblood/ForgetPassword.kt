package tn.yassin.oneblood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ForgetPassword : AppCompatActivity() {
    private lateinit var btnContinue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgetpassword)

        btnContinue= findViewById(R.id.btnContinue)
        btnContinue.setOnClickListener{
           val  intent  = Intent(this,Login::class.java).apply {
           }
            startActivity(intent)
       }
    }

}