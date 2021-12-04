package tn.esprit.leagueoflegendrecyclerview.championList

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.net.Uri
import android.preference.PreferenceManager
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.leagueoflegendrecyclerview.data.Inofrmations
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Util.CustomDialog
import tn.yassin.oneblood.Util.PlayMusic
import java.text.DecimalFormat
import java.util.*


class InformationAdapter(val HospitalsList: MutableList<Inofrmations>) :
    RecyclerView.Adapter<InofrmationsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InofrmationsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_locations, parent, false)
        return InofrmationsViewHolder(view)

    }

    override fun getItemCount() = HospitalsList.size

    private fun calculateDistance(
        fromLong: Double,
        fromLat: Double,
        toLong: Double,
        toLat: Double
    ): Double {
        val d2r = Math.PI / 180
        val dLong = (toLong - fromLong) * d2r
        val dLat = (toLat - fromLat) * d2r
        val a = Math.pow(Math.sin(dLat / 2.0), 2.0) + (Math.cos(fromLat * d2r)
                * Math.cos(toLat * d2r) * Math.pow(Math.sin(dLong / 2.0), 2.0))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val d = 6367000 * c
        return Math.round(d).toDouble()
    }

    override fun onBindViewHolder(holder: InofrmationsViewHolder, position: Int) {
        val context: Context = holder.itemView.getContext()
        val nom = HospitalsList[position].Nom
        val location = HospitalsList[position].Location
        val numero = HospitalsList[position].Numero
        val longitude = HospitalsList[position].Longitude
        val latitude = HospitalsList[position].Latitude

        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        println("Distance (KM)/(M)")
        //Get My Location (Long And Lat)
        val Mylatitude = sharedPreference.getString("Mylatitude", null)
        val Mylongitude = sharedPreference.getString("Mylongitude", null)
        //println("ookk Lon ==>>> " + Mylongitude)
        //println("ookk Lat ==>>> " + Mylatitude)

        val DistanceIS = calculateDistance(
            Mylongitude!!.toDouble(),
            Mylatitude!!.toDouble(),
            longitude.toDouble(),
            latitude.toDouble()
        )
        // println("DistanceIS ===>>> " + DistanceIS)

        var ValueDistanceKM = (DistanceIS * 0.001)
        val df = DecimalFormat("########.000")
        val str: String = df.format(ValueDistanceKM)
        ValueDistanceKM = str.replace(',', '.').toDouble()

        //Set Values
        holder.Nom.text = nom
        holder.Location.text = location
        holder.Numero.text = numero
        if (DistanceIS > 1000) {
            holder.DistanceKM.text = ValueDistanceKM.toString() + " Km"
        } else {
            holder.DistanceKM.text = DistanceIS.toString() + " M"
        }


        //
        //Gradient (Code)
/*        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.parseColor("#2b5876"),
                Color.parseColor("#2b5876"))
        );
        gradientDrawable.cornerRadius = 0f;*/

        //Set Color (Cadre)
        if (position % 2 == 0) {
            holder.Background.setBackgroundColor(Color.parseColor("#181838"))
        } else {
            holder.Background.setBackgroundColor(Color.parseColor("#363F4D"))  // replace normalColor with your default color.
        }
        /// Set Color Background
        holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //Click On items -->>> Open PopUp
        holder.itemView.setOnClickListener { //Show Popup With Information Hospitals
            PopUp(holder.itemView, nom, location, numero)
        }

        //animation Items RecyclerView
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
        //holder.itemView.setVisibility(View.VISIBLE)
        holder.itemView.startAnimation(animation)

    }
}

fun PopUpDialog(context: Context, Nom: String, Location: String, Numero: String) {
    //Show PopUpp
    val factory = LayoutInflater.from(context)
    val view: View = factory.inflate(R.layout.dialogmap, null)
    val msg = CustomDialog()
    msg.ShowTheCustomPopUp(context, view)
    // End Show PopUpp
    val sound = PlayMusic()
    sound.SoundNotification(context)

    //Action On PopUp
    val btnLocationHospital = view.findViewById<Button>(R.id.MapDialog) as? Button
    btnLocationHospital?.setOnClickListener(object : View.OnClickListener {
        override fun onClick(view: View?) {
            //Check App is Not Disabled
            val ai: ApplicationInfo =
                context.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0)
            val appStatus = ai.enabled

            println("Maaaaaaaaaaaaaaaaaaaaapppppppppppppppppppppppppp")
            if (isAppInstalled(
                    "com.google.android.apps.maps",
                    context
                ) && appStatus
            ) { // Check if Google Map existe and not disabled
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=$Nom+$Location")
                );
                context.startActivity(browserIntent)
            } else { //Open Map in Browser
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/place/$Nom+$Location/@36.8049507,10.1529202,16z/data=!4m9!1m2!2m1!1sbanque+du+sang!3m5!1s0x12fd339e90c9f481:0xd9b67502b8aeb544!8m2!3d36.8049507!4d10.1572976!15sCg5iYW5xdWUgZHUgc2FuZ5IBCmJsb29kX2Jhbms")
                );
                context.startActivity(browserIntent)
            }

        }
    })
    val btnCallHospitall = view.findViewById<Button>(R.id.OkaybtnDialog) as? Button
    btnCallHospitall?.setOnClickListener(object : View.OnClickListener {
        override fun onClick(view: View?) {
            println("Caaaaaaaaaaaaaaaaaaaaaaaaalllllllllllllllll")
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$Numero")
            context.startActivity(intent)
        }
    })
    //END Action On PopUp

    // Set Values
    val NomHospital = view.findViewById<TextView>(R.id.NomHospital) as? TextView
    val LocationHospital = view.findViewById<TextView>(R.id.LocationHospital) as? TextView
    val NumeroHospital = view.findViewById<TextView>(R.id.NumeroHospital) as? TextView

    //Set Values
    NomHospital?.text = Nom
    LocationHospital?.text = Location
    NumeroHospital?.text = Numero
    //END Set Values
}

fun PopUp(view: View, Nom: String, Location: String, Numero: String) {
    PopUpDialog(view.context, Nom, Location, Numero)
}

fun isAppInstalled(packageName: String, context: Context): Boolean {
    return try {
        val packageManager = context.packageManager
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }


}


