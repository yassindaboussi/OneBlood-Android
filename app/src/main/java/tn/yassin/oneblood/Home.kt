package tn.yassin.oneblood

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home.*
import tn.yassin.oneblood.Fragments.HomeFragment
import tn.yassin.oneblood.Fragments.MapFragment
import tn.yassin.oneblood.Fragments.SearchFragment
import tn.yassin.oneblood.Fragments.SettingFragment

class Home : AppCompatActivity(){
    private lateinit var btnHome: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        // fragments
        //navigationFragments(HomeFragment())// Default SHow HomeFragement
        navigtion_container.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.id_Home -> navigationFragments(HomeFragment())
                R.id.id_search-> navigationFragments(SearchFragment())
                R.id.id_map-> navigationFragments(MapFragment())
                R.id.id_setting -> navigationFragments(SettingFragment())
            }
            true
        }

    }

    private fun changeFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
    }
    fun navigationFragments(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);// Animation translation Between fragments
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.commit()
        }
    }


}