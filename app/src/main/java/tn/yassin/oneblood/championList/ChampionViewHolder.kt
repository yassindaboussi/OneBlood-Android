package tn.esprit.leagueoflegendrecyclerview.championList

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.yassin.oneblood.R

class ChampionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val Nom : TextView = itemView.findViewById<TextView>(R.id.Nom)
    val Location : TextView = itemView.findViewById<TextView>(R.id.Location)
    val Numero : TextView = itemView.findViewById<TextView>(R.id.Numero)
    val Background : LinearLayout = itemView.findViewById<LinearLayout>(R.id.Carditem)
}