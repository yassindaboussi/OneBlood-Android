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
import tn.esprit.leagueoflegendrecyclerview.championList.ChampionAdapter
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import tn.esprit.leagueoflegendrecyclerview.data.Inofrmations
import tn.yassin.oneblood.R
import java.util.*
import kotlin.collections.ArrayList

private var ctx: Context? = null
private var self: View? = null

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

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //  val myWebView: WebView = view.findViewById(R.id.webVieww)
        //  myWebView.loadUrl("https://www.google.com/maps/d/u/0/edit?mid=1mO-6BPWmj_pgw8iFg4AbTcZWHbNkBQJX&usp=sharing")

        recylcerChampion = view.findViewById(R.id.recyclerLocations)


/*        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        var name = preferences.getString("Name", "")
        println("My Position now ==>>> "+name)*/


        //getCurrentLocation()

        SetLocationUser()
    }


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

    private fun getCurrentLocation() {

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
                Numero = "71 573 884"
            )
        )
        MapListTunis.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Charles Nicolle",
                Location = "Hôpital Charles Nicolle 1006 Tunis",
                Numero = "71 578 357"
            )
        )
        MapListTunis.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital La Rabta",
                Location = "Hôpital La Rabta 1006 Tunis",
                Numero = "71 578 819"
            )
        )
        MapListTunis.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Habib Thameur",
                Location = "Hôpital Habib Thameur 1069 Tunis",
                Numero = "71 397 000"
            )
        )
        MapListTunis.add(
            Inofrmations(
                Nom = "Centre Militaire de Transfusion Sanguine",
                Location = "Ras Tabia, 2000 Le Bardo",
                Numero = "71 564 466"
            )
        )
////////////////////Bizert
        MapListBizert.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Bizerte",
                Location = "Hôpital Régional de Bizerte 7000 Bizerte",
                Numero = "72 422 057"
            )
        )
        MapListBizert.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Menzel Bourguiba",
                Location = "Hopital Régional de Menzel Bourguiba 7050 Menzel Bourguiba",
                Numero = "72 460 455"
            )
        )
//////////////////Beja
        MapListBeja.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Béja",
                Location = "Hôpital Régional de Béja 9000 Béja",
                Numero = "78 457 322"
            )
        )
//////////////////Jendouba
        MapListJendouba.add(
            Inofrmations(
                Nom = "Centre National de Transfusion Sanguine de Jendouba",
                Location = "Hôpital Régional de Jendouba 8100 Jendouba",
                Numero = "78 604 558"
            )
        )
//////////////////Nebel
        MapListNebel.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Nabeul",
                Location = "Hôpital Régional de Nabeul 8000 Nabeul",
                Numero = "72 285 633"
            )
        )
        MapListNebel.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Menzel Témime",
                Location = "Hôpital Régional de Menzel Témime 8080 Menzel Témime",
                Numero = "72 344 155"
            )
        )
        MapListNebel.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital de Circonscription de Grombalia",
                Location = "Hôpital de Circonscription de Grombalia 8030 Grombalia",
                Numero = "72 255 212"
            )
        )
//////////////////Zaghouan
        MapListZaghouan.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Zaghouan",
                Location = "Hôpital Régional de Zaghouan 1100 Zaghouan",
                Numero = "72 676 700"
            )
        )
//////////////////Kef
        MapListKef.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional du Kef",
                Location = "Hôpital Régional du Kef 7100 Le Kef",
                Numero = "78 228 900"
            )
        )
//////////////////Siliana
        MapListSiliana.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Siliana",
                Location = "Hôpital Régional de Siliana 6100 Siliana",
                Numero = "78 872 800"
            )
        )
