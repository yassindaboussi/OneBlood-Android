package tn.yassin.oneblood.Fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import tn.yassin.oneblood.LoginAndSignUp.SessionManager
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Retrofit.Loginresponse
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit
import tn.yassin.oneblood.Util.PlayMusic
import tn.yassin.oneblood.Util.ReadyFunction


class HomeFragment : Fragment() {
    private lateinit var sessionManager: SessionManager


    private var ctx: Context? = null
    private var self: View? = null
    val function = ReadyFunction()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container?.context
        self = LayoutInflater.from(ctx).inflate(R.layout.home_fragment, container, false)

        // Inflate the layout for this fragment
        return self
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val type_blood = self?.findViewById<TextView>(R.id.BloodType)
        val UserType = self?.findViewById<TextView>(R.id.TypeUseris)
        val imgProfile = self?.findViewById<ImageView>(R.id.imgProfile)
        //////////
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val MyBlood = sharedPreference.getString("blood", null)
        val MyType = sharedPreference.getString("usertype", null)
        val MyName = sharedPreference.getString("name", null)
        val MyAvatar = sharedPreference.getString("avatar", null)
        //////////
        type_blood?.text = MyBlood
        UserType?.text = MyType
        //Set Picture to User
        if (MyAvatar?.isNotEmpty() == true && !MyAvatar.startsWith("avat") ) {
            function.setPhotodeProfil(MyAvatar.toLowerCase(),imgProfile!!)
        }
        if( MyAvatar?.isNotEmpty() == true && MyAvatar.startsWith("avat")){
            val PACKAGE_NAME = context?.getPackageName();
            imgProfile ?.setImageResource(getResources().getIdentifier(MyAvatar.toString(), "drawable", PACKAGE_NAME));
        }
        //////////
    }
}
