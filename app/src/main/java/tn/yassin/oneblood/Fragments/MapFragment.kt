package tn.yassin.oneblood.Fragments

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Looper
import android.preference.PreferenceManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import tn.esprit.leagueoflegendrecyclerview.championList.InformationAdapter
import tn.esprit.leagueoflegendrecyclerview.data.Inofrmations
import tn.yassin.oneblood.R
import java.util.*
import kotlin.collections.ArrayList

private var ctx: Context? = null
private var self: View? = null

class MapFragment : Fragment() {
    lateinit var recylcerInformation: RecyclerView
    lateinit var recylcerInformationAdapter: InformationAdapter
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //  val myWebView: WebView = view.findViewById(R.id.webVieww)
        //  myWebView.loadUrl("https://www.google.com/maps/d/u/0/edit?mid=1mO-6BPWmj_pgw8iFg4AbTcZWHbNkBQJX&usp=sharing")

        recylcerInformation = view.findViewById(R.id.recyclerLocations)


/*        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        var name = preferences.getString("Name", "")
        println("My Position now ==>>> "+name)*/

        //getCurrentLocation()
        if (checkForInternet(requireContext())) { // is Cnx == true
            SetLocationUser()
        }

/*        // on refresh layout
        mSwipeRefreshLayout= view.findViewById<SwipeRefreshLayout>(R.id.recyclerLocations)
        mSwipeRefreshLayout!!.setOnRefreshListener {
            //API Calls
            if (checkForInternet(requireContext())) { // is Cnx == true
                SetLocationUser()}
        }*/

    }


    //////////////////////////////////////////




///////////////////////////////////////////////
    fun SetLocationUser() {
        //check permission
        //check internet
        if (checkForInternet(requireContext())) { // There is cnx
            if (ContextCompat.checkSelfPermission(
                    requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_REQUEST_CODE
                )
            } else {
                getCurrentLocation()
            }
        } else { // No cnx
            //Toast.makeText(this, "Need internet to share location", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                //Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getCurrentLocation() {
/*        val mySPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = mySPrefs.edit()
        editor.remove("inconnue")
        editor.apply()*/

        var MapListTunis: MutableList<Inofrmations> = ArrayList()
        var MapListBizert: MutableList<Inofrmations> = ArrayList()
        var MapListBeja: MutableList<Inofrmations> = ArrayList()
        var MapListJendouba: MutableList<Inofrmations> = ArrayList()
        var MapListNebel: MutableList<Inofrmations> = ArrayList()
        var MapListZaghouan: MutableList<Inofrmations> = ArrayList()
        var MapListKef: MutableList<Inofrmations> = ArrayList()
        var MapListSiliana: MutableList<Inofrmations> = ArrayList()
        var MapListSousse: MutableList<Inofrmations> = ArrayList()
        var MapListKairouan: MutableList<Inofrmations> = ArrayList()
        var MapListKasserine: MutableList<Inofrmations> = ArrayList()
        var MapListSidiBouzid: MutableList<Inofrmations> = ArrayList()
        var MapListSfax: MutableList<Inofrmations> = ArrayList()
        var MapListGafsa: MutableList<Inofrmations> = ArrayList()
        var MapListTozeur: MutableList<Inofrmations> = ArrayList()
        var MapListGabes: MutableList<Inofrmations> = ArrayList()
        var MapListKebili: MutableList<Inofrmations> = ArrayList()
        var MapListTataouine: MutableList<Inofrmations> = ArrayList()
        var MapListMedenine: MutableList<Inofrmations> = ArrayList()
        var MapListDjerba: MutableList<Inofrmations> = ArrayList()
        var MapListZarzis: MutableList<Inofrmations> = ArrayList()
        var MapListAll: MutableList<Inofrmations> = ArrayList()


        MapListTunis.add(
            Inofrmations(
                Nom = "Centre National de Transfusion Sanguine",
                Location = "Djebel Lakhdar Bab Sâadoun Tunis",
                Numero = "71 573 884",
                Longitude = "10.1587826",
                Latitude = "36.808302"
            )
        )
        MapListTunis.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Charles Nicolle",
                Location = "Hôpital Charles Nicolle 1006 Tunis",
                Numero = "71 578 357",
                Longitude = "10.161216399999999",
                Latitude = "36.802265399999996"
            )
        )
        MapListTunis.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital La Rabta",
                Location = "Hôpital La Rabta 1006 Tunis",
                Numero = "71 578 819",
                Longitude = "10.1545699",
                Latitude = "36.801819800000004"
            )
        )
        MapListTunis.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Habib Thameur",
                Location = "Hôpital Habib Thameur 1069 Tunis",
                Numero = "71 397 000",
                Longitude = "10.176554099999999",
                Latitude = "36.7860464"
            )
        )
        MapListTunis.add(
            Inofrmations(
                Nom = "Centre Militaire de Transfusion Sanguine",
                Location = "Ras Tabia, 2000 Le Bardo",
                Numero = "71 564 466",
                Longitude = "10.148322",
                Latitude = "36.813266"
            )
        )
