package tn.yassin.oneblood.Fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.internal.ContextUtils
import kotlinx.android.synthetic.main.addneedy.*
import kotlinx.android.synthetic.main.setting_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import tn.yassin.oneblood.LoginAndSignUp.Login
import tn.yassin.oneblood.LoginAndSignUp.SignUpPage2
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Retrofit.Loginresponse
import tn.yassin.oneblood.Retrofit.Needyresponse
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit
import tn.yassin.oneblood.Util.CustomDialog
import tn.yassin.oneblood.Util.CustomToast
import tn.yassin.oneblood.Util.PlayMusic
import tn.yassin.oneblood.Util.ReadyFunction

class SettingFragment : Fragment() {

    private lateinit var EditName: TextView
    private lateinit var EditNameSetting: TextView
    private lateinit var EditEmailSetting : TextView
    private lateinit var EditPhoneSetting : TextView
    private lateinit var EditLocationSetting: TextView
    private lateinit var EditAgeSetting : TextView

    private var ctx: Context? = null
    private var self: View? = null

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ctx = container?.context
        self = LayoutInflater.from(ctx).inflate(R.layout.setting_fragment, container, false)
        val EditName = self?.findViewById<TextView>(R.id.EditName)
        EditNameSetting = self?.findViewById(R.id.EditNameSetting)!!
        val EditEmail = self?.findViewById<TextView>(R.id.EditEmail)
        EditEmailSetting = self?.findViewById(R.id.EditEmailSetting)!!
        //val EditPass = self?.findViewById<TextView>(R.id.EditPassword)
        //val EditPasswordSetting = self?.findViewById<TextView>(R.id.EditPasswordSetting)
        val EditTypeBlood = self?.findViewById<TextView>(R.id.EditTypeBlood)
        val EditTypeBloodSetting = self?.findViewById<Spinner>(R.id.EditTypeBloodSetting)
        val EditLocation = self?.findViewById<TextView>(R.id.EditLocation)
         EditLocationSetting = self?.findViewById(R.id.EditPasswordSetting)!!
        val EditPhone = self?.findViewById<TextView>(R.id.EditPhone)
        EditPhoneSetting= self?.findViewById(R.id.EditPhoneSetting)!!
        val EditAge = self?.findViewById<TextView>(R.id.EditAge)
        EditAgeSetting = self?.findViewById(R.id.EditAgeSetting)!!
        val UserimagSetting = self?.findViewById<ImageView>(R.id.UserimagSetting)
        val txtChangePassword = self?.findViewById<TextView>(R.id.txtChangePassword)
        ///////////////////////
        val Logouticon = self?.findViewById<ImageView>(R.id.logouticon)
        //////////////////////
        val spinnerUserType= self?.findViewById<Spinner>(R.id.spinnerUserType) // Spinner
        val EditSpinner= self?.findViewById<TextView>(R.id.EditSpinner)
        ////////////////////////////////////////////////////Set EditText False
        EditNameSetting?.setEnabled(false);
        EditEmailSetting?.setEnabled(false);
        //EditPasswordSetting?.setEnabled(false);
        EditTypeBloodSetting?.setEnabled(false);
        EditLocationSetting?.setEnabled(false);
        EditAgeSetting?.setEnabled(false);
        EditPhoneSetting?.setEnabled(false);
        spinnerUserType?.setEnabled(false);
        ////////////////////////////////////////////////////Set EditText True
        EditName?.setOnClickListener {
            EditNameSetting?.setEnabled(true);
        }
        EditEmail?.setOnClickListener {
            EditEmailSetting?.setEnabled(true);
        }
        EditPhone?.setOnClickListener {
            EditPhoneSetting?.setEnabled(true);
        }
/*        EditPass?.setOnClickListener {
            EditPasswordSetting?.setEnabled(true);
        }*/
        EditTypeBlood?.setOnClickListener {
            EditTypeBloodSetting?.setEnabled(true);
        }
        EditLocation?.setOnClickListener {
            EditLocationSetting?.setEnabled(true);
        }
        EditAge?.setOnClickListener {
            EditAgeSetting?.setEnabled(true);
        }
        EditSpinner?.setOnClickListener {
            spinnerUserType?.setEnabled(true);
        }
        /// Sett Value when he is connected!
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val MyID = sharedPreference.getString("id", null)
        val MyName = sharedPreference.getString("name", null)
        val MyBlood = sharedPreference.getString("blood", null)
        val MyAge = sharedPreference.getString("age", null)
        val MyAdresse = sharedPreference.getString("adress", null)
        val MyEmail = sharedPreference.getString("email", null)
        val MyPhone = sharedPreference.getString("phone", null)
        val MyType = sharedPreference.getString("usertype", null)
        val MyAvatar = sharedPreference.getString("avatar", null)


