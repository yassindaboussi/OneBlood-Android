package tn.yassin.oneblood.Util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import tn.yassin.oneblood.R

class CustomDialog {
    fun ShowTheCustomPopUp(context: Context?,view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show()


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

