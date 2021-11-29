package tn.yassin.oneblood.LoginAndSignUp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import tn.yassin.oneblood.R
import retrofit2.Retrofit
import tn.yassin.oneblood.Home
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit


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
            Checkinputs(EmailLogin, PasswordLogin)
            if ((EmailLogin.text.toString().isEmailValid()) && !(PasswordLogin.text.isEmpty()))
            {
/*
                val intent = Intent(this, Home::class.java).apply {}
                startActivity(intent)
*/
                ServiceLogin(EmailLogin.text.toString(),PasswordLogin.text.toString())

            }
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
        ) {
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
        } else {
            EmailLogin.setError(null)
        }
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun ServiceLogin(email:String,password:String) {
        // Create Retrofit
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.Login(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    Log.d("Pretty Printed JSON :", prettyJson)

                    GoToHome(this@Login) //GoTo Page Home

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :"+response.errorBody()?.string())
                }
            }
        }
    }

    fun GoToHome(context: Context) {
        val intent = Intent(context, Home::class.java)
        context.startActivity(intent)
        finish()
    }
}