        ///////////////////////////////: Spinner
        val adapter =  ArrayAdapter.createFromResource(requireContext(), R.array.UserType, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUserType?.adapter = adapter
        /////
        val adapterr =  ArrayAdapter.createFromResource(requireContext(), R.array.blood_groups, android.R.layout.simple_spinner_item)
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        EditTypeBloodSetting?.adapter = adapterr


        /////////////////////////////////////////////// ButtonSaveClicked
        gettextwathcerSignUp() //
        val UpdateUserSetting = self?.findViewById<Button>(R.id.UpdateUserSetting)
        UpdateUserSetting?.setOnClickListener {
            EditNameSetting?.setEnabled(false);
            EditEmailSetting?.setEnabled(false);
            EditPhoneSetting?.setEnabled(false);
            //EditPasswordSetting?.setEnabled(false);
            EditTypeBloodSetting?.setEnabled(false);
            EditLocationSetting?.setEnabled(false);
            EditAgeSetting?.setEnabled(false);
            spinnerUserType?.setEnabled(false);

            if(!validateUserName() or !validateUserEmail() or !validateUserPhone() or !validateUserLocation())
            {
                return@setOnClickListener
            }
            else{
        Update(MyID!!,EditNameSetting!!,EditEmailSetting!!,EditAgeSetting!!,EditTypeBloodSetting!!,spinnerUserType!!,EditLocationSetting!!,EditPhoneSetting!!)
            }
        }

        EditNameSetting?.text=MyName
         EditEmailSetting?.text=MyEmail
         EditAgeSetting?.text=MyAge
         EditLocationSetting?.text=MyAdresse
         EditPhoneSetting?.text=MyPhone

        //////////////////////////////////////////////////
        if(MyType=="Donor") {
            spinnerUserType?.setSelection(0)
        }
        if(MyType=="Needy"){
            spinnerUserType?.setSelection(1)
        }
        //////////////////////////////////////////////////
        if(MyBlood=="idk") {
            EditTypeBloodSetting?.setSelection(0)
        }
        if(MyBlood=="A+"){
            EditTypeBloodSetting?.setSelection(1)
        }
        if(MyBlood=="A-"){
            EditTypeBloodSetting?.setSelection(2)
        }
        if(MyBlood=="B+"){
            EditTypeBloodSetting?.setSelection(3)
        }
        if(MyBlood=="B-"){
            EditTypeBloodSetting?.setSelection(4)
        }
        if(MyBlood=="O+"){
            EditTypeBloodSetting?.setSelection(5)
        }
        if(MyBlood=="O-"){
            EditTypeBloodSetting?.setSelection(6)
        }
        if(MyBlood=="AB+"){
            EditTypeBloodSetting?.setSelection(7)
        }
        if(MyBlood=="AB-"){
            EditTypeBloodSetting?.setSelection(8)
        }
        ////////////////////////////////////////////////////


        //Set Picture to User
        val function = ReadyFunction()
        if (MyAvatar?.isNotEmpty() == true && !MyAvatar.startsWith("avat") ) {
            function.setPhotodeProfil(MyAvatar.toLowerCase(),UserimagSetting!!)
        }
        if( MyAvatar?.isNotEmpty() == true && MyAvatar.startsWith("avat")){
            val PACKAGE_NAME = context?.getPackageName();
            UserimagSetting ?.setImageResource(getResources().getIdentifier(MyAvatar.toString(), "drawable", PACKAGE_NAME));
        }

        txtChangePassword?.setOnClickListener{
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.changepassword, null)
            val msg = CustomDialog()
            msg.ShowDialogChangePassword(context, view)
        }
        ///////////////////////

