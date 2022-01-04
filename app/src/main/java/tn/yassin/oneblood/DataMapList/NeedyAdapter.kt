package tn.yassin.oneblood.DataMapList
import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Retrofit.Needyresponse
import androidx.recyclerview.widget.DiffUtil
import java.security.AccessController.getContext
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.core.content.ContextCompat
import tn.yassin.oneblood.Retrofit.Loginresponse
import tn.yassin.oneblood.Util.CustomDialog
import tn.yassin.oneblood.Util.ReadyFunction
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NeedyAdapter(var context: Context) : RecyclerView.Adapter<NeedyAdapter.ViewHolder>(),
    Filterable {

    private lateinit var UserBlood:String
    private lateinit var Userid:String
    var dataList = mutableListOf<Needyresponse>()
    var filteredNeedyList= ArrayList<Needyresponse>()
    val ReadyFunction = ReadyFunction()


    internal fun setDataList(userArrayList: ArrayList<Needyresponse>) {
        this.dataList = userArrayList
        this.filteredNeedyList = userArrayList
        notifyDataSetChanged()
    }
    internal fun addDataList(data: Needyresponse) {
        this.dataList.add(data)
        notifyDataSetChanged()
    }

    // Provide a direct reference to each of the views with data items

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Situation : TextView
        val Nom : TextView
        val Location:TextView
        val Numero:TextView
        val BloodG:TextView
        val DatePost:TextView
        val Background : LinearLayout
        val Button:Button
        val imgProfile : ImageView
        init {
            Situation = itemView.findViewById<TextView>(R.id.SituationNeedy)
            imgProfile = itemView.findViewById<ImageView>(R.id.imgProfile)
            Nom= itemView.findViewById<TextView>(R.id.nomNeedy)
            Location = itemView.findViewById<TextView>(R.id.locationNeedy)
            Numero = itemView.findViewById<TextView>(R.id.phoneNeedy)
            BloodG  = itemView.findViewById<TextView>(R.id.bloodGroup)
            DatePost  = itemView.findViewById<TextView>(R.id.datePostNeedy)
            Background = itemView.findViewById<LinearLayout>(R.id.carditemNeedy)
            Button= itemView.findViewById<Button>(R.id.buttonEditCard)
        }

    }
    //  total count of items in the list
    override fun getItemCount() = filteredNeedyList.size

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeedyAdapter.ViewHolder {

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        UserBlood = sharedPreference.getString("blood", null).toString()
        Userid = sharedPreference.getString("id", null).toString()

        // Inflate the custom layout
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_needly, parent, false)
        return ViewHolder(view)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: NeedyAdapter.ViewHolder, position: Int) {

        // Get the data model based on position
        var data = filteredNeedyList[position]

        if(Userid==data.getPostedby())
        {
            holder.Button.visibility = View.VISIBLE
            holder.imgProfile.setLayoutParams(TableLayout.LayoutParams(370,140,1f))
            val layoutParams = (holder.imgProfile?.layoutParams as? ViewGroup.MarginLayoutParams)
            layoutParams?.setMargins(30, 7, 0, 0)
            holder.imgProfile?.layoutParams = layoutParams
        }
        else
        {
            holder.Button.visibility = View.GONE
            holder.imgProfile.setLayoutParams(TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, 140,1f))
            val layoutParams = (holder.imgProfile?.layoutParams as? ViewGroup.MarginLayoutParams)
            layoutParams?.setMargins(0, 7, 0, 0)
            holder.imgProfile?.layoutParams = layoutParams
        }

        // Set item views based on your views and data model
        holder.Nom.text = data.nom
        holder.Location.text = data.location
        holder.Numero.text = data.phone.toString()
        holder.BloodG.text = data.blood
        holder.DatePost.text = data.datepost
        holder.Situation.text = data.situation
        ////////////////////////////////////////////////////////////////////////////////
        if (data.avatar?.isNotEmpty() == true && !data.avatar!!.startsWith("avat") ) {
            ReadyFunction.setPhotodeProfil(data.avatar!!.toLowerCase(),holder.imgProfile !!)
        }
        if( data.avatar?.isNotEmpty() == true && data.avatar!!.startsWith("avat")){
            val PACKAGE_NAME = context?.getPackageName();
            holder.imgProfile ?.setImageResource(context.getResources().getIdentifier(data.avatar.toString(), "drawable", PACKAGE_NAME));
        }
        ////////////////////////////////////////////////////////////////////////////////////
        if(data.situation=="Danger")
        {
            holder.Situation.setTextColor(Color.parseColor("#F05223"));
        }
        if(data.situation=="Normal")
        {
            holder.Situation.setTextColor(Color.parseColor("#8BC34A"));
        }

        //Set Color (Cadre)
        if (position % 2 == 0) {
            holder.Background.setBackgroundColor(Color.parseColor("#181838"))
        } else {
            holder.Background.setBackgroundColor(Color.parseColor("#381818"))
        }
        if(data.situation=="Closed")
        {
            holder.Background.setBackgroundColor(Color.parseColor("#183821")) //#1f3818  #596c75
            holder.Situation.setTextColor(Color.parseColor("#596c75"));
        }
        holder.itemView.setOnClickListener { //Show Popup With Information Hospitals
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString("PostNeedyid", data.getId())
            editor.putString("NeedyName", data.getNom())
            editor.putString("NeedyLocation", data.getLocation())
            editor.putString("NeedyBlood", data.getBlood())
            editor.putString("Needyphone", data.getPhone().toString())
            editor.putString("NeedySituation", data.getSituation())
            editor.putString("NeedyPostedBy", data.getPostedby())
            editor.apply()  //Save Data
            ///
            PopUpDetails(holder.itemView)
            //ScreenShot
            val fileOutputStream = ByteArrayOutputStream()
            ReadyFunction.ScreenShot(holder.itemView)?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            val compressImage: ByteArray = fileOutputStream.toByteArray()
            val sEncodedImage: String = Base64.encodeToString(compressImage, Base64.DEFAULT)

            println("sEncodedImage ===>>>>>> "+sEncodedImage)
            val preferencess = PreferenceManager.getDefaultSharedPreferences(context)
            val editorr = preferencess.edit()
            editorr.putString("NeedyScreenShot", sEncodedImage)
            editorr.apply()  //Save Data
        }

        holder.Button.setOnClickListener {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+data.getId()+" "+data.getNom())
            editor.putString("PostNeedyid", data.getId())
            editor.putString("NeedyName", data.getNom())
            editor.putString("NeedyLocation", data.getLocation())
            editor.putString("NeedyBlood", data.getBlood())
            editor.putString("Needyphone", data.getPhone().toString())
            editor.putString("NeedySituation", data.getSituation())
            editor.apply()  //Save Data
            //
            PopUp(holder.Button) //Show Option Post
        }

        //animation Items RecyclerView
       // val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
       // holder.itemView.startAnimation(animation)
        val currentDate: String = SimpleDateFormat("dd MMMM HH:mm",Locale.ENGLISH).format(Date())
        val currentDateandTime: String = currentDate.format(Date())

