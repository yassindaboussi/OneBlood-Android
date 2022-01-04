package tn.yassin.oneblood.LoginAndSignUp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.coroutines.*
import tn.yassin.oneblood.R
import retrofit2.Retrofit
import tn.yassin.oneblood.Home
import tn.yassin.oneblood.Retrofit.Loginresponse
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit
import tn.yassin.oneblood.Util.CustomDialog
import tn.yassin.oneblood.Util.CustomToast
import tn.yassin.oneblood.Util.ReadyFunction


class Login : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var btnLogin: Button
    private lateinit var txtForgetPassword: TextView
    private lateinit var btnSignUp: TextView
    private lateinit var EmailLogin: TextView
    private lateinit var PasswordLogin: TextView
    private lateinit var ShowPassword: ImageView
    private lateinit var cbRememberMe:CheckBox
    private lateinit var progressbar_login:ProgressBar
    private lateinit var AllOfPageLogin:ConstraintLayout

    val ReadyFunction = ReadyFunction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        initView() //findViewById
        //RememberMeAutoLogin() //Remember Me =>>>> AutoLogin
        SetLastEmail()
        DoLogin() // Login + Check (isValide EditText)
        showPassword() //Show + Hide (Password)
        GoToSignUp() //GoTo SignUp
        GoToForgetPassword()   //GoTo ForgetPassword
        CLickToHidKeyBoard() //Hide KeyBoard
    }

    fun initView()
    {
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        txtForgetPassword = findViewById(R.id.txtForgetPassword)
        EmailLogin = findViewById(R.id.EmailLogin)
        PasswordLogin = findViewById(R.id.PasswordLogin)
        ShowPassword= findViewById(R.id.showPasswordLogin)
        cbRememberMe= findViewById(R.id.cbRememberMe)
        progressbar_login= findViewById(R.id.progressbar_login)
        AllOfPageLogin= findViewById(R.id.AllOfPageLogin)
    }

    fun GoToSignUp()
    {
        btnSignUp.setOnClickListener {
            val intent = Intent(this, conditionsignup::class.java).apply {
            }
            startActivity(intent)
        }
    }
    fun GoToForgetPassword()
    {
        txtForgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPassword::class.java).apply {
            }
            startActivity(intent)
        }
    }

/*    fun RememberMeAutoLogin()
    {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val RememberEmail = sharedPreference.getString("RememberEmail", null)
        val RememberPassword = sharedPreference.getString("RememberPassword", null)
        if((RememberEmail!=null && RememberPassword!=null)&&(RememberEmail.isNotEmpty()&&RememberPassword.isNotEmpty()))
        {
            ServiceLogin(RememberEmail,RememberPassword)
        }
    }*/

    fun SetLastEmail()
    {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val LastEmail = sharedPreference.getString("email", null)
        EmailLogin.text = LastEmail

    }

    fun showPassword()
    {
        var showingFirst = true
        ShowPassword.setOnClickListener {
            if(showingFirst == true){
                PasswordLogin.setTransformationMethod(null);//Show Password
                showingFirst = false;
            }else{
                PasswordLogin.setTransformationMethod(PasswordTransformationMethod()) //Hide Password
                showingFirst = true;
            }
        }
    }

    private fun DoLogin(){
        gettextwathcerlogin()
        btnLogin.setOnClickListener {
            if (!validateUserEmail() or !validateUserPassword()) {
                return@setOnClickListener
            } else {
                showProgressBar()
                ServiceLogin(EmailLogin.text.toString(),PasswordLogin.text.toString())
            }
        }
    }

    private fun gettextwathcerlogin() {
        EmailLogin.addTextChangedListener(emailTextWatcher)
        PasswordLogin.addTextChangedListener(passwordTextWatcher)
    }

    private val emailTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateUserEmail() }
    }
    private val passwordTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateUserPassword() }
    }
    private fun validateUserEmail(): Boolean {
        val email: String = EmailLogin.text.toString().trim()
        if (EmailLogin.text.isEmpty()) {
            EmailLogin.setError("Must Not be Empty !")
            val shake: Animation = AnimationUtils.loadAnimation(this@Login, R.anim.shake)
            EmailLogin.startAnimation(shake)
            return false
        } else if (!(EmailLogin.text.toString().isEmailValid())) {
            EmailLogin.setError("Malformed Email !")
            return false
        } else {
            EmailLogin.setError(null)
            return true
        }
        return true
    }
    private fun validateUserPassword(): Boolean {
        if (PasswordLogin.text.isEmpty()) {
            PasswordLogin.setError("Must Not be Empty !")
            val shake: Animation = AnimationUtils.loadAnimation(this@Login, R.anim.shake)
            PasswordLogin.startAnimation(shake)
            return false
        } else {
            PasswordLogin.setError(null)
            return true
        }
        return true
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun GoToHome(context: Context) {
        val intent = Intent(context, Home::class.java)
        context.startActivity(intent)
        finish()
    }

   private fun showProgressBar() {
        progressbar_login.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar_login.visibility = View.GONE
    }

    fun CLickToHidKeyBoard()
    {
        AllOfPageLogin.setOnClickListener {
            ReadyFunction.hideKeyboard(EmailLogin,this@Login)
            ReadyFunction.hideKeyboard(PasswordLogin,this@Login)
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
                    val preferences = PreferenceManager.getDefaultSharedPreferences(this@Login)
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
                    //Remember Me
                    if (cbRememberMe.isChecked) {
                        editor.putString("RememberEmail", response.body()?.getEmail())
                        editor.putString("RememberPassword", password)
                    }
                    editor.apply()  //Save Data

                    CustomToast(this@Login, "Login Successful!","GREEN").show()
                    //sessionManager.saveAuthToken(response.body()?.getToken().toString())
                    GoToHome(this@Login) //GoTo Page Home

                } else {
                    hideProgressBar() //Hide ProgressBar
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(this@Login, "Email or password is incorrect!","RED").show()
                }
            }
        } catch (e: Exception)
        {
            println(e.printStackTrace())
            println("Error")

            getActivity(this@Login)?.runOnUiThread(java.lang.Runnable {
               // val factory = LayoutInflater.from(this@Login)
             //   val view: View = factory.inflate(R.layout.dialogerror, null)
              //  Dialog.ShowErrorDialog(this@Login,view,"Our Server Is Down!")
                CustomToast(this@Login, "Sorry, Our Server Is Down!","RED").show()
                hideProgressBar()
            })
        }
    }
    }


}