        UserimagSetting?.setOnClickListener{
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.changeavatar, null)
            val msg = CustomDialog()
            msg.ShowDialogChangeAvatar(context, view)
        }
        Logouticon?.setOnClickListener{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("RememberEmail", "")
            editor.apply()  //Save Data
            val intent = Intent (this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
            CustomToast(requireContext(), "See you \uD83D\uDE07","GREEN").show()
        }
///////////////////////
        return self
    }

    @SuppressLint("RestrictedApi")
    fun Update(ID:String, EditNameSetting: TextView, EditEmailSetting: TextView, EditAgeSetting: TextView, EditTypeBloodSetting: Spinner, spinnerUserType:Spinner, Location:TextView, Phone:TextView) {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)
        val User = Loginresponse()
        User.setID(ID)
        User.setName(EditNameSetting.text.toString())
        User.setEmail(EditEmailSetting.text.toString())
        User.setPhone(Phone.text.toString())
        User.setAge(EditAgeSetting.text.toString())
        User.setBlood(EditTypeBloodSetting.getSelectedItem().toString())
        User.setAdresse(Location.text.toString())
        User.setUserType(spinnerUserType.getSelectedItem().toString())
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val MyAvatar = preferences.getString("avatar", null)
        if (EditNameSetting.text.toString().isNotEmpty()&&MyAvatar?.isNotEmpty() == true && !MyAvatar.startsWith("avat") ) {
            User.setAvatar(EditNameSetting.text.toString())
        }
            println("=====>>>>>>>>>>>>>>>>>>>>>"+EditTypeBloodSetting.getSelectedItem().toString())
        CoroutineScope(Dispatchers.IO).launch {
            try{
            // Do the POST request and get response
            val response = service.UpdateUser(User)
            withContext(Dispatchers.Main) {
                if (response!!.isSuccessful) {
                    CustomToast(requireContext(), "Updated Successfull!","GREEN").show()

                    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                    val MyID = preferences.getString("id", null)
                    val editor = preferences.edit()
                    editor.putString("name", EditNameSetting.text.toString())
                    editor.putString("email", EditEmailSetting.text.toString())
                    editor.putString("blood", EditTypeBloodSetting.getSelectedItem().toString())
                    editor.putString("age", EditAgeSetting.text.toString())
                    editor.putString("usertype", spinnerUserType.getSelectedItem().toString())
                    editor.putString("adress", Location.text.toString())
                    editor.putString("phone", Phone.text.toString())

                    val MyAvatar = preferences.getString("avatar", null)
                    val function = ReadyFunction()

                    if (EditNameSetting.text.toString().isNotEmpty()&&MyAvatar?.isNotEmpty() == true && !MyAvatar.startsWith("avat") ) {
                        editor.putString("avatar", EditNameSetting.text.toString())
                        function.setPhotodeProfil(EditNameSetting.text.toString().toLowerCase(),UserimagSetting!!)

        ////////////////////////////////////////////////////////////////////////////
                       val Needy = Needyresponse()
                        Needy.setPostedby(MyID)
                        Needy.setAvatar(EditNameSetting.text.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            try{
                                val responsee = service.UpdateAvatarPost(Needy)
                               withContext(Dispatchers.Main) {
                                if (responsee!!.isSuccessful) {
                                    //println("waaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaw")
                           }
                           }
                            }
                            catch (e: Exception)
                            {
                                println("Error")
                            }
                        }
        //////////////////////////////////////////////////////////////////////////////

                    }
                    if( MyAvatar?.isNotEmpty() == true && MyAvatar.startsWith("avat")){
                        val PACKAGE_NAME = context?.getPackageName();
                        UserimagSetting ?.setImageResource(getResources().getIdentifier(MyAvatar.toString(), "drawable", PACKAGE_NAME));
                    }

                    editor.apply()  //Save Data


                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :"+response.errorBody()?.string())
                    CustomToast(requireContext(), "Something Wrong!","RED").show()
                }
            }
            }
            catch (e: Exception)
            {
                println("Error")
                ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                    CustomToast(requireContext(), "Sorry, Our Server Is Down!","RED").show()
                })
            }
        }
    }

