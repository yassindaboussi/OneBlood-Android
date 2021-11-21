package tn.yassin.oneblood.Util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.view.View
import tn.yassin.oneblood.R
import java.net.URI
import java.security.AccessController.getContext

class PlayMusic {
    var mMediaPlayer: MediaPlayer? = null
    fun SoundNotification(context: Context?) {
        mMediaPlayer = MediaPlayer.create(context, R.raw.soundialog)
        mMediaPlayer!!.start()
    }
    fun SoundCall(context: Context?) {
        mMediaPlayer = MediaPlayer.create(context, R.raw.soundcall)
        mMediaPlayer!!.start()
    }
}