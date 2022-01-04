package tn.yassin.oneblood.Util

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.util.Base64
import android.widget.*
import androidx.core.view.isVisible
import com.google.android.material.internal.ContextUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import tn.yassin.oneblood.Fragments.SearchFragment
import tn.yassin.oneblood.Fragments.SettingFragment
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Retrofit.Loginresponse
import tn.yassin.oneblood.Retrofit.Needyresponse
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit
import java.text.SimpleDateFormat
import java.util.*


class CustomDialog(val talk: talk? = null) {

    private lateinit var DialogNameNeedy:TextInputLayout
    private lateinit var DialogLocationNeedy:TextInputEditText
    private lateinit var DialogBloodNeedy:TextInputLayout
    private lateinit var DialogPhoneNeedy:TextInputLayout

    private lateinit var EditNameNeedy:TextInputLayout
    private lateinit var EditLocationNeedy:TextInputLayout
    private lateinit var EditBloodNeedy:TextInputLayout
    private lateinit var EditPhoneNeedy:TextInputLayout
    val ReadyFunction= ReadyFunction()



    fun ShowTheCustomPopUp(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
    }

    fun ShowErrorDialog(context: Context?, view: View, msgtext: String) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)
        val BtnCloseDialogError = view.findViewById<Button>(R.id.BtnCloseDialogError) as? Button
        val SubjectError = view.findViewById<TextView>(R.id.SubjectError) as? TextView
        SubjectError?.text = msgtext
        BtnCloseDialogError?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                dialog.dismiss()
            }
        })
    }

    fun ShowDialogNoConnection(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)
        val btnigotit = view.findViewById<Button>(R.id.SavebtnAvatar) as? Button
        btnigotit?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                println("Hamdoulllllllaaaaah khedmet")
                dialog.dismiss()
            }
        })
    }

    fun ShowDialogChangePassword(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)
        val btnSaveDialog = view.findViewById<Button>(R.id.SavebtnAvatar) as? Button
        val showPasswordSetting = view.findViewById<Button>(R.id.showPasswordSetting) as? ImageView
        val PasswordSettingDialog =
            view.findViewById<TextView>(R.id.PasswordSettingDialog) as? TextView
        val ConfirmPasswordSettingDialog =
            view.findViewById<TextView>(R.id.ConfirmPasswordSettingDialog) as? TextView
        val NotTheSamePassword = view.findViewById<TextView>(R.id.NotTheSamePassword) as? TextView
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val MyID = sharedPreference.getString("id", null)
        NotTheSamePassword?.isVisible = false
        btnSaveDialog?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("RestrictedApi")
            override fun onClick(view: View?) {
                if (PasswordSettingDialog?.text.toString() == ConfirmPasswordSettingDialog?.text.toString()) {
                    if (PasswordSettingDialog?.text.toString()
                            .isNotEmpty() && ConfirmPasswordSettingDialog?.text.toString()
                            .isNotEmpty()
                    ) {
                        val retrofi: Retrofit = retrofit.getInstance()
                        val service: Request = retrofi.create(Request::class.java)
                        val User = Loginresponse()
                        User.setID(MyID)
                        User.setPassword(PasswordSettingDialog?.text.toString())

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                // Do the POST request and get response
                                val response = service.UpdatePassword(User)
                                withContext(Dispatchers.Main) {
                                    if (response!!.isSuccessful) {
                                        dialog.dismiss()
                                        NotTheSamePassword?.isVisible = false
                                        println("success")
                                    } else {
                                        Log.e("RETROFIT_ERROR", response.code().toString())
                                        println("Message :" + response.errorBody()?.string())
                                        CustomToast(context, "Something Went Wrong!", "RED").show()
                                    }
                                }
                            } catch (e: Exception) {
                                println("Error")
                                ContextUtils.getActivity(context)
                                    ?.runOnUiThread(java.lang.Runnable {
                                        dialog.dismiss()
                                        CustomToast(
                                            context,
                                            "Sorry, Our Server Is Down!",
                                            "RED"
                                        ).show()

                                    })
                            }
                        }
                    }
                } else {
                    NotTheSamePassword?.isVisible = true
                }
            }
        })
        var showingFirst = true
        showPasswordSetting?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                println("Hamdoulllllllaaaaah ShowPass")
                //dialog.dismiss()

                if (showingFirst == true) {
                    PasswordSettingDialog?.setTransformationMethod(null);//Show Password
                    ConfirmPasswordSettingDialog?.setTransformationMethod(null);//Show Password
                    showingFirst = false;
                } else {
                    PasswordSettingDialog?.setTransformationMethod(PasswordTransformationMethod()) //Hide Password
                    ConfirmPasswordSettingDialog?.setTransformationMethod(
                        PasswordTransformationMethod()
                    ) //Hide Password
                    showingFirst = true;
                }
            }
        })
    }


    fun ShowDialogChangeAvatar(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)
        val SavebtnAvatar = view.findViewById<Button>(R.id.SavebtnAvatar) as? Button
        val previewUserImage = view.findViewById<ImageView>(R.id.previewUserImage) as? ImageView
        val avatar1 = view.findViewById<ImageView>(R.id.avatar1) as? ImageView
        val avatar2 = view.findViewById<ImageView>(R.id.avatar2) as? ImageView
        val avatar3 = view.findViewById<ImageView>(R.id.avatar3) as? ImageView
        val avatar4 = view.findViewById<ImageView>(R.id.avatar4) as? ImageView
        val avatar5 = view.findViewById<ImageView>(R.id.avatar5) as? ImageView
        val avatar6 = view.findViewById<ImageView>(R.id.avatar6) as? ImageView
        val avatar7 = view.findViewById<ImageView>(R.id.avatar7) as? ImageView
        val avatar8 = view.findViewById<ImageView>(R.id.avatar8) as? ImageView
        val avatar9 = view.findViewById<ImageView>(R.id.avatar9) as? ImageView

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val MyID = sharedPreference.getString("id", null)
        val MyName = sharedPreference.getString("name", null)
        val MyAvatar = sharedPreference.getString("avatar", null)
        val function = ReadyFunction()

        if (MyAvatar?.isNotEmpty() == true && !MyAvatar.startsWith("avat") ) {
            function.setPhotodeProfil(MyAvatar.toLowerCase(),previewUserImage!!)
        }
        if( MyAvatar?.isNotEmpty() == true && MyAvatar.startsWith("avat")){
            val PACKAGE_NAME = context?.getPackageName();
            context.getResources().getIdentifier(MyAvatar.toString(), "drawable", PACKAGE_NAME)
        }

        var imgSelectedis = ""
        avatar1?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar1)
                imgSelectedis = "avatar1"
            }
        })
        avatar2?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar2)
                imgSelectedis = "avatar2"
            }
        })
        avatar3?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar3)
                imgSelectedis = "avatar3"
            }
        })
        avatar4?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar4)
                imgSelectedis = "avatar4"
            }
        })
        avatar5?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar5)
                imgSelectedis = "avatar5"
            }
        })
        avatar6?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar6)
                imgSelectedis = "avatar6"
            }
        })
        avatar7?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar7)
                imgSelectedis = "avatar7"
            }
        })
        avatar8?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar8)
                imgSelectedis = "avatar8"
            }
        })
        avatar9?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                previewUserImage?.setImageResource(R.drawable.avatar9)
                imgSelectedis = "avatar9"
            }
        })

        SavebtnAvatar?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("RestrictedApi")
            override fun onClick(view: View?) {
                val retrofi: Retrofit = retrofit.getInstance()
                val service: Request = retrofi.create(Request::class.java)
                val User = Loginresponse()
                User.setID(MyID)
                User.setAvatar(imgSelectedis)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Do the POST request and get response
                        val response = service.UpdateAvatar(User)
                        withContext(Dispatchers.Main) {
                            if (response!!.isSuccessful) {
                                CustomToast(context, "Updated Successfull!","GREEN").show()
                                val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                                val editor = preferences.edit()
                                editor.putString("avatar", imgSelectedis)
                                editor.apply()  //Save Data
                                dialog.dismiss()
                                ReadyFunction.changeFragment(SettingFragment(), context)
                                println("success")

                                ////////////////////////////////////////////////////////////////////////////
                                val Needy = Needyresponse()
                                Needy.setPostedby(MyID)
                                Needy.setAvatar(imgSelectedis)
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
                            } else {
                                Log.e("RETROFIT_ERROR", response.code().toString())
                                println("Message :" + response.errorBody()?.string())
                                CustomToast(context, "Something Went Wrong!", "RED").show()
                            }
                        }
                    } catch (e: Exception) {
                        println("Error")
                        ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                            dialog.dismiss()
                            CustomToast(context, "Our Server Is Down!", "RED").show()

                        })
                    }
                }
            }
        })

    }

    private fun gettextwathcerAddNeedy() {
        DialogNameNeedy?.editText?.addTextChangedListener(NameTextWatcher)
        DialogLocationNeedy.addTextChangedListener(LocationTextWatcher)
        DialogPhoneNeedy?.editText?.addTextChangedListener(PhoneTextWatcher)
    }
    private val NameTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateNeedyName() }
    }
    private val LocationTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateNeedyLocation() }
    }
    private val PhoneTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateNeedyPhone() }
    }
    private fun validateNeedyLocation(): Boolean {
        if (DialogLocationNeedy.text!!.isEmpty()) {
            DialogLocationNeedy.setError("Must Not be Empty !")
            return false
        } else {
            DialogLocationNeedy.setError(null)
            return true
        }
        return true
    }
    private fun validateNeedyName(): Boolean {
        if (DialogNameNeedy?.editText?.text!!.isEmpty()) {
            DialogNameNeedy.setError("Must Not be Empty !")
            return false
        } else {
            DialogNameNeedy.setError(null)
            return true
        }
        return true
    }
    private fun validateNeedyPhone(): Boolean {
        if (DialogPhoneNeedy.editText?.text!!.isEmpty()) {
            DialogPhoneNeedy.setError("Must Not be Empty!")
            return false
        }  else if (DialogPhoneNeedy.editText?.text!!.length < 8) {
            DialogPhoneNeedy.setError("Missing "+(8-DialogPhoneNeedy.editText?.text!!.length)+" numbers")
            return false
        }
        else if (!(DialogPhoneNeedy.editText?.text!!.toString().isPhoneValid())) {
            DialogPhoneNeedy.setError("Malformed Phone Number!")
            return false
        }
        else {
            DialogPhoneNeedy.setError(null)
            return true
        }
        return true
    }

    private fun String.isPhoneValid(): Boolean {
        return !TextUtils.isEmpty(this) && Patterns.PHONE.matcher(this).matches()
    }


    fun ShowDialogAddNeedy(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)

        val BtnSaveAddNeedy = view.findViewById<Button>(R.id.BtnSaveAddNeedy) as? Button
         DialogNameNeedy = view.findViewById(R.id.DialogNameNeedy)
         DialogLocationNeedy = view.findViewById(R.id.DialogLocationNeedy)
         DialogBloodNeedy = view.findViewById(R.id.DialogBloodNeedy)
         DialogPhoneNeedy = view.findViewById(R.id.DialogPhoneNeedy)

        var bloodType: Array<String>
        val actvBloodGroup = view.findViewById<AutoCompleteTextView>(R.id.actvBloodGroup) as? AutoCompleteTextView
        bloodType = context.resources.getStringArray(R.array.BloodGroups) as Array<String>
        ReadyFunction.autoCompleteListAdapter(context, bloodType, actvBloodGroup!!)
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val MyID = sharedPreference.getString("id", null)
        //
        val MyAvatar = sharedPreference.getString("avatar", null)
        ////

        val CadreDanger = view.findViewById<ImageView>(R.id.CadreDanger) as? ImageView
        val CadreNormal = view.findViewById<ImageView>(R.id.CadreNormal) as? ImageView
        val textNormal = view.findViewById<TextView>(R.id.textNormal) as? TextView
        val textDanger = view.findViewById<TextView>(R.id.textDanger) as? TextView
        var Situation=""
        CadreDanger?.setOnClickListener {
            CadreDanger.setImageResource(R.drawable.clickedcadre)
            CadreNormal?.setImageResource(R.drawable.roundedcadre)
            textNormal?.setTextColor(Color.parseColor("#020101"));
            textDanger?.setTextColor(Color.parseColor("#F05223"));
            Situation="Danger"
        }
        CadreNormal?.setOnClickListener {
            CadreNormal?.setImageResource(R.drawable.clickedcadre)
            CadreDanger?.setImageResource(R.drawable.roundedcadre)
            textDanger?.setTextColor(Color.parseColor("#020101"));
            textNormal?.setTextColor(Color.parseColor("#F05223"));
            Situation="Normal"
        }
        textDanger?.setOnClickListener {
            CadreDanger?.setImageResource(R.drawable.clickedcadre)
            CadreNormal?.setImageResource(R.drawable.roundedcadre)
            textNormal?.setTextColor(Color.parseColor("#020101"));
            textDanger?.setTextColor(Color.parseColor("#F05223"));
            Situation="Danger"
        }
        textNormal?.setOnClickListener {
            CadreNormal?.setImageResource(R.drawable.clickedcadre)
            CadreDanger?.setImageResource(R.drawable.roundedcadre)
            textDanger?.setTextColor(Color.parseColor("#020101"));
            textNormal?.setTextColor(Color.parseColor("#F05223"));
            Situation="Normal"
        }
        ///////
        gettextwathcerAddNeedy()
        BtnSaveAddNeedy?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("RestrictedApi")
            override fun onClick(view: View?) {
                if (DialogBloodNeedy!!.editText?.text?.toString() == "Blood group"
                    || DialogBloodNeedy!!.editText?.text?.isEmpty() == true
                   or  !validateNeedyName() or !validateNeedyLocation() or !validateNeedyPhone()
                ) {
                    CustomToast(context, "Something is empty!", "RED").show()
                    return
                }
                if(DialogBloodNeedy!!.editText?.text?.toString() != "Blood group" && DialogBloodNeedy!!.editText?.text?.isEmpty() == false
                    &&validateNeedyName()&&validateNeedyLocation()&&validateNeedyPhone() && Situation.isNotEmpty()
                )
                {
                    val currentDate: String = SimpleDateFormat("dd MMMM HH:mm",Locale.ENGLISH).format(Date())
                    val currentDateandTime: String = currentDate.format(Date())
                    val retrofi: Retrofit = retrofit.getInstance()
                    val service: Request = retrofi.create(Request::class.java)
                    val Needy = Needyresponse()
                    Needy.setDatepost(currentDateandTime)
                    Needy.setPostedby(MyID)
                    Needy.setNom(DialogNameNeedy?.editText?.text!!.toString())
                    Needy.setBlood(DialogBloodNeedy?.editText?.text.toString())
                    Needy.setLocation(DialogLocationNeedy?.text.toString())
                    Needy.setPhone(Integer.valueOf(DialogPhoneNeedy?.editText?.text.toString()))
                    Needy.setSituation(Situation)
                    if(!MyAvatar.toString().isEmpty()){
                        Needy.setAvatar(MyAvatar)
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Do the POST request and get response
                            val response = service.AddNeedy(Needy)
                            withContext(Dispatchers.Main) {
                                if (response!!.isSuccessful) {
                                    CustomToast(context, "Added Successfully!","GREEN").show()
                                    talk!!.senddata(response.body()!!)
                                    dialog.dismiss()
                                    println("success")
                                    //changeFragment(SearchFragment(), context)
                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                    println("Message :" + response.errorBody()?.string())
                                    CustomToast(context, "Something Went Wrong!", "RED").show()
                                }
                            }
                        } catch (e: Exception) {
                            println("Error")
                            ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                                dialog.dismiss()
                                CustomToast(context, "Sorry, Our Server Is Down!", "RED").show()

                            })
                        }
                    }
                }

            }
        })

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private fun gettextwathcerUpdateNeedy() {
        EditNameNeedy?.editText?.addTextChangedListener(EditNameTextWatcher)
        EditLocationNeedy.editText?.addTextChangedListener(EditLocationTextWatcher)
        EditPhoneNeedy?.editText?.addTextChangedListener(EditPhoneTextWatcher)
    }
    private val EditNameTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateEditNeedyName() }
    }
    private val EditLocationTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateEditNeedyLocation() }
    }
    private val EditPhoneTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateEditNeedyPhone() }
    }
    private fun validateEditNeedyLocation(): Boolean {
        if (EditLocationNeedy.editText?.text!!.isEmpty()) {
            EditLocationNeedy.setError("Must Not be Empty !")
            return false
        } else {
            EditLocationNeedy.setError(null)
            return true
        }
        return true
    }
    private fun validateEditNeedyName(): Boolean {
        if (EditNameNeedy?.editText?.text!!.isEmpty()) {
            EditNameNeedy.setError("Must Not be Empty !")
            return false
        } else {
            EditNameNeedy.setError(null)
            return true
        }
        return true
    }
    private fun validateEditNeedyPhone(): Boolean {
        if (EditPhoneNeedy.editText?.text!!.isEmpty()) {
            EditPhoneNeedy.setError("Must Not be Empty!")
            return false
        }  else if (EditPhoneNeedy.editText?.text!!.length < 8) {
            EditPhoneNeedy.setError("Missing "+(8-EditPhoneNeedy.editText?.text!!.length)+" numbers")
            return false
        }
        else if (!(EditPhoneNeedy.editText?.text!!.toString().isPhoneValid())) {
            EditPhoneNeedy.setError("Malformed Phone Number!")
            return false
        }
        else {
            EditPhoneNeedy.setError(null)
            return true
        }
        return true
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun ShowDialogUpdatePostNeedy(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)

        val BtnUpdateNeedy = view.findViewById<Button>(R.id.BtnUpdateNeedy) as? Button
        EditNameNeedy = view.findViewById(R.id.EditNameNeedy)
        EditLocationNeedy = view.findViewById(R.id.EditLocationNeedy)
        EditBloodNeedy = view.findViewById(R.id.EditBloodNeedy)
        EditPhoneNeedy = view.findViewById(R.id.EditPhoneNeedy)

        var bloodTypee: Array<String>
        val autoBloodGroup = view.findViewById<AutoCompleteTextView>(R.id.autoBloodGroup) as? AutoCompleteTextView
        bloodTypee = context.resources.getStringArray(R.array.BloodGroups) as Array<String>
        ReadyFunction.autoCompleteListAdapter(context, bloodTypee, autoBloodGroup!!)

        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val PostNeedyid = sharedPreference.getString("PostNeedyid", null)
        val NeedyName = sharedPreference.getString("NeedyName", null)
        val NeedyHospital = sharedPreference.getString("NeedyLocation", null)
        val NeedyBlood = sharedPreference.getString("NeedyBlood", null)
        val Needyphone = sharedPreference.getString("Needyphone", null)
        val MySituation = sharedPreference.getString("NeedySituation", null)
    ////

    val EditCadreDanger = view.findViewById<ImageView>(R.id.EditCadreDanger) as? ImageView
    val EditCadreNormal = view.findViewById<ImageView>(R.id.EditCadreNormal) as? ImageView
    val EdittextNormal = view.findViewById<TextView>(R.id.EdittextNormal) as? TextView
    val EdittextDanger = view.findViewById<TextView>(R.id.EdittextDanger) as? TextView
    var Situation=MySituation
    if(Situation=="Danger")
    {
        EditCadreDanger?.setImageResource(R.drawable.clickedcadre)
        EdittextDanger?.setTextColor(Color.parseColor("#F05223"));
    }
    if(Situation=="Normal")
    {
        EditCadreNormal?.setImageResource(R.drawable.clickedcadre)
        EdittextNormal?.setTextColor(Color.parseColor("#F05223"));
    }
    EditCadreDanger?.setOnClickListener {
        EditCadreDanger?.setImageResource(R.drawable.clickedcadre)
        EditCadreNormal?.setImageResource(R.drawable.roundedcadre)
        EdittextNormal?.setTextColor(Color.parseColor("#020101"));
        EdittextDanger?.setTextColor(Color.parseColor("#F05223"));
        Situation="Danger"
    }
    EditCadreNormal?.setOnClickListener {
        EditCadreNormal?.setImageResource(R.drawable.clickedcadre)
        EditCadreDanger?.setImageResource(R.drawable.roundedcadre)
        EdittextDanger?.setTextColor(Color.parseColor("#020101"));
        EdittextNormal?.setTextColor(Color.parseColor("#F05223"));
        Situation="Normal"
    }
    EdittextDanger?.setOnClickListener {
        EditCadreDanger?.setImageResource(R.drawable.clickedcadre)
        EditCadreNormal?.setImageResource(R.drawable.roundedcadre)
        EdittextNormal?.setTextColor(Color.parseColor("#020101"));
        EdittextDanger?.setTextColor(Color.parseColor("#F05223"));
        Situation="Danger"
    }
    EdittextNormal?.setOnClickListener {
        EditCadreNormal?.setImageResource(R.drawable.clickedcadre)
        EditCadreDanger?.setImageResource(R.drawable.roundedcadre)
        EdittextDanger?.setTextColor(Color.parseColor("#020101"));
        EdittextNormal?.setTextColor(Color.parseColor("#F05223"));
        Situation="Normal"
    }
    ///////
        ///
        EditBloodNeedy.editText?.setText(NeedyBlood)
        EditNameNeedy.editText?.setText(NeedyName)
        EditLocationNeedy.editText?.setText(NeedyHospital)
        EditPhoneNeedy.editText?.setText(Needyphone)
        /////

        gettextwathcerUpdateNeedy()
        BtnUpdateNeedy?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("RestrictedApi")
            override fun onClick(view: View?) {
                if (EditBloodNeedy!!.editText?.text?.toString() == "Blood group"
                    || EditBloodNeedy!!.editText?.text?.isEmpty() == true
                    or  !validateEditNeedyName() or !validateEditNeedyLocation() or !validateEditNeedyPhone()
                ) {
                    CustomToast(context, "Something is empty!", "RED").show()
                    return
                }
                if(EditBloodNeedy!!.editText?.text?.toString() != "Blood group" && EditBloodNeedy!!.editText?.text?.isEmpty() == false
                    &&validateEditNeedyName()&&validateEditNeedyLocation()&&validateEditNeedyPhone()
                )
                {
                    val retrofi: Retrofit = retrofit.getInstance()
                    val service: Request = retrofi.create(Request::class.java)
                    val Needy = Needyresponse()
                    Needy.setId(PostNeedyid)
                    Needy.setNom(EditNameNeedy?.editText?.text!!.toString())
                    Needy.setBlood(EditBloodNeedy?.editText?.text.toString())
                    Needy.setLocation(EditLocationNeedy?.editText?.text.toString())
                    Needy.setPhone(Integer.valueOf(EditPhoneNeedy?.editText?.text.toString()))
                    Needy.setSituation(Situation)

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Do the POST request and get response
                            val response = service.UpdatePost(Needy)
                            withContext(Dispatchers.Main) {
                                if (response!!.isSuccessful) {
                                    CustomToast(context, "Updated Successfull!","GREEN").show()
                                    //talk!!.senddata(response.body()!!)
                                    dialog.dismiss()
                                    println("success")
                                    ReadyFunction.changeFragment(SearchFragment(), context)
                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                    println("Message :" + response.errorBody()?.string())
                                    CustomToast(context, "Something Went Wrong!", "RED").show()
                                }
                            }
                        } catch (e: Exception) {
                            println("Error")
                            println(e.printStackTrace())
                            ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                                dialog.dismiss()
                                CustomToast(context, "Sorry, Our Server Is Down!", "RED").show()

                            })
                        }
                    }
                }

            }
        })

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    fun ShowOptionPopup(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()

        val optionEditPost = view.findViewById<TextView>(R.id.optionEditPost) as? TextView
        val optionDeletePost = view.findViewById<TextView>(R.id.optionDeletePost) as? TextView
        val optionCloseThisCase = view.findViewById<TextView>(R.id.optionCloseThisCase) as? TextView
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val NeedySituation = sharedPreference.getString("NeedySituation", null)
        /////

        if(NeedySituation=="Closed")
        {
            optionEditPost?.visibility = View.GONE
            optionCloseThisCase?.visibility = View.GONE
        }
        //Show PopUppEditPost
        optionEditPost?.setOnClickListener {
            dialog.cancel()
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.updateneedy, null)
            val msg = CustomDialog()
            msg.ShowDialogUpdatePostNeedy(context, view)
        }
        optionDeletePost?.setOnClickListener {
            dialog.cancel()
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.deleteneedy, null)
            val msg = CustomDialog()
            msg.ShowDeletePostNeedy(context, view)
        }
        optionCloseThisCase?.setOnClickListener {
            dialog.cancel()
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.confirmclosecase, null)
            val msg = CustomDialog()
            msg.ShowCloseThisCase(context, view)
        }

    }

    @SuppressLint("RestrictedApi")
    fun ShowCloseThisCase(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val PostNeedyid = sharedPreference.getString("PostNeedyid", null)
        /////
        val CloseCaseYes = view.findViewById<TextView>(R.id.CloseCaseYes) as? TextView
        val CloseCaseNo = view.findViewById<TextView>(R.id.CloseCaseNo) as? TextView

        CloseCaseNo?.setOnClickListener{
            dialog.dismiss()
        }
        CloseCaseYes?.setOnClickListener{

            val retrofi: Retrofit = retrofit.getInstance()
            val service: Request = retrofi.create(Request::class.java)
            val Needy = Needyresponse()
            Needy.setId(PostNeedyid)
            Needy.setSituation("Closed")

            if(PostNeedyid!!.isNotEmpty())
            {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = service.CloseCase(Needy)
                        withContext(Dispatchers.Main) {
                            if (response!!.isSuccessful) {
                                CustomToast(context, "Case Successfully Closed!","GREEN").show()
                                dialog.dismiss()
                                ReadyFunction.changeFragment(SearchFragment(), context)
                            } else {
                                Log.e("RETROFIT_ERROR", response.code().toString())
                                println("Message :" + response.errorBody()?.string())
                                CustomToast(context, "Something Went Wrong!", "RED").show()
                            }
                        }
                    } catch (e: Exception) {
                        println("Error")
                        println(e.printStackTrace())
                        ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                            dialog.dismiss()
                            // CustomToast(context, "Sorry, Our Server Is Down!", "RED").show()
                            ReadyFunction.changeFragment(SearchFragment(), context)
                        })
                    }
                }
            }
        }

    }


    @SuppressLint("RestrictedApi")
    fun ShowDeletePostNeedy(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val PostNeedyid = sharedPreference.getString("PostNeedyid", null)
        val NeedyName = sharedPreference.getString("NeedyName", null)
        val NeedyHospital = sharedPreference.getString("NeedyLocation", null)
        val NeedyBlood = sharedPreference.getString("NeedyBlood", null)
        val Needyphone = sharedPreference.getString("Needyphone", null)
        /////
        val deleteBlood = view.findViewById<TextView>(R.id.deleteBlood) as? TextView
        val deleteName = view.findViewById<TextView>(R.id.deleteName) as? TextView
        val deleteHospital = view.findViewById<TextView>(R.id.deleteHospital) as? TextView
        val deletePhone = view.findViewById<TextView>(R.id.deletePhone) as? TextView
        /////
        deleteBlood?.text="Blood: "+NeedyBlood
        deleteName?.text="Name: "+NeedyName
        deleteHospital?.text="Hospital: "+NeedyHospital
        deletePhone?.text="Phone: "+Needyphone
        /////
        val btnYesDeleteNeedy = view.findViewById<Button>(R.id.btnYesDeleteNeedy) as? Button
        val btnNoDeleteNeedy = view.findViewById<Button>(R.id.btnNoDeleteNeedy) as? Button

        btnNoDeleteNeedy?.setOnClickListener{
            dialog.dismiss()
        }
        btnYesDeleteNeedy?.setOnClickListener{

            val retrofi: Retrofit = retrofit.getInstance()
            val service: Request = retrofi.create(Request::class.java)
            val Needy = Needyresponse()
            Needy.setId(PostNeedyid)

            if(PostNeedyid!!.isNotEmpty())
            {
                CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = service.deletPost(Needy)
                    withContext(Dispatchers.Main) {
                        if (response!!.isSuccessful) {
                            CustomToast(context, "Deleted Successfully!","GREEN").show()
                            dialog.dismiss()
                            ReadyFunction.changeFragment(SearchFragment(), context)
                        } else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                            println("Message :" + response.errorBody()?.string())
                            CustomToast(context, "Something Went Wrong!", "RED").show()
                        }
                    }
                } catch (e: Exception) {
                    println("Error")
                    println(e.printStackTrace())
                    ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                        dialog.dismiss()
                       // CustomToast(context, "Sorry, Our Server Is Down!", "RED").show()
                        ReadyFunction.changeFragment(SearchFragment(), context)
                    })
                }
            }
        }
        }

    }


    fun ShowDetailsNeedy(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)

        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val PostNeedyid = sharedPreference.getString("PostNeedyid", null)
        val NeedyName = sharedPreference.getString("NeedyName", null)
        val NeedyHospital = sharedPreference.getString("NeedyLocation", null)
        val NeedyBlood = sharedPreference.getString("NeedyBlood", null)
        val Needyphone = sharedPreference.getString("Needyphone", null)
        val NeedyScreenShot= sharedPreference.getString("NeedyScreenShot", null)
        val NeedySituation = sharedPreference.getString("NeedySituation", null)
        val NeedyPostedBy = sharedPreference.getString("NeedyPostedBy", null)
        val Userid = sharedPreference.getString("id", null)
        /////
        val DetailsBlood = view.findViewById<TextView>(R.id.DetailsBlood) as? TextView
        val DetailsFullName = view.findViewById<TextView>(R.id.DetailsFullName) as? TextView
        val DetailsHospitalLocation = view.findViewById<TextView>(R.id.DetailsHospitalLocation) as? TextView
        val DetailsPhoneNeedy = view.findViewById<TextView>(R.id.DetailsPhoneNeedy) as? TextView
        /////
        DetailsBlood?.text="Blood: "+NeedyBlood
        DetailsFullName?.text="Name: "+NeedyName
        DetailsHospitalLocation?.text="Hospital: "+NeedyHospital
        DetailsPhoneNeedy?.text="Phone: "+Needyphone
        /////
        val DetailsCallNeedy = view.findViewById<TextView>(R.id.DetailsCallNeedy) as? TextView
        val DetailsShareNeedy = view.findViewById<TextView>(R.id.DetailsShareNeedy) as? TextView
        val CloseDetailsNeed = view.findViewById<ImageView>(R.id.CloseDetailsNeed) as? ImageView
        /////
        println("NeedyPostedBy "+NeedySituation)
        if(NeedySituation=="Closed")
        {
            DetailsShareNeedy?.visibility = View.GONE
            DetailsCallNeedy?.visibility = View.GONE
        }
        if(NeedyPostedBy==Userid)
        {
            //DetailsShareNeedy?.visibility = View.GONE
            DetailsShareNeedy?.setLayoutParams(TableLayout.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT,
                ActionBar.LayoutParams.FILL_PARENT,
                1f
            ))
            DetailsCallNeedy?.visibility = View.GONE
        }
        /////
        CloseDetailsNeed?.setOnClickListener{
            dialog.dismiss()
        }
        DetailsCallNeedy?.setOnClickListener{
            try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$Needyphone")
            context.startActivity(intent)
        }catch (e: Exception) {
            println(e.printStackTrace())
          }
        }
        DetailsShareNeedy?.setOnClickListener{
            dialog.cancel()
            dialog.dismiss()
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.optionsharepopup, null)
            val msg = CustomDialog()
            msg.ShareToHelp(context, view)

        }

    }

    fun ShareToHelp(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)

        /////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val NeedyScreenShot= sharedPreference.getString("NeedyScreenShot", null)
        /////
        val WhatsAppShare = view.findViewById<ImageView>(R.id.WhatsAppShare) as? ImageView
        val FacebookShare = view.findViewById<ImageView>(R.id.FacebookShare) as? ImageView
        val MessengerShare = view.findViewById<ImageView>(R.id.MessengerShare) as? ImageView
        val TwitterShare = view.findViewById<ImageView>(R.id.TwitterShare) as? ImageView
        val SnapChatShare = view.findViewById<ImageView>(R.id.SnapChatShare) as? ImageView
        val TelegramShare = view.findViewById<ImageView>(R.id.TelegramShare) as? ImageView
        val EmailShare = view.findViewById<ImageView>(R.id.EmailShare) as? ImageView
        val DiscordShare = view.findViewById<ImageView>(R.id.DiscordShare) as? ImageView

        val InstagramShare = view.findViewById<ImageView>(R.id.InstagramShare) as? ImageView
        val btnCloseShare = view.findViewById<TextView>(R.id.btnCloseShare) as? TextView
        /////
        val b: ByteArray = Base64.decode(NeedyScreenShot, Base64.DEFAULT)
        val bitmapImage = BitmapFactory.decodeByteArray(b, 0, b.size)
        val waterMark = BitmapFactory.decodeResource(view.getResources(), R.drawable.icon)
        val FinalImage = ReadyFunction.MergeImages(bitmapImage,waterMark)
        /////
        WhatsAppShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.whatsapp")
        }
        /////
        FacebookShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.facebook.katana")
        }
        /////
        MessengerShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.facebook.orca")
        }
        /////
        InstagramShare?.setOnClickListener {
            ReadyFunction.instagramShare(FinalImage!!,context)
        }
        /////
        TwitterShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.twitter.android")
        }
        /////
        SnapChatShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.snapchat.android")
        }
        /////
        TelegramShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"org.telegram.messenger")
        }
        /////
        EmailShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.google.android.gm")
        }
        /////
        DiscordShare?.setOnClickListener {
            ReadyFunction.ShareIt(FinalImage!!,context,"com.discord")
        }
        /////
        btnCloseShare?.setOnClickListener {
            dialog.dismiss()
        }
    }
}


interface talk {
    fun senddata(n: Needyresponse)
}