package tn.yassin.oneblood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home.*
import tn.yassin.oneblood.Fragments.HomeFragment
import tn.yassin.oneblood.Fragments.MapFragment
import tn.yassin.oneblood.Fragments.SearchFragment
import tn.yassin.oneblood.Fragments.SettingFragment
import tn.yassin.oneblood.Util.CustomDialog
import tn.yassin.oneblood.Util.ReadyFunction

class Home : AppCompatActivity() {
    private lateinit var btnHome: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        // fragments
        navigationFragments(HomeFragment())// Default SHow HomeFragement

        val Cnx = ReadyFunction()

        navigtion_container.setOnNavigationItemSelectedListener {
            if (Cnx.isOnline(this)) {
             //   println("Internet is ON!")
                when (it.itemId) {
                    R.id.id_Home -> {
                        changeFragment(HomeFragment(),"aa")
                    }
                    R.id.id_search -> { changeFragment(SearchFragment(),"aaa") }
                    R.id.id_map -> { changeFragment(MapFragment(),"aaa") }
                    R.id.id_setting -> { changeFragment(SettingFragment(),"aaa") }
                }
           } else {
            //   println("Internet is OFF!")
                val factory = LayoutInflater.from(this)
                val view: View = factory.inflate(R.layout.noconnection, null)
                val msg = CustomDialog()
                msg.ShowDialogNoConnection(this, view)
            }
            true
        }

    }


    ///////////

/////////
    private fun changeFragment(fragment: Fragment, name: String) {

        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(name).commit()

    }

    fun navigationFragments(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            );// Animation translation Between fragments
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.commit()
        }
    }


}