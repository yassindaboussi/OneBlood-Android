package tn.yassin.oneblood.LoginAndSignUp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Looper
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.*
import tn.yassin.oneblood.DataMapList.InformationAdapter
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Util.CustomToast
import tn.yassin.oneblood.Util.ReadyFunction

class SignUpPage1 : AppCompatActivity() {
    private lateinit var btnNextSignUp: Button
    private lateinit var NameSignup: TextView
    private lateinit var emailSignup: TextView
    private lateinit var passwordSignup: TextView
    private lateinit var LocationSignup: TextView
    private lateinit var PhoneSignup: TextView
    private lateinit var ShowPassword: ImageView
    private lateinit var AllOfPageSingup1: ConstraintLayout

    val ReadyFunction = ReadyFunction()

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page1)

        initView()
        showPassword()
        SetLocationUser()
        GoToSignUpPage2()
        CLickToHidKeyBoard()
    }

    fun initView()
    {
        btnNextSignUp = findViewById(R.id.btnNextSignUp)
        NameSignup= findViewById(R.id.NameSignup)
        emailSignup = findViewById(R.id.emailSignup)
        passwordSignup = findViewById(R.id.passwordSignup)
        LocationSignup = findViewById(R.id.LocationSignup)
        PhoneSignup = findViewById(R.id.PhoneSignup)
        ShowPassword= findViewById(R.id.ShowPassword)
        AllOfPageSingup1= findViewById(R.id.AllOfPageSingup1)
    }

    fun GoToSignUpPage2()
    {
        gettextwathcerSignUp()
        btnNextSignUp.setOnClickListener {
        if (!validateUserName() or !validateUserEmail() or !validateUserPassword() or !validateUserPhone() or !validateUserLocation()) {
                return@setOnClickListener
            } else {
                val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = preferences.edit()
                editor.putString("SignUpName", NameSignup.text.toString())
                editor.putString("SignUpEmail", emailSignup.text.toString())
                editor.putString("SignUpPassword", passwordSignup.text.toString())
                editor.putString("SignUpLocation", LocationSignup.text.toString())
                editor.putString("SignUpPhone", PhoneSignup.text.toString())
                editor.apply()

                val intent = Intent(this, SignUpPage2::class.java).apply {}
                startActivity(intent)
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }

    fun CLickToHidKeyBoard()
    {
        AllOfPageSingup1.setOnClickListener {
            ReadyFunction.hideKeyboard(NameSignup,this)
            ReadyFunction.hideKeyboard(emailSignup,this)
            ReadyFunction.hideKeyboard(passwordSignup,this)
            ReadyFunction.hideKeyboard(LocationSignup,this)
            ReadyFunction.hideKeyboard(PhoneSignup,this)
        }
    }

    fun showPassword()
    {
        var showingFirst = true
        ShowPassword.setOnClickListener {
            if(showingFirst == true){
                passwordSignup.setTransformationMethod(null);//Show Password
                showingFirst = false;
            }else{
                passwordSignup.setTransformationMethod(PasswordTransformationMethod()) //Hide Password
                showingFirst = true;
            }
        }
    }

    private fun gettextwathcerSignUp() {
        NameSignup.addTextChangedListener(NameTextWatcher)
        emailSignup.addTextChangedListener(emailTextWatcher)
        passwordSignup.addTextChangedListener(passwordTextWatcher)
        LocationSignup.addTextChangedListener(LocationTextWatcher)
        PhoneSignup.addTextChangedListener(PhoneTextWatcher)
    }
    private val NameTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateUserName() }
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
    private val LocationTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateUserLocation() }
    }
    private val PhoneTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateUserPhone() }
    }

    private fun validateUserName(): Boolean {
        if (NameSignup.text.isEmpty()) {
            NameSignup.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            NameSignup.startAnimation(shake)
            return false
        }
        else if (NameSignup.text.length < 3) {
            NameSignup.setError("At least 3 Characters!")
            return false
        }
        else {
            NameSignup.setError(null)
            return true
        }
        return true
    }

    private fun validateUserLocation(): Boolean {
        if (LocationSignup.text.isEmpty()) {
            LocationSignup.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            LocationSignup.startAnimation(shake)
            return false
        } else {
            LocationSignup.setError(null)
            return true
        }
        return true
    }

    private fun validateUserEmail(): Boolean {
        val email: String = emailSignup.text.toString().trim()
        if (emailSignup.text.isEmpty()) {
            emailSignup.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            emailSignup.startAnimation(shake)
            return false
        } else if (!(emailSignup.text.toString().isEmailValid())) {
            emailSignup.setError("Malformed Email!")
            return false
        } else {
            emailSignup.setError(null)
            return true
        }
        return true
    }
    private fun validateUserPassword(): Boolean {
        if (passwordSignup.text.isEmpty()) {
            passwordSignup.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            passwordSignup.startAnimation(shake)
            return false
        }   else if (passwordSignup.text.length < 6) {
            passwordSignup.setError("At least 6!")
            return false
        }
        else {
            passwordSignup.setError(null)
            return true
        }
        return true
    }

    private fun validateUserPhone(): Boolean {
        if (PhoneSignup.text.isEmpty()) {
            PhoneSignup.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            PhoneSignup.startAnimation(shake)
            return false
        }  else if (PhoneSignup.text.length < 8) {
            PhoneSignup.setError("Missing "+(8-PhoneSignup.text.length)+" numbers")
            return false
        }
        else if (!(PhoneSignup.text.toString().isPhoneValid())) {
            PhoneSignup.setError("Malformed Phone Number!")
            return false
        }
        else {
            PhoneSignup.setError(null)
            return true
        }
        return true
    }


    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    private fun String.isPhoneValid(): Boolean {
        return !TextUtils.isEmpty(this) && Patterns.PHONE.matcher(this).matches()
    }


    fun SetLocationUser() {
        val Cnx = ReadyFunction()
        //check permission
        //check internet
        if (Cnx.isOnline(this)) { // There is cnx
            if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_REQUEST_CODE)
            } else {
                //LocationSignup.setEnabled(false);//Not Editable
                //LocationSignup.text = ""
                getCurrentLocation()
            }
        } else { // No cnx
            CustomToast(this, "Need internet to Getlocation!","RED").show()
            //LocationSignup.setEnabled(true); //Editable
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                CustomToast(this@SignUpPage1, "Permission Denied!","RED").show()
            }
        }
    }

    private fun getCurrentLocation() {

        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //now getting address from latitude and longitude

        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return //IF Reject Permission GPS
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(this@SignUpPage1).removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                   try{
                        var locIndex = locationResult.locations.size - 1
                        var latitude = locationResult.locations.get(locIndex).latitude
                        var longitude = locationResult.locations.get(locIndex).longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        var address: String = addresses[0].getAddressLine(0)
                        val stateName = addresses[0].adminArea

                        LocationSignup.text = stateName
                       println("stateNamestateNamestateNamestateName" +stateName)
                       }
                        catch (e: Exception) { // When Something Goes Wrong
                            println(e.printStackTrace())
                            println("Canont get Address!");
                            CustomToast(this@SignUpPage1, "Canont get Location automatically!","RED").show()
                        }
                    }
                }
            }, Looper.getMainLooper())

    }

}