////////////////////Bizert
        MapListBizert.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Bizerte",
                Location = "Hôpital Régional de Bizerte 7000 Bizerte",
                Numero = "72 422 057",
                Longitude = "9.8605322",
                Latitude = "37.2698447"
            )
        )
        MapListBizert.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Menzel Bourguiba",
                Location = "Hopital Régional de Menzel Bourguiba 7050 Menzel Bourguiba",
                Numero = "72 460 455",
                Longitude = "9.796453699999999",
                Latitude = "37.161495099999996"
            )
        )
//////////////////Beja
        MapListBeja.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Béja",
                Location = "Hôpital Régional de Béja 9000 Béja",
                Numero = "78 457 322",
                Longitude = "9.179618999999999",
                Latitude = "36.721576999999996"
            )
        )
//////////////////Jendouba
        MapListJendouba.add(
            Inofrmations(
                Nom = "Centre National de Transfusion Sanguine de Jendouba",
                Location = "Hôpital Régional de Jendouba 8100 Jendouba",
                Numero = "78 604 558",
                Longitude = "8.757964399999999",
                Latitude = "36.513932000000004"
            )
        )
//////////////////Nebel
        MapListNebel.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Nabeul",
                Location = "Hôpital Régional de Nabeul 8000 Nabeul",
                Numero = "72 285 633",
                Longitude = "10.731980799999999",
                Latitude = "36.4539014"
            )
        )
        MapListNebel.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Menzel Témime",
                Location = "Hôpital Régional de Menzel Témime 8080 Menzel Témime",
                Numero = "72 344 155",
                Longitude = "10.9976551",
                Latitude = "36.7785139"
            )
        )
        MapListNebel.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital de Circonscription de Grombalia",
                Location = "Hôpital de Circonscription de Grombalia 8030 Grombalia",
                Numero = "72 255 212",
                Longitude = "10.494985",
                Latitude = "36.595185"
            )
        )
//////////////////Zaghouan
        MapListZaghouan.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Zaghouan",
                Location = "Hôpital Régional de Zaghouan 1100 Zaghouan",
                Numero = "72 676 700",
                Longitude = "10.1321283",
                Latitude = "36.4163321"
            )
        )
//////////////////Kef
        MapListKef.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional du Kef",
                Location = "Hôpital Régional du Kef 7100 Le Kef",
                Numero = "78 228 900",
                Longitude = "8.705107",
                Latitude = "36.178057900000006"
            )
        )
//////////////////Siliana
        MapListSiliana.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Siliana",
                Location = "Hôpital Régional de Siliana 6100 Siliana",
                Numero = "78 872 800",
                Longitude = "9.3753558",
                Latitude = "36.0805504"
            )
        )
