package tn.yassin.oneblood.Fragments

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import tn.yassin.oneblood.DataMapList.NeedyAdapter
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Util.CustomDialog
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import tn.yassin.oneblood.Retrofit.Needyresponse
import tn.yassin.oneblood.Retrofit.Request
import tn.yassin.oneblood.Retrofit.retrofit
import android.content.pm.PackageManager
import android.os.Build
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.internal.ContextUtils
import kotlinx.android.synthetic.main.addneedy.*
import tn.yassin.oneblood.Util.CustomToast
import tn.yassin.oneblood.Util.talk
import android.view.MenuInflater
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import android.content.SharedPreferences
import android.text.method.PasswordTransformationMethod
import tn.yassin.oneblood.Retrofit.Loginresponse
import tn.yassin.oneblood.Util.ReadyFunction
import java.util.*


class SearchFragment : Fragment(), talk {

    lateinit var recylcerNeedy: RecyclerView
    private lateinit var  NeedyAdapter: NeedyAdapter
    var BesoinModels: ArrayList<Needyresponse> = ArrayList<Needyresponse>()
   // private lateinit var fabAddPostNeedly:FloatingActionButton

    private lateinit var SwipeRefreshSearch: SwipeRefreshLayout
    private lateinit var searchView:SearchView
    private lateinit var BtnAddNeedy: Button
    private lateinit var LigneS:View
    private lateinit var btnMatchBloodGroupe:Button
    private lateinit var btnShowMyPosts:Button
    private lateinit var bntSituationNeedy:Button
    val ReadyFunction = ReadyFunction()

    private var ctx: Context? = null
    private var self: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container?.context
        self = LayoutInflater.from(ctx).inflate(R.layout.search_fragment, container, false)

        ////////////////////////////////////////////////////////////////////////////////////////////
        //  val Iam = self?.findViewById<TextView>(R.id.Iam)

        return self
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recylcerNeedy = view.findViewById(R.id.recyclerSearch)
        //fabAddPostNeedly=view.findViewById(R.id.fab)
        SwipeRefreshSearch=view.findViewById(R.id.SwipeRefreshSearch)
        searchView=view.findViewById(R.id.searchView)
        BtnAddNeedy=view.findViewById(R.id.ButtonAddNeedy)
        LigneS =view.findViewById(R.id.LigneS)
        btnMatchBloodGroupe=view.findViewById(R.id.btnMatchBloodGroupe)
        btnShowMyPosts=view.findViewById(R.id.btnShowMyPosts)
        bntSituationNeedy=view.findViewById(R.id.bntSituationNeedy)

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val MyID = sharedPreference.getString("id", null)
        val MyName = sharedPreference.getString("name", null)
        val MyBlood = sharedPreference.getString("blood", null)
        val MyAdresse = sharedPreference.getString("adress", null)
        val MyEmail = sharedPreference.getString("email", null)
        val MyPhone = sharedPreference.getString("phone", null)
        val MyType = sharedPreference.getString("usertype", null)
        val MyAvatar = sharedPreference.getString("avatar", null)

        //recylcerNeedy.layoutManager = GridLayoutManager(getActivity()?.getApplicationContext(),2)
        recylcerNeedy.setLayoutManager(StaggeredGridLayoutManager(2, 1))
        NeedyAdapter = NeedyAdapter(requireActivity().getApplicationContext())
        recylcerNeedy.adapter = NeedyAdapter