//////////////////Sousse
        MapListSousse.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Sousse",
                Location = "Hôpital Farhat Hached de Sousse 4000 Sousse",
                Numero = "73 223 311"
            )
        )
        MapListSousse.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Sahloul de Sousse",
                Location = "Hôpital Sahloul de Sousse 4000 Sousse",
                Numero = "73 369 411"
            )
        )
        MapListSousse.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Fattouma Bourguiba de Monastir",
                Location = "Hôpital Fattouma Bourguiba de Monastir 5000 Monastir",
                Numero = "73 460 411"
            )
        )
        MapListSousse.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Ksar Hellal",
                Location = "Hôpital Régional de Ksar Hellal 5070 Ksar Hellal",
                Numero = "73 475 275"
            )
        )
        MapListSousse.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Mahdia",
                Location = "Hôpital Régional de Mahdia 5100 Mahdia",
                Numero = "3 671 744"
            )
        )
//////////////////Kairouan
        MapListKairouan.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kairouan",
                Location = "Hôpital Régional de Kairouan 3100 Kairouan",
                Numero = "77 226 300"
            )
        )
//////////////////Kasserine
        MapListKasserine.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kasserine",
                Location = "Hôpital Régional de Kasserine 1200 Kasserine",
                Numero = "77 474 910"
            )
        )
//////////////////SidiBouzid
        MapListSidiBouzid.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Sidi Bouzid",
                Location = "Hôpital Régional de Sidi Bouzid 9100 Sidi Bouzid",
                Numero = "76 632 500"
            )
        )
//////////////////Sfax
        MapListSfax.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Sfax",
                Location = "Hôpital Habib Bourguiba de Sfax 3000 Sfax",
                Numero = "74 241 511"
            )
        )
//////////////////Gafsa
        MapListGafsa.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Gafsa",
                Location = "Hôpital Régional de Gafsa 2100 Gafsa",
                Numero = "76 225 177"
            )
        )
        MapListGafsa.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Metlaoui",
                Location = "Hôpital Régional de Metlaoui 2130 Metlaoui",
                Numero = "76 241 600"
            )
        )
        //////////////////Tozeur
        MapListTozeur.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Tozeur",
                Location = "Hôpital Régional de Tozeur 2200 Tozeur",
                Numero = "76 453 400"
            )
        )
        //////////////////Gabès
        MapListGabes.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Gabès",
                Location = "Hôpital Régional de Gabès 6001 Gabès",
                Numero = "75 290 300"
            )
        )
        //////////////////Kébili
        MapListKebili.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kébili",
                Location = "Hôpital Régional de Kébili 4200 Kébili",
                Numero = "75 490 027"
            )
        )
        //////////////////Tataouine
        MapListTataouine.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Tataouine",
                Location = "Hôpital Régional de Tataouine 3200 Tataouine",
                Numero = "75 870 114"
            )
        )
        //////////////////Médenine
        MapListMedenine.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Médenine",
                Location = "Hôpital Régional de Médenine 4100 Médenine",
                Numero = "75 640 044"
            )
        )
        //////////////////Djerba
        MapListDjerba.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Djerba",
                Location = "Hôpital Régional de Djerba 4180 Houmet Souk - Djerba",
                Numero = "75 650 018"
            )
        )
        //////////////////Zarzis
        MapListZarzis.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Zarzis",
                Location = " Hôpital Régional de Zarzis 4170 Zarzis",
                Numero = "75 738 026"
            )
        )
        //////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////
        MapListAll.add(
            Inofrmations(
                Nom = "Centre National de Transfusion Sanguine",
                Location = "Djebel Lakhdar Bab Sâadoun Tunis",
                Numero = "71 573 884"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Charles Nicolle",
                Location = "Hôpital Charles Nicolle 1006 Tunis",
                Numero = "71 578 357"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital La Rabta",
                Location = "Hôpital La Rabta 1006 Tunis",
                Numero = "71 578 819"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Habib Thameur",
                Location = "Hôpital Habib Thameur 1069 Tunis",
                Numero = "71 397 000"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Militaire de Transfusion Sanguine",
                Location = "Ras Tabia, 2000 Le Bardo",
                Numero = "71 564 466"
            )
        )
////////////////////Bizert
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Bizerte",
                Location = "Hôpital Régional de Bizerte 7000 Bizerte",
                Numero = "72 422 057"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Menzel Bourguiba",
                Location = "Hopital Régional de Menzel Bourguiba 7050 Menzel Bourguiba",
                Numero = "72 460 455"
            )
        )