//////////////////Sousse
        MapListSousse.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Sousse",
                Location = "Hôpital Farhat Hached de Sousse 4000 Sousse",
                Numero = "73 223 311",
                Longitude = "10.627749399999999",
                Latitude = "35.8296103"
            )
        )
        MapListSousse.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Sahloul de Sousse",
                Location = "Hôpital Sahloul de Sousse 4000 Sousse",
                Numero = "73 369 411",
                Longitude = "10.5904807",
                Latitude = "35.837135599999996"
            )
        )
        MapListSousse.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Fattouma Bourguiba de Monastir",
                Location = "Hôpital Fattouma Bourguiba de Monastir 5000 Monastir",
                Numero = "73 460 411",
                Longitude = "10.833765399999999",
                Latitude = "35.7703318"
            )
        )
        MapListSousse.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Ksar Hellal",
                Location = "Hôpital Régional de Ksar Hellal 5070 Ksar Hellal",
                Numero = "73 475 275",
                Longitude = "10.8889227",
                Latitude = "35.6507551"
            )
        )
        MapListSousse.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Mahdia",
                Location = "Hôpital Régional de Mahdia 5100 Mahdia",
                Numero = "3 671 744",
                Longitude = "11.0326427",
                Latitude = "35.5100753"
            )
        )
//////////////////Kairouan
        MapListKairouan.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kairouan",
                Location = "Hôpital Régional de Kairouan 3100 Kairouan",
                Numero = "77 226 300",
                Longitude = "10.095772799999999",
                Latitude = "35.684039299999995"
            )
        )
//////////////////Kasserine
        MapListKasserine.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kasserine",
                Location = "Hôpital Régional de Kasserine 1200 Kasserine",
                Numero = "77 474 910",
                Longitude = "8.794013",
                Latitude = "35.1749796"
            )
        )
//////////////////SidiBouzid
        MapListSidiBouzid.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Sidi Bouzid",
                Location = "Hôpital Régional de Sidi Bouzid 9100 Sidi Bouzid",
                Numero = "76 632 500",
                Longitude = "9.4631963",
                Latitude = "35.0239295"
            )
        )
//////////////////Sfax
        MapListSfax.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Sfax",
                Location = "Hôpital Habib Bourguiba de Sfax 3000 Sfax",
                Numero = "74 241 511",
                Longitude = "10.7482127",
                Latitude = "34.7402677"
            )
        )
