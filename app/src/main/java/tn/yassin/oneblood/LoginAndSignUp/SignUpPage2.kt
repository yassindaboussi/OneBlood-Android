package tn.yassin.oneblood.LoginAndSignUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
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
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit

class SignUpPage2 : AppCompatActivity() {
    private lateinit var btnFinishSignUp: Button

    private lateinit var BloodA: Button
    private lateinit var BloodAB: Button
    private lateinit var BloodB: Button
    private lateinit var BloodO: Button

    private lateinit var BloodPlus: Button
    private lateinit var BloodNegative: Button
    private lateinit var checkBoxDontKnow: CheckBox
    //private lateinit var CheckboxPerson:CheckBox
    private lateinit var inputAge: TextView
   // var TypeBlood = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page2)

        BloodA = findViewById(R.id.BloodA)
        BloodAB = findViewById(R.id.BloodAB)
        BloodB = findViewById(R.id.BloodB)
        BloodO = findViewById(R.id.BloodO)

        BloodPlus = findViewById(R.id.BloodP)
        BloodPlus = findViewById(R.id.BloodN)
       // CheckboxPerson =findViewById(R.id.CheckboxPerson)

        inputAge =findViewById(R.id.inputAge)



        btnFinishSignUp = findViewById(R.id.btnFinishSignUp)
        btnFinishSignUp.setOnClickListener {
/*            val intent = Intent(this, Login::class.java).apply {
            }
         startActivity(intent)*/
            ServiceSignuP()
        }


    }

    fun GetBloodType(BloodA: Button) {
        BloodA.setOnClickListener {

        }
    }


    fun ServiceSignuP() {
        // Create Retrofit
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("name", "abcd")
        jsonObject.put("email", "a@a.com")
        jsonObject.put("password", "12345")
        jsonObject.put("blood", "O+")
        jsonObject.put("age", "25")
        jsonObject.put("weight", "74")
        jsonObject.put("adress", "Tunis")
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.Signup(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string()))
                    Log.d("Pretty Printed JSON :", prettyJson)

                   // GoToHome(this@SignUpPage2) //GoTo Page Home

                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
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