//////////////////Beja
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Béja",
                Location = "Hôpital Régional de Béja 9000 Béja",
                Numero = "78 457 322"
            )
        )
//////////////////Jendouba
        MapListAll.add(
            Inofrmations(
                Nom = "Centre National de Transfusion Sanguine de Jendouba",
                Location = "Hôpital Régional de Jendouba 8100 Jendouba",
                Numero = "78 604 558"
            )
        )
//////////////////Nebel
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Nabeul",
                Location = "Hôpital Régional de Nabeul 8000 Nabeul",
                Numero = "72 285 633"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Menzel Témime",
                Location = "Hôpital Régional de Menzel Témime 8080 Menzel Témime",
                Numero = "72 344 155"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital de Circonscription de Grombalia",
                Location = "Hôpital de Circonscription de Grombalia 8030 Grombalia",
                Numero = "72 255 212"
            )
        )
//////////////////Zaghouan
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Zaghouan",
                Location = "Hôpital Régional de Zaghouan 1100 Zaghouan",
                Numero = "72 676 700"
            )
        )
//////////////////Kef
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional du Kef",
                Location = "Hôpital Régional du Kef 7100 Le Kef",
                Numero = "78 228 900"
            )
        )
//////////////////Siliana
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Siliana",
                Location = "Hôpital Régional de Siliana 6100 Siliana",
                Numero = "78 872 800"
            )
        )
//////////////////Sousse
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Sousse",
                Location = "Hôpital Farhat Hached de Sousse 4000 Sousse",
                Numero = "73 223 311"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Sahloul de Sousse",
                Location = "Hôpital Sahloul de Sousse 4000 Sousse",
                Numero = "73 369 411"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Fattouma Bourguiba de Monastir",
                Location = "Hôpital Fattouma Bourguiba de Monastir 5000 Monastir",
                Numero = "73 460 411"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Ksar Hellal",
                Location = "Hôpital Régional de Ksar Hellal 5070 Ksar Hellal",
                Numero = "73 475 275"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Mahdia",
                Location = "Hôpital Régional de Mahdia 5100 Mahdia",
                Numero = "3 671 744"
            )
        )
//////////////////Kairouan
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kairouan",
                Location = "Hôpital Régional de Kairouan 3100 Kairouan",
                Numero = "77 226 300"
            )
        )
//////////////////Kasserine
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kasserine",
                Location = "Hôpital Régional de Kasserine 1200 Kasserine",
                Numero = "77 474 910"
            )
        )
//////////////////SidiBouzid
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Sidi Bouzid",
                Location = "Hôpital Régional de Sidi Bouzid 9100 Sidi Bouzid",
                Numero = "76 632 500"
            )
        )
//////////////////Sfax
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Sfax",
                Location = "Hôpital Habib Bourguiba de Sfax 3000 Sfax",
                Numero = "74 241 511"
            )
        )
