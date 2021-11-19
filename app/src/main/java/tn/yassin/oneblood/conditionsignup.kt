package tn.yassin.oneblood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox

class conditionsignup : AppCompatActivity() {
    private lateinit var btnConditionNext: Button
    private lateinit var iAgreeCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conditionsignup)
        btnConditionNext = findViewById(R.id.btnConditionNext)
        iAgreeCheckBox= findViewById(R.id.iAgreeCheckBox)
        btnConditionNext.setOnClickListener {
            if(iAgreeCheckBox.isChecked) {
                val intent = Intent(this, SignUpPage1::class.java).apply {
                }
                startActivity(intent)
            }
            else
            {
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                iAgreeCheckBox.startAnimation(shake)
            }
        }
    }
}