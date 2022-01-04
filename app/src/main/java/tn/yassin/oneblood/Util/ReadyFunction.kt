package tn.yassin.oneblood.Util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.ConnectivityManager
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import tn.yassin.oneblood.R
import android.net.Uri
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import java.io.ByteArrayOutputStream


class ReadyFunction {



    fun isOnline(context:Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun setPhotodeProfil(MyName:String,imgProfile: ImageView)
    {
        if (MyName?.toLowerCase()!!.startsWith("a")) { imgProfile?.setImageResource(R.drawable.a)}
        if (MyName?.toLowerCase()!!.startsWith("b")) { imgProfile?.setImageResource(R.drawable.b)}
        if (MyName?.toLowerCase()!!.startsWith("c")) { imgProfile?.setImageResource(R.drawable.c)}
        if (MyName?.toLowerCase()!!.startsWith("d")) { imgProfile?.setImageResource(R.drawable.d)}
        if (MyName?.toLowerCase()!!.startsWith("e")) { imgProfile?.setImageResource(R.drawable.e)}
        if (MyName?.toLowerCase()!!.startsWith("f")) { imgProfile?.setImageResource(R.drawable.f)}
        if (MyName?.toLowerCase()!!.startsWith("g")) { imgProfile?.setImageResource(R.drawable.g)}
        if (MyName?.toLowerCase()!!.startsWith("h")) { imgProfile?.setImageResource(R.drawable.h)}
        if (MyName?.toLowerCase()!!.startsWith("i")) { imgProfile?.setImageResource(R.drawable.i)}
        if (MyName?.toLowerCase()!!.startsWith("j")) { imgProfile?.setImageResource(R.drawable.j)}
        if (MyName?.toLowerCase()!!.startsWith("k")) { imgProfile?.setImageResource(R.drawable.k)}
        if (MyName?.toLowerCase()!!.startsWith("l")) { imgProfile?.setImageResource(R.drawable.l)}
        if (MyName?.toLowerCase()!!.startsWith("m")) { imgProfile?.setImageResource(R.drawable.m)}
        if (MyName?.toLowerCase()!!.startsWith("n")) { imgProfile?.setImageResource(R.drawable.n)}
        if (MyName?.toLowerCase()!!.startsWith("o")) { imgProfile?.setImageResource(R.drawable.o)}
        if (MyName?.toLowerCase()!!.startsWith("p")) { imgProfile?.setImageResource(R.drawable.p)}
        if (MyName?.toLowerCase()!!.startsWith("q")) { imgProfile?.setImageResource(R.drawable.q)}
        if (MyName?.toLowerCase()!!.startsWith("r")) { imgProfile?.setImageResource(R.drawable.r)}
        if (MyName?.toLowerCase()!!.startsWith("s")) { imgProfile?.setImageResource(R.drawable.s)}
        if (MyName?.toLowerCase()!!.startsWith("t")) { imgProfile?.setImageResource(R.drawable.t)}
        if (MyName?.toLowerCase()!!.startsWith("u")) { imgProfile?.setImageResource(R.drawable.u)}
        if (MyName?.toLowerCase()!!.startsWith("v")) { imgProfile?.setImageResource(R.drawable.v)}
        if (MyName?.toLowerCase()!!.startsWith("w")) { imgProfile?.setImageResource(R.drawable.w)}
        if (MyName?.toLowerCase()!!.startsWith("x")) { imgProfile?.setImageResource(R.drawable.x)}
        if (MyName?.toLowerCase()!!.startsWith("y")) { imgProfile?.setImageResource(R.drawable.y)}
        if (MyName?.toLowerCase()!!.startsWith("z")) { imgProfile?.setImageResource(R.drawable.z)}
        //else {imgProfile?.setImageResource(R.drawable.angel)}
    }

     fun setFadeAnimation(view: View) {
        val FADE_DURATION = 1000 //FADE_DURATION in milliseconds
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

    fun autoCompleteListAdapter(
        context: Context,
        list: Array<String>,
        autoCompleteTextView: AutoCompleteTextView
    ) {
        val arrayAdapter = ArrayAdapter(context, R.layout.dropdown_item, list)
        autoCompleteTextView.setAdapter(arrayAdapter)
    }


    fun changeFragment(newFragment: Fragment?, context: Context) {
        val transaction: FragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, newFragment!!)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun ScreenShot(view: View): Bitmap {
        val bitmapOne = Bitmap.createBitmap(550, 432, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapOne)
        canvas.drawColor(Color.parseColor("#ff6366"))
        view.draw(canvas)

        //val waterMark = BitmapFactory.decodeResource(view.getResources(), R.drawable.icon)
        //canvas.drawBitmap(waterMark, 0f, 0f, null)
        return bitmapOne
    }


     fun MergeImages(firstImage: Bitmap, secondImage: Bitmap): Bitmap? {
        val result = Bitmap.createBitmap(firstImage.width, firstImage.height, firstImage.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(firstImage, 0f, 0f, null)
        canvas.drawBitmap(secondImage, 350f, 10f, null)
        return result
    }


     fun instagramShare(ScreenShot: Bitmap,ctx:Context) {
        val attributionLinkUrl = "https://developers.facebok.com"
        val shareIntent = Intent("com.instagram.share.ADD_TO_STORY")
        try{
            val path = MediaStore.Images.Media.insertImage(ctx?.contentResolver, ScreenShot, "", "")
            val uri = Uri.parse(path)
            shareIntent.setDataAndType(uri, "image/*")
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.putExtra("content_url", attributionLinkUrl)

            shareIntent.putExtra("top_background_color", "#33FF33");
            shareIntent.putExtra("bottom_background_color", "#FF00FF");
            ctx?.grantUriPermission("com.instagram.android", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            //startActivityForResult(shareIntent, 42)
            (ctx as Activity).startActivityForResult(shareIntent, 42)
        }
        catch (e: java.lang.Exception){
            CustomToast(ctx, "App Not installed", "RED").show()
        }
    }

    fun ShareIt(bitmap:Bitmap,context:Context,Package:String) {
        try {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path: String = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
            val imageUri = Uri.parse(path)
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "image/*"
            waIntent.setPackage(Package)
            waIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
            waIntent.putExtra(Intent.EXTRA_TEXT, "He Need Help \nplease share it \n#OneBlood")
            context.startActivity(Intent.createChooser(waIntent, "Share with"))
        } catch (e: java.lang.Exception) {
            Log.e("Error on sharing", "$e ")
            CustomToast(context, "App Not installed", "RED").show()
        }
    }

     fun hideKeyboard(view: View,context:Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }






}