//////////////////Gafsa
        MapListGafsa.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Gafsa",
                Location = "Hôpital Régional de Gafsa 2100 Gafsa",
                Numero = "76 225 177",
                Longitude = "8.7961413",
                Latitude = "34.4200515"
            )
        )
        MapListGafsa.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Metlaoui",
                Location = "Hôpital Régional de Metlaoui 2130 Metlaoui",
                Numero = "76 241 600",
                Longitude = "8.392040399999999",
                Latitude = "34.312501499999996"
            )
        )
        //////////////////Tozeur
        MapListTozeur.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Tozeur",
                Location = "Hôpital Régional de Tozeur 2200 Tozeur",
                Numero = "76 453 400",
                Longitude = "8.1269021",
                Latitude = "33.9160202"
            )
        )
        //////////////////Gabès
        MapListGabes.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Gabès",
                Location = "Hôpital Régional de Gabès 6001 Gabès",
                Numero = "75 290 300",
                Longitude = "10.1153674",
                Latitude = "3.8634805"
            )
        )
        //////////////////Kébili
        MapListKebili.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kébili",
                Location = "Hôpital Régional de Kébili 4200 Kébili",
                Numero = "75 490 027",
                Longitude = "8.971264",
                Latitude = "33.7184713"
            )
        )
        //////////////////Tataouine
        MapListTataouine.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Tataouine",
                Location = "Hôpital Régional de Tataouine 3200 Tataouine",
                Numero = "75 870 114",
                Longitude = "10.4593787",
                Latitude = "32.9851757"
            )
        )
        //////////////////Médenine
        MapListMedenine.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Médenine",
                Location = "Hôpital Régional de Médenine 4100 Médenine",
                Numero = "75 640 044",
                Longitude = "10.4562372",
                Latitude = "33.3466181"
            )
        )
        //////////////////Djerba
        MapListDjerba.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Djerba",
                Location = "Hôpital Régional de Djerba 4180 Houmet Souk - Djerba",
                Numero = "75 650 018",
                Longitude = "10.9887719",
                Latitude = "33.8131631"
            )
        )
        //////////////////Zarzis
        MapListZarzis.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Zarzis",
                Location = "Hôpital Régional de Zarzis 4170 Zarzis",
                Numero = "75 738 026",
                Longitude = "11.0862514",
                Latitude = " 33.5414516"
            )
        )
        //////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////
        MapListAll.add(
            Inofrmations(
                Nom = "Centre National de Transfusion Sanguine",
                Location = "Djebel Lakhdar Bab Sâadoun Tunis",
                Numero = "71 573 884",
                Longitude = "10.1587826",
                Latitude = "36.808302"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Charles Nicolle",
                Location = "Hôpital Charles Nicolle 1006 Tunis",
                Numero = "71 578 357",
                Longitude = "10.161216399999999",
                Latitude = "36.802265399999996"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital La Rabta",
                Location = "Hôpital La Rabta 1006 Tunis",
                Numero = "71 578 819",
                Longitude = "10.1545699",
                Latitude = "36.801819800000004"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Habib Thameur",
                Location = "Hôpital Habib Thameur 1069 Tunis",
                Numero = "71 397 000",
                Longitude = "10.176554099999999",
                Latitude = "36.7860464"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Militaire de Transfusion Sanguine",
                Location = "Ras Tabia, 2000 Le Bardo",
                Numero = "71 564 466",
                Longitude = "10.148322",
                Latitude = "36.813266"
            )
        )
////////////////////Bizert
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Bizerte",
                Location = "Hôpital Régional de Bizerte 7000 Bizerte",
                Numero = "72 422 057",
                Longitude = "9.8605322",
                Latitude = "37.2698447"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Menzel Bourguiba",
                Location = "Hopital Régional de Menzel Bourguiba 7050 Menzel Bourguiba",
                Numero = "72 460 455",
                Longitude = "9.796453699999999",
                Latitude = "37.161495099999996"
            )
        )
//////////////////Beja
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Béja",
                Location = "Hôpital Régional de Béja 9000 Béja",
                Numero = "78 457 322",
                Longitude = "9.179618999999999",
                Latitude = "36.721576999999996"
            )
        )
//////////////////Jendouba
        MapListAll.add(
            Inofrmations(
                Nom = "Centre National de Transfusion Sanguine de Jendouba",
                Location = "Hôpital Régional de Jendouba 8100 Jendouba",
                Numero = "78 604 558",
                Longitude = "8.757964399999999",
                Latitude = "36.513932000000004"
            )
        )
//////////////////Nebel
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Nabeul",
                Location = "Hôpital Régional de Nabeul 8000 Nabeul",
                Numero = "72 285 633",
                Longitude = "10.731980799999999",
                Latitude = "36.4539014"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Menzel Témime",
                Location = "Hôpital Régional de Menzel Témime 8080 Menzel Témime",
                Numero = "72 344 155",
                Longitude = "10.9976551",
                Latitude = "36.7785139"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital de Circonscription de Grombalia",
                Location = "Hôpital de Circonscription de Grombalia 8030 Grombalia",
                Numero = "72 255 212",
                Longitude = "10.494985",
                Latitude = "36.595185"
            )
        )
//////////////////Zaghouan
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Zaghouan",
                Location = "Hôpital Régional de Zaghouan 1100 Zaghouan",
                Numero = "72 676 700",
                Longitude = "10.1321283",
                Latitude = "36.4163321"
            )
        )
