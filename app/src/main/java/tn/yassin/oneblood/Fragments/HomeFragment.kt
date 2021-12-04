package tn.yassin.oneblood.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.oneblood.LoginAndSignUp.SessionManager
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.User
import tn.yassin.oneblood.Retrofit.Userresponse
import tn.yassin.oneblood.Retrofit.retrofit


class HomeFragment : Fragment() {
    private lateinit var sessionManager: SessionManager



    private var ctx: Context? = null
    private var self: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container?.context
        self = LayoutInflater.from(ctx).inflate(R.layout.home_fragment, container, false)
        sessionManager = SessionManager(ctx!!)


        val type_blood = self?.findViewById<TextView>(R.id.BloodType)
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)

        service.fetchPosts(token = "Bearer${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<Userresponse> {
                override fun onFailure(call: Call<Userresponse>, t: Throwable) {
                    // Error fetching posts
                }

                override fun onResponse(call: Call<Userresponse>, response: Response<Userresponse>) {
                    // Handle function to display posts
                    println(sessionManager.fetchAuthToken())
                  //  type_blood?.text = response.body().user.get()
                }
            })
        // Inflate the layout for this fragment
        return self
    }

}
