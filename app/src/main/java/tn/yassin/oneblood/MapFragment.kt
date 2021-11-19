package tn.yassin.oneblood

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.leagueoflegendrecyclerview.championList.ChampionAdapter
import tn.esprit.leagueoflegendrecyclerview.data.Champion


class MapFragment : Fragment() {
    lateinit var recylcerChampion: RecyclerView
    lateinit var recylcerChampionAdapter: ChampionAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
      //  val myWebView: WebView = view.findViewById(R.id.webVieww)
      //  myWebView.loadUrl("https://www.google.com/maps/d/u/0/edit?mid=1mO-6BPWmj_pgw8iFg4AbTcZWHbNkBQJX&usp=sharing")


        recylcerChampion = view.findViewById(R.id.recyclerLocations)

        var champList : MutableList<Champion> = ArrayList()

        champList.add(Champion(Nom = "Centre National de Transfusion Sanguine", Location = "Rue Djebel Lakhdar 1006 Tunis" , Numero = "71.573.884"))
        


        recylcerChampionAdapter = ChampionAdapter(champList)

        recylcerChampion.adapter = recylcerChampionAdapter

        recylcerChampion.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)


    }

}