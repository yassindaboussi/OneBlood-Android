package tn.esprit.leagueoflegendrecyclerview.championList

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.view.*
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.leagueoflegendrecyclerview.data.Champion
import tn.yassin.oneblood.R

class ChampionAdapter(val championsList: MutableList<Champion>) :
    RecyclerView.Adapter<ChampionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_locations, parent, false)
        return ChampionViewHolder(view)

    }

    override fun getItemCount() = championsList.size

    override fun onBindViewHolder(holder: ChampionViewHolder, position: Int) {
        val nom = championsList[position].Nom
        val location = championsList[position].Location
        val numero = championsList[position].Numero

        holder.Nom.text = nom
        holder.Location.text = location
        holder.Numero.text = numero

        if (position % 2 == 0) {
            holder.Background.setBackgroundColor(Color.parseColor("#181838"))
        } else {
            holder.Background.setBackgroundColor(Color.parseColor("#381818"))  // replace normalColor with your default color.
        }

        holder.itemView.setOnClickListener { //Show Popup
            PopUp(holder.itemView, nom, location, numero)
/*            val intent = Intent(holder.itemView.context, PopUP::class.java)
            intent.apply {
                putExtra(Nom, nom)
                putExtra(Location, location)
                putExtra(Numero, numero)
            }
            holder.itemView.context.startActivity(intent)*/

        }
    }
}

fun PopUpDialog(context: Context, Nom: String, Location: String, Numero: String) {
    val alertadd: AlertDialog.Builder = AlertDialog.Builder(context)
    val factory = LayoutInflater.from(context)
    val view: View = factory.inflate(R.layout.popupdialog, null)
    alertadd.setView(view)
    alertadd.show()

    val CallDialog = view.findViewById<Button>(R.id.CallDialog) as? Button
    CallDialog?.setOnClickListener(object : View.OnClickListener {
        override fun onClick(view: View?) {
            println("Caaaaaaaaaaaaaaaaaaaaaaaaalllllllllllllllll")
        }
    })
    val LocationDialog = view.findViewById<Button>(R.id.MapDialog) as? Button
    LocationDialog?.setOnClickListener(object : View.OnClickListener {
        override fun onClick(view: View?) {
            val ai: ApplicationInfo = context.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0)
            val appStatus = ai.enabled

            println("Maaaaaaaaaaaaaaaaaaaaapppppppppppppppppppppppppp")
            if (isAppInstalled("com.google.android.apps.maps", context) && appStatus) { // Check if Google Map existe and not disabled
                val browserIntent = Intent(
                    android.content.Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=$Nom+$Location")
                );
                context.startActivity(browserIntent)
            }
            else
            {
                val browserIntent = Intent(
                    android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/place/$Nom+$Location/@36.8049507,10.1529202,16z/data=!4m9!1m2!2m1!1sbanque+du+sang!3m5!1s0x12fd339e90c9f481:0xd9b67502b8aeb544!8m2!3d36.8049507!4d10.1572976!15sCg5iYW5xdWUgZHUgc2FuZ5IBCmJsb29kX2Jhbms")
                );
                context.startActivity(browserIntent)
            }
        }
    })


    val Nominpopup = view.findViewById<TextView>(R.id.Nominpopup) as? TextView
    val Locationinpopup = view.findViewById<TextView>(R.id.Locationinpopup) as? TextView
    val Numeroinpopup = view.findViewById<TextView>(R.id.Numeroinpopup) as? TextView
    Nominpopup?.text = Nom
    Locationinpopup?.text = Location
    Numeroinpopup?.text = Numero
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

fun PopUp(view: View, Nom: String, Location: String, Numero: String) {
    PopUpDialog(view.context, Nom, Location, Numero)
}

