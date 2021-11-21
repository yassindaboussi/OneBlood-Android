package tn.yassin.oneblood.LoginAndSignUp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tn.yassin.oneblood.R

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
            val intent = Intent(this, Login::class.java).apply {
            }
         startActivity(intent)
        }

//        if(CheckboxPerson.isChecked)
//        {
//            inputAge.setText("1");
//        }
//        else {
//            inputAge.setText("2")}
    }

    fun GetBloodType(BloodA: Button) {
        BloodA.setOnClickListener {

        }
    }
}