/*
        var qq=data.nom.toString()
        if(currentDateandTime.equals(data.datepost)){
            qq=""
            System.out.println("Equaaaaaallllllllllllllllllllsssss ====>>>> "+data.nom);
        }
*/

    }


    fun PopUpDialog(context: Context) {
        //Show PopUpp
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.optionspopup, null)
        val msg = CustomDialog()
        msg.ShowOptionPopup(context, view)
    }

    fun PopUpDetailsNeedy(context: Context) {
        //Show PopUpp
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.detailsneedy, null)
        val msg = CustomDialog()
        msg.ShowDetailsNeedy(context, view)
    }

    fun PopUp(view: View) {
        PopUpDialog(view.context)
    }

    fun PopUpDetails(view: View) {
        PopUpDetailsNeedy(view.context)
    }


    override fun getFilter(): Filter? {
        return object : Filter() {
            protected override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val searchString = charSequence.toString()
                if (searchString.isEmpty()) {
                    filteredNeedyList = dataList as ArrayList<Needyresponse>
                } else {
                    val tempFilteredList: ArrayList<Needyresponse> = ArrayList()
                    for (user in dataList) {

                        //*** search Options
                        if(searchString!="wadasxsqsqfjhf")
                        {
                        if (user.getNom()!!.toLowerCase().contains(searchString)||user.getLocation()!!.toLowerCase().contains(searchString)||user.getBlood()!!.toLowerCase().contains(searchString)) {
                            tempFilteredList.add(user)
                        }
                        }
                        ///End Of Search
                        if(searchString=="wadasxsqsqfjhf")
                        {
///**Filtre**** Blood Compatible
if (UserBlood.toLowerCase() == "a+") {
if (user.getBlood()!!.toLowerCase().contains("a+") || user.getBlood()!!.toLowerCase().contains("ab+")) {
tempFilteredList.add(user)}}
////////////
if (UserBlood.toLowerCase() == "o+") {
if (user.getBlood()!!.toLowerCase().contains("o+") || user.getBlood()!!.toLowerCase().contains("a+") || user.getBlood()!!.toLowerCase().contains("b+") || user.getBlood()!!.toLowerCase().contains("ab+")) {
tempFilteredList.add(user)}}
///////////
if (UserBlood.toLowerCase() == "b+") {
if (user.getBlood()!!.toLowerCase().contains("b+") || user.getBlood()!!.toLowerCase().contains("ab+")) {
tempFilteredList.add(user)}}
///////////
if (UserBlood.toLowerCase() == "ab+")
{if (user.getBlood()!!.toLowerCase().contains("ab+")) {
tempFilteredList.add(user)}}
////////////
if (UserBlood.toLowerCase() == "a-") {
if (user.getBlood()!!.toLowerCase().contains("a+") || user.getBlood()!!.toLowerCase().contains("a-") || user.getBlood()!!.toLowerCase().contains("ab+") || user.getBlood()!!.toLowerCase().contains("ab-")) {
tempFilteredList.add(user)}}
////////////
if (UserBlood.toLowerCase() == "b-") {
if (user.getBlood()!!.toLowerCase().contains("b+") || user.getBlood()!!.toLowerCase().contains("b-") || user.getBlood()!!.toLowerCase().contains("ab+") || user.getBlood()!!.toLowerCase().contains("ab-")) {
tempFilteredList.add(user)}}
////////////
if (UserBlood.toLowerCase() == "ab-") {
if (user.getBlood()!!.toLowerCase().contains("ab+") || user.getBlood()!!.toLowerCase().contains("ab-")) {
tempFilteredList.add(user)}}
////////////
if (UserBlood.toLowerCase() == "o-" || UserBlood.toLowerCase() == "idk") {
if (user.getBlood()!!.toLowerCase().contains("a+") || user.getBlood()!!.toLowerCase().contains("a-")||
    user.getBlood()!!.toLowerCase().contains("b+") || user.getBlood()!!.toLowerCase().contains("b-")||
    user.getBlood()!!.toLowerCase().contains("ab+") || user.getBlood()!!.toLowerCase().contains("ab-")||
    user.getBlood()!!.toLowerCase().contains("o+") || user.getBlood()!!.toLowerCase().contains("o-")) {
tempFilteredList.add(user)}}
                    }
                    }
//***End of Filtre

                    filteredNeedyList = tempFilteredList
                }
                ////////////
                ////////////
                val filterResults = FilterResults()
                filterResults.values = filteredNeedyList
                return filterResults
            }

            protected override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredNeedyList = filterResults.values as ArrayList<Needyresponse>
                notifyDataSetChanged()

            }
        }
    }



}