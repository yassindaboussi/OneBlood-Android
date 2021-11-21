package tn.yassin.oneblood.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import tn.yassin.oneblood.R

class SettingFragment : Fragment() {

    private lateinit var EditName: TextView
    private lateinit var EditNameSetting: TextView
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.setting_fragment, container, false)
//    }

   //EditEmailSetting
    private var ctx: Context? = null
    private var self: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ctx = container?.context
        self = LayoutInflater.from(ctx).inflate(R.layout.setting_fragment, container, false)
        val EditName = self?.findViewById<TextView>(R.id.EditName)
        val EditNameSetting = self?.findViewById<TextView>(R.id.EditNameSetting)
        val EditEmail = self?.findViewById<TextView>(R.id.EditEmail)
        val EditEmailSetting = self?.findViewById<TextView>(R.id.EditEmailSetting)
        val EditPass = self?.findViewById<TextView>(R.id.EditPassword)
        val EditPasswordSetting = self?.findViewById<TextView>(R.id.EditPasswordSetting)
        val EditTypeBlood = self?.findViewById<TextView>(R.id.EditTypeBlood)
        val EditTypeBloodSetting = self?.findViewById<TextView>(R.id.EditTypeBloodSetting)
        val EditLocation = self?.findViewById<TextView>(R.id.EditLocation)
        val EditLocationSetting = self?.findViewById<TextView>(R.id.EditLocationSetting)
        ///////////////////////
        val spinnerUserType= self?.findViewById<Spinner>(R.id.spinnerUserType) // Spinner
        val EditSpinner= self?.findViewById<TextView>(R.id.EditSpinner)
        ////////////////////////////////////////////////////Set EditText False
        EditNameSetting?.setEnabled(false);
        EditEmailSetting?.setEnabled(false);
        EditPasswordSetting?.setEnabled(false);
        EditTypeBloodSetting?.setEnabled(false);
        EditLocationSetting?.setEnabled(false);
        spinnerUserType?.setEnabled(false);
        ////////////////////////////////////////////////////Set EditText True
        EditName?.setOnClickListener {
            EditNameSetting?.setEnabled(true);
        }
        EditEmail?.setOnClickListener {
            EditEmailSetting?.setEnabled(true);
        }
        EditPass?.setOnClickListener {
            EditPasswordSetting?.setEnabled(true);
        }
        EditTypeBlood?.setOnClickListener {
            EditTypeBloodSetting?.setEnabled(true);
        }
        EditLocation?.setOnClickListener {
            EditLocationSetting?.setEnabled(true);
        }
        EditSpinner?.setOnClickListener {
            spinnerUserType?.setEnabled(true);
        }
        /////////////////////////////////////////////// ButtonSaveClicked
        val UpdateUserSetting = self?.findViewById<Button>(R.id.UpdateUserSetting)
        UpdateUserSetting?.setOnClickListener {
            EditNameSetting?.setEnabled(false);
            EditEmailSetting?.setEnabled(false);
            EditPasswordSetting?.setEnabled(false);
            EditTypeBloodSetting?.setEnabled(false);
            EditLocationSetting?.setEnabled(false);
            spinnerUserType?.setEnabled(false);
        }
        ///////////////////////////////: Spinner
        val adapter =  ArrayAdapter.createFromResource(requireContext(), R.array.UserType, android.R.layout.simple_spinner_item)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinnerUserType?.adapter = adapter

        spinnerUserType?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //..
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position).toString()
                //..
            }
        }

///////////////////////
        return self
    }


//    fun getValues(view: View) {
//        Toast.makeText(this, "Spinner 1 " + spinner.selectedItem.toString(), Toast.LENGTH_LONG).show()
//    }


}