//////////////////Kef
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional du Kef",
                Location = "Hôpital Régional du Kef 7100 Le Kef",
                Numero = "78 228 900",
                Longitude = "8.705107",
                Latitude = "36.178057900000006"
            )
        )
//////////////////Siliana
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Siliana",
                Location = "Hôpital Régional de Siliana 6100 Siliana",
                Numero = "78 872 800",
                Longitude = "9.3753558",
                Latitude = "36.0805504"
            )
        )
//////////////////Sousse
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Sousse",
                Location = "Hôpital Farhat Hached de Sousse 4000 Sousse",
                Numero = "73 223 311",
                Longitude = "10.627749399999999",
                Latitude = "35.8296103"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Sahloul de Sousse",
                Location = "Hôpital Sahloul de Sousse 4000 Sousse",
                Numero = "73 369 411",
                Longitude = "10.5904807",
                Latitude = "35.837135599999996"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Fattouma Bourguiba de Monastir",
                Location = "Hôpital Fattouma Bourguiba de Monastir 5000 Monastir",
                Numero = "73 460 411",
                Longitude = "10.833765399999999",
                Latitude = "35.7703318"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Ksar Hellal",
                Location = "Hôpital Régional de Ksar Hellal 5070 Ksar Hellal",
                Numero = "73 475 275",
                Longitude = "10.8889227",
                Latitude = "35.6507551"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Mahdia",
                Location = "Hôpital Régional de Mahdia 5100 Mahdia",
                Numero = "3 671 744",
                Longitude = "11.0326427",
                Latitude = "35.5100753"
            )
        )
//////////////////Kairouan
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kairouan",
                Location = "Hôpital Régional de Kairouan 3100 Kairouan",
                Numero = "77 226 300",
                Longitude = "10.095772799999999",
                Latitude = "35.684039299999995"
            )
        )
//////////////////Kasserine
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kasserine",
                Location = "Hôpital Régional de Kasserine 1200 Kasserine",
                Numero = "77 474 910",
                Longitude = "8.794013",
                Latitude = "35.1749796"
            )
        )
//////////////////SidiBouzid
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Sidi Bouzid",
                Location = "Hôpital Régional de Sidi Bouzid 9100 Sidi Bouzid",
                Numero = "76 632 500",
                Longitude = "9.4631963",
                Latitude = "35.0239295"
            )
        )
//////////////////Sfax
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Sfax",
                Location = "Hôpital Habib Bourguiba de Sfax 3000 Sfax",
                Numero = "74 241 511",
                Longitude = "10.7482127",
                Latitude = "34.7402677"
            )
        )
