package tn.yassin.oneblood.LoginAndSignUp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.internal.ContextUtils
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import tn.yassin.oneblood.Home
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Retrofit.Loginresponse
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit
import tn.yassin.oneblood.Util.CustomToast
import tn.yassin.oneblood.Util.ReadyFunction

class SignUpPage2 : AppCompatActivity() {

    private lateinit var btnFinishSignUp: Button

    private lateinit var BloodA: ToggleButton
    private lateinit var BloodAB: ToggleButton
    private lateinit var BloodB: ToggleButton
    private lateinit var BloodO: ToggleButton

    private lateinit var BloodPlus: ToggleButton
    private lateinit var BloodNegative: ToggleButton

    private lateinit var checkBoxDontKnow: CheckBox

    private lateinit var inputAge: TextView
    var BloodType = ""
    var BloodSigne = ""
    var BloodGroup = ""
    var PoidsSUser=""

    private lateinit var CadreMore50KG:ImageView
    private lateinit var CadreLess50KG:ImageView

    private lateinit var textmore:TextView
    private lateinit var textless:TextView

    private lateinit var AllOfPageSingup2: ConstraintLayout

    val ReadyFunction = ReadyFunction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page2)

        initView() //init View
        ActionWeight() //Action Cadre Weight
        ChangeListener() //Listener CheckBox
        ActionSignUp() // Last Step Signup Action
        CLickToHidKeyBoard()//Hide Keyboard
    }


    fun initView()
    {
        BloodA = findViewById(R.id.BloodA)
        BloodAB = findViewById(R.id.BloodAB)
        BloodB = findViewById(R.id.BloodB)
        BloodO = findViewById(R.id.BloodO)
        BloodPlus = findViewById(R.id.BloodP)
        BloodNegative = findViewById(R.id.BloodN)
        checkBoxDontKnow =findViewById(R.id.checkBoxDontKnow)
        inputAge =findViewById(R.id.inputAge)
        CadreMore50KG=findViewById(R.id.CadreMore50KG)
        CadreLess50KG=findViewById(R.id.CadreLess50KG)
        textmore=findViewById(R.id.textmore)
        textless=findViewById(R.id.textless)
        btnFinishSignUp = findViewById(R.id.btnFinishSignUp)
        AllOfPageSingup2= findViewById(R.id.AllOfPageSingup2)
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
    fun CLickToHidKeyBoard()
    {
        AllOfPageSingup2.setOnClickListener {
            ReadyFunction.hideKeyboard(inputAge,this)
        }
    }

    fun ActionWeight()
    {
        CadreLess50KG.setOnClickListener {
            CadreLess50KG.setImageResource(R.drawable.clickedcadre)
            CadreMore50KG.setImageResource(R.drawable.roundedcadre)
            textless.setTextColor(Color.parseColor("#FFFFFF"));
            textmore.setTextColor(Color.parseColor("#0E1621"));
            PoidsSUser="LessThen50KG"
        }
        CadreMore50KG.setOnClickListener {
            CadreMore50KG.setImageResource(R.drawable.clickedcadre)
            CadreLess50KG.setImageResource(R.drawable.roundedcadre)
            textmore.setTextColor(Color.parseColor("#FFFFFF"));
            textless.setTextColor(Color.parseColor("#0E1621"));
            PoidsSUser="MoreThen50KG"
        }
    }

    fun ActionSignUp()
    {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val MyName = sharedPreference.getString("SignUpName", null)
        val MyEmail = sharedPreference.getString("SignUpEmail", null)
        val MyPassword = sharedPreference.getString("SignUpPassword", null)
        val MyPhone = sharedPreference.getString("SignUpPhone", null)
        val MyLocation = sharedPreference.getString("SignUpLocation", null)

        btnFinishSignUp.setOnClickListener {
            BloodType=BloodGroup+BloodSigne
            println("BloodType =>>>>>>> "+BloodType)
            validateChamps()
            if(BloodType.isNotEmpty()&&inputAge.text.isNotEmpty()&&PoidsSUser.isNotEmpty())
            {
             ServiceSignuP(MyName!!,MyEmail!!,MyPassword!!,MyPhone!!,BloodType,inputAge.text.toString(),PoidsSUser,MyLocation!!)
            }
        }
    }


    fun validateChamps() {
        val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
        if(BloodSigne.isEmpty()&&!checkBoxDontKnow.isChecked)
        {
            BloodPlus.startAnimation(shake)
            BloodNegative.startAnimation(shake)
        }
        if(BloodGroup.isEmpty())
        {
            BloodA.startAnimation(shake)
            BloodAB.startAnimation(shake)
            BloodB.startAnimation(shake)
            BloodO.startAnimation(shake)
        }
        if(BloodGroup.isEmpty()&&BloodSigne.isEmpty()&&!checkBoxDontKnow.isChecked)
        {
            BloodPlus.startAnimation(shake)
            BloodNegative.startAnimation(shake)
            BloodA.startAnimation(shake)
            BloodAB.startAnimation(shake)
            BloodB.startAnimation(shake)
            BloodO.startAnimation(shake)
            checkBoxDontKnow.startAnimation(shake)
        }
        if(PoidsSUser.isEmpty())
        {
            CadreLess50KG.startAnimation(shake)
            CadreMore50KG.startAnimation(shake)
        }
        if(inputAge.text.isEmpty())
        {
            inputAge.startAnimation(shake)
        }
    }

    fun ChangeListener()
    {
        BloodA.setOnCheckedChangeListener(changeChecker);
        BloodAB.setOnCheckedChangeListener(changeChecker);
        BloodB.setOnCheckedChangeListener(changeChecker);
        BloodO.setOnCheckedChangeListener(changeChecker);
        BloodPlus.setOnCheckedChangeListener(changeChecker);
        BloodNegative.setOnCheckedChangeListener(changeChecker);
        checkBoxDontKnow.setOnCheckedChangeListener(changeChecker);
    }
    var changeChecker: CompoundButton.OnCheckedChangeListener = object :
        CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            if (isChecked) {
                if (buttonView == BloodA) {
                    BloodB.setChecked(false);
                    BloodAB.setChecked(false);
                    BloodO.setChecked(false);
                    checkBoxDontKnow.setChecked(false);
                    BloodGroup="A"
                }
                if (buttonView == BloodB) {
                    BloodA.setChecked(false);
                    BloodAB.setChecked(false);
                    BloodO.setChecked(false);
                    checkBoxDontKnow.setChecked(false);
                    BloodGroup="B"
                }
                if (buttonView == BloodAB) {
                    BloodB.setChecked(false);
                    BloodA.setChecked(false);
                    BloodO.setChecked(false);
                    checkBoxDontKnow.setChecked(false);
                    BloodGroup="AB"
                }
                if (buttonView == BloodO) {
                    BloodB.setChecked(false);
                    BloodAB.setChecked(false);
                    BloodA.setChecked(false);
                    checkBoxDontKnow.setChecked(false);
                    BloodGroup="O"
                }

                if (buttonView == BloodPlus) {
                    BloodNegative.setChecked(false);
                    checkBoxDontKnow.setChecked(false);
                    BloodSigne="+"
                }
                if (buttonView == BloodNegative) {
                    BloodPlus.setChecked(false);
                    checkBoxDontKnow.setChecked(false);
                    BloodSigne="-"
                }

                if (buttonView ==  checkBoxDontKnow) {
                    BloodPlus.setChecked(false);
                    BloodNegative.setChecked(false);
                    BloodB.setChecked(false);
                    BloodAB.setChecked(false);
                    BloodO.setChecked(false);
                    BloodA.setChecked(false);
                    BloodGroup="idk"
                    BloodSigne = ""
                }
            }
            if (!BloodA.isChecked && !BloodB.isChecked && !BloodAB.isChecked && !BloodO.isChecked &&!checkBoxDontKnow.isChecked) {
                BloodGroup=""
            }
            if (!BloodPlus.isChecked && !BloodNegative.isChecked) {
                BloodSigne=""
            }
        }
    }

    @SuppressLint("RestrictedApi")
    fun ServiceSignuP(name:String, email:String, password:String, phone:String, blood:String, age:String, weight:String, loaction:String) {
        // Create Retrofit
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)
        val User = Loginresponse()
        User.setName(name)
        User.setEmail(email)
        User.setPassword(password)
        User.setBlood(blood)
        User.setAge(age)
        User.setWeight(weight)
        User.setPhone(phone)
        User.setAdresse(loaction)

        CoroutineScope(Dispatchers.IO).launch {
            try{
            val response = service.Signup(User)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    CustomToast(this@SignUpPage2, "Sign up complete!","GREEN").show()
                    GoToLogin(this@SignUpPage2) //GoTo Page Login
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                }
            }
        }
        catch (e: Exception)
        {
            println("Error")
            ContextUtils.getActivity(this@SignUpPage2)?.runOnUiThread(java.lang.Runnable {
                CustomToast(this@SignUpPage2, "Sorry, Our Server Is Down!","RED").show()
            })
        }
        }
    }

    fun GoToLogin(context: Context) {
        val intent = Intent(context, Login::class.java)
        context.startActivity(intent)
        finish()
    }



}