//////////////////Gafsa
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Gafsa",
                Location = "Hôpital Régional de Gafsa 2100 Gafsa",
                Numero = "76 225 177"
            )
        )
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Metlaoui",
                Location = "Hôpital Régional de Metlaoui 2130 Metlaoui",
                Numero = "76 241 600"
            )
        )
        //////////////////Tozeur
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Tozeur",
                Location = "Hôpital Régional de Tozeur 2200 Tozeur",
                Numero = "76 453 400"
            )
        )
        //////////////////Gabès
        MapListAll.add(
            Inofrmations(
                Nom = "Centre Régional de Transfusion Sanguine de Gabès",
                Location = "Hôpital Régional de Gabès 6001 Gabès",
                Numero = "75 290 300"
            )
        )
        //////////////////Kébili
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Kébili",
                Location = "Hôpital Régional de Kébili 4200 Kébili",
                Numero = "75 490 027"
            )
        )
        //////////////////Tataouine
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Tataouine",
                Location = "Hôpital Régional de Tataouine 3200 Tataouine",
                Numero = "75 870 114"
            )
        )
        //////////////////Médenine
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Médenine",
                Location = "Hôpital Régional de Médenine 4100 Médenine",
                Numero = "75 640 044"
            )
        )
        //////////////////Djerba
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Djerba",
                Location = "Hôpital Régional de Djerba 4180 Houmet Souk - Djerba",
                Numero = "75 650 018"
            )
        )
        //////////////////Zarzis
        MapListAll.add(
            Inofrmations(
                Nom = "Banque du Sang de l'Hôpital Régional de Zarzis",
                Location = " Hôpital Régional de Zarzis 4170 Zarzis",
                Numero = "75 738 026"
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

                        var address: String = addresses[0].getAddressLine(0)
                        val locationMap: TextView = requireView().findViewById(R.id.locationMap)
                        //LocationSignup.text = address
                        //val cityName = addresses[0].getLocality()
                        var stateName = addresses[0].adminArea

                       if(addresses[0].adminArea == null) {
                            println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                            recylcerChampionAdapter = ChampionAdapter(MapListAll)
                        }
                        else {
                            locationMap.text = stateName
                            println("cityName =>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + stateName)
                            ////...........1.........////
                            if (stateName.toLowerCase().contains("tunis") || stateName.toLowerCase()
                                    .contains("ariana") || stateName.toLowerCase()
                                    .contains("ben arous")
                            ) {
                                recylcerChampionAdapter = ChampionAdapter(MapListTunis)
                            }
                            if (stateName.toLowerCase().contains("bizert")) {
                                recylcerChampionAdapter = ChampionAdapter(MapListBizert)
                            }
                           if (stateName.toLowerCase().contains("béja")) {
                                recylcerChampionAdapter = ChampionAdapter(MapListBeja)
                            }
                                                      if (stateName.toLowerCase().contains("jendouba")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListJendouba)
                                                     }
                                                     if (stateName.toLowerCase().contains("nebel")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListNebel)
                                                     }
                                                     if (stateName.toLowerCase().contains("zaghouan")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListZaghouan)
                                                     }
                                                     if (stateName.toLowerCase().contains("kef")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListKef)
                                                     }
                                                     ////...........2.........////
                                                     if (stateName.toLowerCase().contains("siliana")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListSiliana)
                                                     }
                                                     if (stateName.toLowerCase()
                                                             .contains("sousse") || stateName.toLowerCase()
                                                             .contains("monastir") || stateName.toLowerCase()
                                                             .contains("mahdia")
                                                     ) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListSousse)
                                                     }
                                                     if (stateName.toLowerCase().contains("kairouan")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListKairouan)
                                                     }
                                                     if (stateName.toLowerCase().contains("kasserine")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListKasserine)
                                                     }
                                                     if (stateName.toLowerCase().contains("Sidi Bouzid")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListSidiBouzid)
                                                     }
                                                     if (stateName.toLowerCase().contains("sfax")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListSfax)
                                                     }
                                                     ////...........3.........////
                                                     if (stateName.toLowerCase().contains("gafsa")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListGafsa)
                                                     }
                                                     if (stateName.toLowerCase().contains("tozeur")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListTozeur)
                                                     }
                                                     if (stateName.toLowerCase().contains("gabès")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListGabes)
                                                     }
                                                     if (stateName.toLowerCase().contains("kebili")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListKebili)
                                                     }
                                                     if (stateName.toLowerCase().contains("tataouine")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListTataouine)
                                                     }
                                                     if (stateName.toLowerCase().contains("medenine")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListMedenine)
                                                     }
                                                     if (stateName.toLowerCase().contains("djerba")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListDjerba)
                                                     }
                                                     if (stateName.toLowerCase().contains("zarzis")) {
                                                         recylcerChampionAdapter = ChampionAdapter(MapListZarzis)
                                                     }
/*                            else { // Hedh tjy erreur yefichi kolchay heta ken heya f tunis mthlen
                                recylcerChampionAdapter = ChampionAdapter(MapListAll)
                                }*/
                       }


                        recylcerChampion.adapter = recylcerChampionAdapter
                        recylcerChampion.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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