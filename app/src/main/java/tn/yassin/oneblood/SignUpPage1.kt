package tn.yassin.oneblood

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
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.*
import java.util.*

class SignUpPage1 : AppCompatActivity() {
    private lateinit var btnNextSignUp: Button
    private lateinit var emailSignup: TextView
    private lateinit var passwordSignup: TextView
    private lateinit var LocationSignup: TextView

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page1)
        //////////////////////////////////////
        btnNextSignUp = findViewById(R.id.btnNextSignUp)
        emailSignup = findViewById(R.id.emailSignup)
        passwordSignup = findViewById(R.id.passwordSignup)
        LocationSignup = findViewById(R.id.LocationSignup)
        LocationSignup.setEnabled(false);//Not Editable
        btnNextSignUp.setOnClickListener {
            CheckifEmpty(emailSignup, passwordSignup, LocationSignup)
            if ((emailSignup.text.toString()
                    .isEmailValid()) && !(passwordSignup.text.isEmpty()) && !(LocationSignup.text.isEmpty()) && passwordSignup.text.length >= 6
            ) {
                val intent = Intent(this, SignUpPage2::class.java).apply {
                }
                startActivity(intent)
            }
        }
        SetLocationUser()
    }

    fun CheckifEmpty(emailSignup: TextView, passwordSignup: TextView, Location: TextView) {
        if ((emailSignup.text.toString().isEmailValid()) && !(passwordSignup.text.isEmpty())
        ) {
            // SendData(EmailLogin,PasswordLogin)
        }
        if (emailSignup.text.isEmpty()) {
            emailSignup.setError("Must Not be Empty !")
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            emailSignup.startAnimation(shake)
        } else if (!(emailSignup.text.toString().isEmailValid())) {
            emailSignup.setError("Check Your Email !")
        }

        if (passwordSignup.text.isEmpty()) {
            passwordSignup.setError("Must Not be Empty !")
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            passwordSignup.startAnimation(shake)
        } else {
            passwordSignup.setError(null)
        }
        if (!passwordSignup.text.isEmpty() && passwordSignup.text.length < 6) {
            passwordSignup.setError("Password Must be at least 6 !")
        }

        if (Location.text.isEmpty()) {
            Location.setError("Must Not be Empty !")
            val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            Location.startAnimation(shake)
        } else {
            Location.setError(null)
        }
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    fun SetLocationUser() {
        //check permission
        //check internet
        if (checkForInternet(this)) { // There is cnx
            if (ContextCompat.checkSelfPermission(
                    applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_REQUEST_CODE
                )
            } else {
                LocationSignup.setEnabled(false);//Not Editable
                LocationSignup.text = ""
                getCurrentLocation()
            }
        } else { // No cnx
            Toast.makeText(this, "Need internet to share location", Toast.LENGTH_SHORT).show()
            LocationSignup.setEnabled(true); //Editable
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
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
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

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(this@SignUpPage1)
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                        var locIndex = locationResult.locations.size - 1

                        var latitude = locationResult.locations.get(locIndex).latitude
                        var longitude = locationResult.locations.get(locIndex).longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)

                        var address: String = addresses[0].getAddressLine(0)
                        val LocationSignup: TextView = findViewById(R.id.LocationSignup)
                        //LocationSignup.text = address
                        //val cityName = addresses[0].getLocality()
                        val stateName = addresses[0].adminArea
                        println("cityName " + stateName)

                        LocationSignup.text = stateName

                        val preferences = PreferenceManager.getDefaultSharedPreferences(this@SignUpPage1)
                        val editor = preferences.edit()
                        editor.putString("Name",stateName.toString())
                        editor.apply()

                    }
                }
            }, Looper.getMainLooper())

    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}