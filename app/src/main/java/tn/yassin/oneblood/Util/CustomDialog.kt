package tn.yassin.oneblood.Util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import tn.yassin.oneblood.R

class CustomDialog {

    fun ShowTheCustomPopUp(context: Context?,view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
    }

    fun ShowDialogNoConnection(context: Context?,view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()
        val sound = PlayMusic()
        sound.SoundNotification(context!!)
        val btnigotit = view.findViewById<Button>(R.id.OkaybtnDialog) as? Button
        btnigotit?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                println("Hamdoulllllllaaaaah khedmet")
                dialog.dismiss()
            }
        })
    }

}

/*class CustomDialog(context: Context?,view: View) : AlertDialog.Builder(context!!) {
    init {
        val inflater = LayoutInflater.from(context)
        setView(view)
    }
}*/

/*
class CustomDialog(context: Context, view: View) {
    fun CustomDialog(context: Context?, view: View) {
        val dialog = AlertDialog.Builder(context!!)
        dialog.setView(view)
        dialog.show()
    }
}
*/