        BtnAddNeedy.setOnClickListener {
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.addneedy, null)
            val msg = CustomDialog(this)
            msg.ShowDialogAddNeedy(context, view)
        }

        if (MyType == "Donor") {
            BtnAddNeedy.setVisibility(View.GONE);
            LigneS.setVisibility(View.GONE);
            //Fix View Position (Design)
            searchView.setLayoutParams(TableLayout.LayoutParams(
                    ActionBar.LayoutParams.FILL_PARENT,
                    ActionBar.LayoutParams.FILL_PARENT,
                    1f
                ))
        }
        if (MyType == "Needy") {
            BtnAddNeedy.setVisibility(View.VISIBLE);
            LigneS.setVisibility(View.VISIBLE);
        }

        GetAllBesoin()
        SwipeRefreshSearch.setOnRefreshListener {
            GetAllBesoin()                // refresh your list contents somehow
            SwipeRefreshSearch.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }

        OnSearch()
        btnMatchBloodGroupe.setOnClickListener {
            NeedyAdapter.getFilter()?.filter("wadasxsqsqfjhf")
        }

        ShowCase() //Show Case Slide More to show more ...

        btnShowMyPosts.setOnClickListener {
            GetAllMyPost() //Show only my Posts
        }
        var showingFirst = true
        bntSituationNeedy.setOnClickListener { //Show Danger / Normal
                if(showingFirst == true){
                    ShowSituation("Danger")
                    showingFirst = false;
                }else{
                    ShowSituation("Normal")
                    showingFirst = true;
                }
        }

        RefreshAuto()
    }

    fun RefreshAuto()
    {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                GetAllBesoin()
            }
        }, 0, 6000) //put here time 1000 milliseconds=1 second
    }


    fun GetAllBesoin() {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)
        val call: Call<List<Needyresponse>> = service.GetAllBesoin()
        call.enqueue(object : Callback<List<Needyresponse>> {
            override fun onResponse(call: Call<List<Needyresponse>>, response: Response<List<Needyresponse>>) {
                BesoinModels = ArrayList<Needyresponse>(response.body())
                NeedyAdapter.setDataList(BesoinModels)

            }
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<List<Needyresponse>>, t: Throwable) {
                ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                    CustomToast(requireContext(), "Something went wrong!","RED").show()
                })
                // Log.d("***", "Opppsss" + t.message)
            }
        })
    }


    fun GetAllMyPost() {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val MyID = sharedPreference.getString("id", null)
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)
        val call: Call<List<Needyresponse>> = service.GetAllMyPost(MyID!!)
        call.enqueue(object : Callback<List<Needyresponse>> {
            override fun onResponse(call: Call<List<Needyresponse>>, response: Response<List<Needyresponse>>) {
                BesoinModels = ArrayList<Needyresponse>(response.body())
                NeedyAdapter.setDataList(BesoinModels)
            }
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<List<Needyresponse>>, t: Throwable) {
                ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                    CustomToast(requireContext(), "Something went wrong!","RED").show()
                })
                // Log.d("***", "Opppsss" + t.message)
            }
        })
    }

    fun ShowSituation(Situation:String) {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val retrofi: Retrofit = retrofit.getInstance()
        val service: Request = retrofi.create(Request::class.java)
        val call: Call<List<Needyresponse>> = service.ShowSituation(Situation)
        call.enqueue(object : Callback<List<Needyresponse>> {
            override fun onResponse(call: Call<List<Needyresponse>>, response: Response<List<Needyresponse>>) {
                BesoinModels = ArrayList<Needyresponse>(response.body())
                NeedyAdapter.setDataList(BesoinModels)
            }
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<List<Needyresponse>>, t: Throwable) {
                ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                    CustomToast(requireContext(), "Something went wrong!","RED").show()
                })
                // Log.d("***", "Opppsss" + t.message)
            }
        })
    }


    override fun senddata(n: Needyresponse) {
        NeedyAdapter.addDataList(n)
    }


    fun OnSearch() {
        //searchView.setIconifiedByDefault(true);
        //searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();
        //searchView.requestFocusFromTouch();

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //searchView.clearFocus()
                //searchView.requestFocus();
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                NeedyAdapter.getFilter()?.filter(newText)
                return true
            }

        })
    }

    fun ShowCase()
    {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        if (sharedPreference!!.getBoolean("firstrun", true)) {
            sharedPreference!!.edit().putBoolean("firstrun", false).commit();

            TapTargetView.showFor(activity,
                TapTarget.forView(searchView, "Informations",
                    "You can swipe down this page to show more Needy person"
                ).outerCircleColor(R.color.white)
                    .outerCircleAlpha(0.96f)
                    .targetCircleColor(R.color.Spotlight)
                    .titleTextSize(25)
                    .titleTextColor(R.color.white)
                    .descriptionTextSize(20)
                    .descriptionTextColor(R.color.black)
                    .textColor(R.color.black)
                    .textTypeface(Typeface.SANS_SERIF)
                    .dimColor(R.color.black)
                    .drawShadow(true)
                    .cancelable(false)
                    .tintTarget(true)
                    .transparentTarget(false)
                    .targetRadius(70),
                object : TapTargetView.Listener() {
                    override fun onTargetClick(view: TapTargetView?) {
                        super.onTargetClick(view)
                        // val intent = Intent (this@MainActivity,SecondActivity::class.java)
                        // startActivity(intent)
                        //  finish()
                        sharedPreference!!.edit().putBoolean("firstrun", false).commit();
                    }
                })
        }

    }

}




