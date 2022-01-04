package tn.yassin.oneblood

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.activity_auto_login.*
import kotlinx.android.synthetic.main.custom_progress_bar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import tn.yassin.oneblood.LoginAndSignUp.Login
import tn.yassin.oneblood.Retrofit.Loginresponse
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit
import tn.yassin.oneblood.Util.CustomToast

class AutoLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_login)
        //progressbar_Autologin= findViewById(R.id.progressbar_Autologin)
       // showProgressBar()
        llProgressBar.visibility = View.VISIBLE
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val RememberEmail = sharedPreference.getString("RememberEmail", null)
        val RememberPassword = sharedPreference.getString("RememberPassword", null)
        val nameUser = sharedPreference.getString("name", null)
        llProgressBar.NameAutoLogin.text = nameUser
        if ((RememberEmail != null && RememberPassword != null) && (RememberEmail.isNotEmpty() && RememberPassword.isNotEmpty())) {
            //ServiceLogin(RememberEmail, RememberPassword)
            GoToHome(this@AutoLogin)
        } else {
            GoToLogin(this@AutoLogin)
            llProgressBar.visibility = View.GONE
        }
    }

    @SuppressLint("RestrictedApi")
    fun ServiceLogin(email:String, password:String) {
        // sessionManager = SessionManager(this)
        // Create Retrofit
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)
        val User = Loginresponse()
        User.setEmail(email)
        User.setPassword(password)

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            try {
                val response = service.Login2(User)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        println("Token =============>>>>>>>>>  " + response.body()?.getToken())
                        println("ID =============>>>>>>>>>  " + response.body()?.getID())
                        println("Name =============>>>>>>>>>  " + response.body()?.getName())
                        val preferences = PreferenceManager.getDefaultSharedPreferences(this@AutoLogin)
                        val editor = preferences.edit()
                        editor.putString("id", response.body()?.getID())
                        editor.putString("name", response.body()?.getName())
                        editor.putString("email", response.body()?.getEmail())
                        editor.putString("blood", response.body()?.getBlood())
                        editor.putString("age", response.body()?.getAge())
                        editor.putString("weight", response.body()?.getWeight())
                        editor.putString("adress", response.body()?.getAdresse())
                        editor.putString("phone", response.body()?.getPhone())
                        editor.putString("usertype", response.body()?.getUserType())
                        editor.putString("avatar", response.body()?.getAvatar())

                        editor.apply()  //Save Data

                        CustomToast(this@AutoLogin, "Login Successful!","GREEN").show()
                        GoToHome(this@AutoLogin) //GoTo Page Home

                    } else {
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        println("Message :" + response.errorBody()?.string())
                        CustomToast(this@AutoLogin, "Email or password is incorrect!","RED").show()
                        GoToLogin(this@AutoLogin)
                        val preferences = PreferenceManager.getDefaultSharedPreferences(this@AutoLogin)
                        val editor = preferences.edit()
                        editor.putString("RememberEmail", "")
                        editor.apply()  //Save Data
                    }
                }
            } catch (e: Exception)
            {
                println(e.printStackTrace())
                println("Error")

                getActivity(this@AutoLogin)?.runOnUiThread(java.lang.Runnable {
                    CustomToast(this@AutoLogin, "Sorry, Our Server Is Down!","RED").show()
                    GoToLogin(this@AutoLogin)
                })
            }
        }
    }

    fun GoToHome(context: Context) {
        val intent = Intent(context, Home::class.java)
        context.startActivity(intent)
        finish()
    }

    fun GoToLogin(context: Context) {
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
        finish()
    }


}