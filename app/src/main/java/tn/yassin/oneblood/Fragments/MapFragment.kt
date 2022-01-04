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
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import tn.yassin.oneblood.DataMapList.InformationAdapter
import tn.yassin.oneblood.R
import tn.yassin.oneblood.Util.CustomToast
import tn.yassin.oneblood.Util.ListHospitals
import tn.yassin.oneblood.Util.ReadyFunction
import tn.yassin.oneblood.data.Inofrmations
import java.util.*
import kotlin.collections.ArrayList

private var ctx: Context? = null
private var self: View? = null

class MapFragment : Fragment() {
    lateinit var recylcerInformation: RecyclerView
    lateinit var recylcerInformationAdapter: InformationAdapter
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    val ReadyFunc = ReadyFunction()
    val ListHospitals = ListHospitals()
    private lateinit var searchView: SearchView
    private lateinit var locationMap: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
     // Inflate the layout for this fragment
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recylcerInformation = view.findViewById(R.id.recyclerLocations)
        searchView=view.findViewById(R.id.searchView)
        locationMap=view.findViewById(R.id.locationMap)

        ListHospitals.initListHospit()

        OnSearch()

        SetLocationUser()

    }

    fun OnSearch() {

      //  searchView.setIconifiedByDefault(true);
       // searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();
       // searchView.requestFocusFromTouch();

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //searchView.clearFocus()
                //searchView.requestFocus();
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListAll,
                    ListHospitals.MapListAll as java.util.ArrayList<Inofrmations>
                )
                recylcerInformation.adapter = recylcerInformationAdapter
                recylcerInformation.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                recylcerInformationAdapter.getFilter()?.filter(newText)
                return true
            }

        })
    }

    fun SetLocationUser() {
        //check permission
        //check internet
        if (ReadyFunc.isOnline(requireContext())) { // There is cnx
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_REQUEST_CODE)
            } else {
                getCurrentLocation()
            }
        } else { // No cnx
            CustomToast(requireContext(), "Need internet to Getlocation!","RED").show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                CustomToast(requireContext(), "Permission Denied!","RED").show()
            }
        }
    }

    fun getCurrentLocation() {

        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //now getting address from latitude and longitude
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var addresses: List<Address>

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.getFusedLocationProviderClient(requireContext())
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(requireContext()).removeLocationUpdates(this)

                    if (locationResult != null && locationResult.locations.size > 0) {
                        try{
                        var locIndex = locationResult!!.locations.size - 1

                        var latitude = locationResult!!.locations.get(locIndex).latitude
                        var longitude = locationResult!!.locations.get(locIndex).longitude

                        addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        val address = addresses[0].getAddressLine(0)

                        var stateName = addresses[0].adminArea

                        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                        val editor = preferences.edit()
                        editor.putString("Mylatitude", latitude.toString())
                        editor.putString("Mylongitude", longitude.toString())
                        editor.apply()  //Send Data to Recycler to Calculate (Diff KM)

                        if (addresses[0].adminArea == null) {
                            println("Location is NULL")
                            locationMap.text = "inconnue"
                            recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListAll,
                                ListHospitals.MapListAll as java.util.ArrayList<Inofrmations>
                            )
                        } else {
                            locationMap.text = stateName
                            println("cityName =>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + stateName)
                            ////...........1.........////
                            if (stateName.toLowerCase().contains("tunis") || stateName.toLowerCase()
                                    .contains("ariana") || stateName.toLowerCase()
                                    .contains("ben arous") || stateName.toLowerCase()
                                    .contains("manouba")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListTunis,
                                    ListHospitals.MapListTunis as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("bizert")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListBizert,
                                    ListHospitals.MapListBizert as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("béja")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListBeja,
                                    ListHospitals.MapListBeja as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("jendouba")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListJendouba,
                                    ListHospitals.MapListJendouba as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("nebel") || stateName.toLowerCase()
                                    .contains("nabeul")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListNebel,
                                    ListHospitals.MapListNebel as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("zaghouan")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListZaghouan,
                                    ListHospitals.MapListZaghouan as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("kef")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListKef,
                                    ListHospitals.MapListKef as java.util.ArrayList<Inofrmations>
                                )
                            }
                            ////...........2.........////
                            if (stateName.toLowerCase().contains("siliana")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListSiliana,
                                    ListHospitals.MapListSiliana as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase()
                                    .contains("sousse") || stateName.toLowerCase()
                                    .contains("monastir") || stateName.toLowerCase()
                                    .contains("mahdia")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListSousse,
                                    ListHospitals.MapListSousse as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("kairouan")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListKairouan,
                                    ListHospitals.MapListKairouan as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("kasserine")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListKasserine,
                                    ListHospitals.MapListKasserine as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("sidi Bouzid")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListSidiBouzid,
                                    ListHospitals.MapListSidiBouzid as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("sfax")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListSfax,
                                    ListHospitals.MapListSfax as java.util.ArrayList<Inofrmations>
                                )
                            }
                            ////...........3.........////
                            if (stateName.toLowerCase().contains("gafsa")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListGafsa,
                                    ListHospitals.MapListGafsa as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("tozeur")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListTozeur,
                                    ListHospitals.MapListTozeur as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("gabès")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListGabes,
                                    ListHospitals.MapListGabes as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("kebili")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListKebili,
                                    ListHospitals.MapListKebili as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("tataouine")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListTataouine,
                                    ListHospitals.MapListTataouine as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase()
                                    .contains("medenine") || stateName.toLowerCase()
                                    .contains("médenine")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListMedenine,
                                    ListHospitals.MapListMedenine as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("djerba")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListDjerba,
                                    ListHospitals.MapListDjerba as java.util.ArrayList<Inofrmations>
                                )
                            }
                            if (stateName.toLowerCase().contains("zarzis")) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListZarzis,
                                    ListHospitals.MapListZarzis as java.util.ArrayList<Inofrmations>
                                )
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
                                    .contains("djerba") && !stateName.toLowerCase()
                                    .contains("médenine")
                                && !stateName.toLowerCase().contains("zarzis")
                            ) {
                                recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListAll,
                                    ListHospitals.MapListAll as java.util.ArrayList<Inofrmations>
                                )

                            }
                        }//END Else
                    }
               catch (e: Exception) { // When Something Goes Wrong
                   locationMap.text = "inconnue"
                   recylcerInformationAdapter = InformationAdapter(ListHospitals.MapListAll,
                       ListHospitals.MapListAll as java.util.ArrayList<Inofrmations>
                   )
                    println(e.printStackTrace())
                   println("Canont get Address!");
                }
                    recylcerInformation.adapter = recylcerInformationAdapter
                    recylcerInformation.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }

            }, Looper.getMainLooper())

    }


}