//////////////////Gafsa
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Gafsa",
                Location = "Hôpital Régional de Gafsa 2100 Gafsa",
                Numero = "76 225 177",
                Longitude = "8.7961413",
                Latitude = "34.4200515"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Metlaoui",
                Location = "Hôpital Régional de Metlaoui 2130 Metlaoui",
                Numero = "76 241 600",
                Longitude = "8.392040399999999",
                Latitude = "34.312501499999996"
            )
        )
        //////////////////Tozeur
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Tozeur",
                Location = "Hôpital Régional de Tozeur 2200 Tozeur",
                Numero = "76 453 400",
                Longitude = "8.1269021",
                Latitude = "33.9160202"
            )
        )
        //////////////////Gabès
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Gabès",
                Location = "Hôpital Régional de Gabès 6001 Gabès",
                Numero = "75 290 300",
                Longitude = "10.1153674",
                Latitude = "3.8634805"
            )
        )
        //////////////////Kébili
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kébili",
                Location = "Hôpital Régional de Kébili 4200 Kébili",
                Numero = "75 490 027",
                Longitude = "8.971264",
                Latitude = "33.7184713"
            )
        )
        //////////////////Tataouine
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Tataouine",
                Location = "Hôpital Régional de Tataouine 3200 Tataouine",
                Numero = "75 870 114",
                Longitude = "10.4593787",
                Latitude = "32.9851757"
            )
        )
        //////////////////Médenine
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Médenine",
                Location = "Hôpital Régional de Médenine 4100 Médenine",
                Numero = "75 640 044",
                Longitude = "10.4562372",
                Latitude = "33.3466181"
            )
        )
        //////////////////Djerba
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Djerba",
                Location = "Hôpital Régional de Djerba 4180 Houmet Souk - Djerba",
                Numero = "75 650 018",
                Longitude = "10.9887719",
                Latitude = "33.8131631"
            )
        )
        //////////////////Zarzis
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Zarzis",
                Location = "Hôpital Régional de Zarzis 4170 Zarzis",
                Numero = "75 738 026",
                Longitude = "11.0862514",
                Latitude = " 33.5414516"
            )
        )
        //////////////////////////////////////////////////////////////////////////////

        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //now getting address from latitude and longitude

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var addresses: List<Address>

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(requireContext())
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(requireContext())
                        .removeLocationUpdates(this)

                    if (locationResult != null && locationResult.locations.size > 0) {
                        var locIndex = locationResult.locations.size - 1

                        var latitude = locationResult.locations.get(locIndex).latitude
                        var longitude = locationResult.locations.get(locIndex).longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        val   address = addresses[0].getAddressLine(0)

                        //LocationSignup.text = address
                        //val cityName = addresses[0].getLocality()
                        var stateName = addresses[0].adminArea

                        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                        val editor = preferences.edit()
                        editor.putString("Mylatitude", latitude.toString())
                        editor.putString("Mylongitude", longitude.toString())
                        editor.apply()  //Send Data to Recycler to Calculate (Diff KM)

                        val locationMap: TextView = requireView().findViewById(R.id.locationMap)
                        if (addresses[0].adminArea == null) {
                            println("Location is NULL")
                            locationMap.text = "inconnue"
                            recylcerInformationAdapter = InformationAdapter(MapListAll)
                        } else {
                            locationMap.text = stateName
                            println("cityName =>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + stateName)
                            ////...........1.........////
                            if (stateName.toLowerCase().contains("tunis") || stateName.toLowerCase()
                                    .contains("ariana") || stateName.toLowerCase()
                                    .contains("ben arous") || stateName.toLowerCase()
                                    .contains("manouba")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(MapListTunis)
                            }
                            if (stateName.toLowerCase().contains("bizert")) {
                                recylcerInformationAdapter = InformationAdapter(MapListBizert)
                            }
                            if (stateName.toLowerCase().contains("béja")) {
                                recylcerInformationAdapter = InformationAdapter(MapListBeja)
                            }
                            if (stateName.toLowerCase().contains("jendouba")) {
                                recylcerInformationAdapter = InformationAdapter(MapListJendouba)
                            }
                            if (stateName.toLowerCase().contains("nebel") || stateName.toLowerCase()
                                    .contains("nabeul")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(MapListNebel)
                            }
                            if (stateName.toLowerCase().contains("zaghouan")) {
                                recylcerInformationAdapter = InformationAdapter(MapListZaghouan)
                            }
                            if (stateName.toLowerCase().contains("kef")) {
                                recylcerInformationAdapter = InformationAdapter(MapListKef)
                            }
                            ////...........2.........////
                            if (stateName.toLowerCase().contains("siliana")) {
                                recylcerInformationAdapter = InformationAdapter(MapListSiliana)
                            }
                            if (stateName.toLowerCase()
                                    .contains("sousse") || stateName.toLowerCase()
                                    .contains("monastir") || stateName.toLowerCase()
                                    .contains("mahdia")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(MapListSousse)
                            }
                            if (stateName.toLowerCase().contains("kairouan")) {
                                recylcerInformationAdapter = InformationAdapter(MapListKairouan)
                            }
                            if (stateName.toLowerCase().contains("kasserine")) {
                                recylcerInformationAdapter = InformationAdapter(MapListKasserine)
                            }
                            if (stateName.toLowerCase().contains("sidi Bouzid")) {
                                recylcerInformationAdapter = InformationAdapter(MapListSidiBouzid)
                            }
                            if (stateName.toLowerCase().contains("sfax")) {
                                recylcerInformationAdapter = InformationAdapter(MapListSfax)
                            }
                            ////...........3.........////
                            if (stateName.toLowerCase().contains("gafsa")) {
                                recylcerInformationAdapter = InformationAdapter(MapListGafsa)
                            }
                            if (stateName.toLowerCase().contains("tozeur")) {
                                recylcerInformationAdapter = InformationAdapter(MapListTozeur)
                            }
                            if (stateName.toLowerCase().contains("gabès")) {
                                recylcerInformationAdapter = InformationAdapter(MapListGabes)
                            }
                            if (stateName.toLowerCase().contains("kebili")) {
                                recylcerInformationAdapter = InformationAdapter(MapListKebili)
                            }
                            if (stateName.toLowerCase().contains("tataouine")) {
                                recylcerInformationAdapter = InformationAdapter(MapListTataouine)
                            }
                            if (stateName.toLowerCase().contains("medenine")||stateName.toLowerCase().contains("médenine")) {
                                recylcerInformationAdapter = InformationAdapter(MapListMedenine)
                            }
                            if (stateName.toLowerCase().contains("djerba")) {
                                recylcerInformationAdapter = InformationAdapter(MapListDjerba)
                            }
                            if (stateName.toLowerCase().contains("zarzis")) {
                                recylcerInformationAdapter = InformationAdapter(MapListZarzis)
                            }
                            if (!stateName.toLowerCase()
                                    .contains("tunis") && !stateName.toLowerCase()
                                    .contains("manouba") && !stateName.toLowerCase()
                                    .contains("nabeul")
                                && !stateName.toLowerCase()
                                    .contains("ariana") && !stateName.toLowerCase()
                                    .contains("ben arous") && !stateName.toLowerCase()
                                    .contains("bizert")
                                && !stateName.toLowerCase()
                                    .contains("béja") && !stateName.toLowerCase()
                                    .contains("jendouba") && !stateName.toLowerCase()
                                    .contains("nebel")
                                && !stateName.toLowerCase()
                                    .contains("zaghouan") && !stateName.toLowerCase()
                                    .contains("kef") && !stateName.toLowerCase().contains("siliana")
                                && !stateName.toLowerCase()
                                    .contains("mahdia") && !stateName.toLowerCase()
                                    .contains("monastir") && !stateName.toLowerCase()
                                    .contains("sousse")
                                && !stateName.toLowerCase()
                                    .contains("kairouan") && !stateName.toLowerCase()
                                    .contains("kasserine") && !stateName.toLowerCase()
                                    .contains("sidi Bouzid")
                                && !stateName.toLowerCase()
                                    .contains("sfax") && !stateName.toLowerCase()
                                    .contains("gafsa") && !stateName.toLowerCase()
                                    .contains("tozeur")
                                && !stateName.toLowerCase()
                                    .contains("gabès") && !stateName.toLowerCase()
                                    .contains("kebili") && !stateName.toLowerCase()
                                    .contains("tataouine")
                                && !stateName.toLowerCase()
                                    .contains("medenine") && !stateName.toLowerCase()
                                    .contains("djerba") && !stateName.toLowerCase().contains("médenine")
                                && !stateName.toLowerCase().contains("zarzis")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(MapListAll)

                                val preferencess =
                                    PreferenceManager.getDefaultSharedPreferences(context)
                                val editorr = preferencess.edit()
                                editorr.putString("inconnue", "Yes")
                                editorr.apply()

                            }
                        }//Else


                        recylcerInformation.adapter = recylcerInformationAdapter
                        recylcerInformation.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }

            }, Looper.getMainLooper())

    }


    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


}