//    fun getValues(view: View) {
//        Toast.makeText(this, "Spinner 1 " + spinner.selectedItem.toString(), Toast.LENGTH_LONG).show()
//    }


    private fun gettextwathcerSignUp() {
        EditNameSetting.addTextChangedListener(NameTextWatcher)
        EditEmailSetting.addTextChangedListener(emailTextWatcher)
        EditPhoneSetting.addTextChangedListener(PhoneTextWatcher)
        EditAgeSetting.addTextChangedListener(AgeTextWatcher)
        EditLocationSetting.addTextChangedListener(LocationTextWatcher)

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
    private val AgeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateUserAge() }
    }

    ////////////////////////////////////////////////////////
    private fun validateUserName(): Boolean {
        if (EditNameSetting.text.isEmpty()) {
            EditNameSetting.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
            EditNameSetting.startAnimation(shake)
            return false
        }
        else if (EditNameSetting.text.length < 3) {
            EditNameSetting.setError("At least 3 Characters!")
            return false
        }
        else {
            EditNameSetting.setError(null)
            return true
        }
        return true
    }

    private fun validateUserPhone(): Boolean {
        if (EditPhoneSetting.text.isEmpty()) {
            EditPhoneSetting.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
            EditPhoneSetting.startAnimation(shake)
            return false
        }  else if (EditPhoneSetting.text.length < 8) {
            EditPhoneSetting.setError("Missing "+(8-EditPhoneSetting.text.length)+" numbers")
            return false
        }
        else if (!(EditPhoneSetting.text.toString().isPhoneValid())) {
            EditPhoneSetting.setError("Malformed Phone Number!")
            return false
        }
        else {
            EditPhoneSetting.setError(null)
            return true
        }
        return true
    }

 private fun validateUserLocation(): Boolean {
        if (EditLocationSetting.text.isEmpty()) {
            EditLocationSetting.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
            EditLocationSetting.startAnimation(shake)
            return false
        } else {
            EditLocationSetting.setError(null)
            return true
        }
        return true
    }

    private fun validateUserEmail(): Boolean {
        val email: String = EditEmailSetting.text.toString().trim()
        if (EditEmailSetting.text.isEmpty()) {
            EditEmailSetting.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
            EditEmailSetting.startAnimation(shake)
            return false
        } else if (!(EditEmailSetting.text.toString().isEmailValid())) {
            EditEmailSetting.setError("Malformed Email!")
            return false
        } else {
            EditEmailSetting.setError(null)
            return true
        }
        return true
    }

    private fun validateUserAge(): Boolean {
        if (EditAgeSetting.text.isEmpty()) {
            EditAgeSetting.setError("Must Not be Empty!")
            val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
            EditAgeSetting.startAnimation(shake)
            return false
        } else {
            EditAgeSetting.setError(null)
            return true
        }
        return true
    }

    fun String.isEmailValid(): Boolean { return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches() }
    private fun String.isPhoneValid(): Boolean { return !TextUtils.isEmpty(this) && Patterns.PHONE.matcher(this).matches() }
    //////////////////////////////////////////////////////////////////////////////////
    private fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}