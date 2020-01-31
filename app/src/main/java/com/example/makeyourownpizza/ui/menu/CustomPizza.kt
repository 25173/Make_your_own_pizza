package com.example.makeyourownpizza.ui.menu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.makeyourownpizza.R
import com.example.makeyourownpizza.data.Bestelling
import com.example.makeyourownpizza.timer.CountDown
import com.example.makeyourownpizza.ui.shared.SharedView
import com.example.makeyourownpizza.utility.SharedPreference

private lateinit var navController: NavController
private lateinit var viewModel: SharedView
private lateinit var sharePref: SharedPreference
private lateinit var clock: CountDown

class CustomPizza : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(false)


//decalratie
        val view = inflater.inflate(R.layout.fragment_custom_pizza, container, false)
        val goBackBtn = view.findViewById<Button>(R.id.anullerenBtn)
        val toevoegenBtn = view.findViewById<Button>(R.id.toevoegenBtn)

//        countdown
        sharePref = SharedPreference(view.context)
        clock = CountDown(view, view.context)
        val heeftBesteld = sharePref.getInt("HeeftBesteld")

        if (heeftBesteld == 1){
            clock.StartTimer()
        }


        // navigation controller
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)

//        click linsterner
        goBackBtn.setOnClickListener {
            annuleren()
        }

        toevoegenBtn.setOnClickListener {
            getBestelling(view)
        }



        // Inflate the layout for this fragment
        return view
    }

    private fun getBestelling(view: View) {
        var text = "zelf gekozen pizza : \n"

//        bodem
        val id: Int = view.findViewById<RadioGroup>(R.id.bodem_pizza).checkedRadioButtonId
        if (id != 1) {
            val radioButton = view.findViewById<RadioButton>(id)
            text += " ${radioButton.text} bodem"
        } else {
            // If no radio button checked in this radio group
            Toast.makeText(
                context, "On button click :" +
                        " nothing selected",
                Toast.LENGTH_SHORT
            ).show()
        }

//        toppings
        val toppings = arrayListOf<String>()
        var isThereToppings = false
        if (view.findViewById<CheckBox>(R.id.top_tomaat).isChecked) {
            toppings.add(view.findViewById<CheckBox>(R.id.top_tomaat).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.top_cheese).isChecked) {
            toppings.add(view.findViewById<CheckBox>(R.id.top_cheese).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.top_mozzarella).isChecked) {
            toppings.add(view.findViewById<CheckBox>(R.id.top_mozzarella).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.top_ham).isChecked) {
            toppings.add(view.findViewById<CheckBox>(R.id.top_ham).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.top_rucola).isChecked) {
            toppings.add(view.findViewById<CheckBox>(R.id.top_rucola).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.top_olijf).isChecked) {
            toppings.add(view.findViewById<CheckBox>(R.id.top_olijf).text.toString())
            isThereToppings = true
        }
        if (isThereToppings){
            text += " met "
            text += toppings.joinToString(", ")
        }else{
            text += " zonder toppings"
        }

//        extra toppings
        val extraToppings = arrayListOf<String>()
        isThereToppings = false
        if (view.findViewById<CheckBox>(R.id.extra_top_tomaat).isChecked) {
            extraToppings.add(view.findViewById<CheckBox>(R.id.top_tomaat).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.extra_top_cheese).isChecked) {
            extraToppings.add(view.findViewById<CheckBox>(R.id.top_cheese).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.extra_top_mozzarella).isChecked) {
            extraToppings.add(view.findViewById<CheckBox>(R.id.top_mozzarella).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.extra_top_ham).isChecked) {
            extraToppings.add(view.findViewById<CheckBox>(R.id.top_ham).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.extra_top_rucola).isChecked) {
            extraToppings.add(view.findViewById<CheckBox>(R.id.top_rucola).text.toString())
            isThereToppings = true
        }
        if (view.findViewById<CheckBox>(R.id.extra_top_olijf).isChecked) {
            extraToppings.add(view.findViewById<CheckBox>(R.id.top_olijf).text.toString())
            isThereToppings = true
        }
        if (isThereToppings){
            text += "\n \t + extra "
            text += extraToppings.joinToString(", ")

        }

        Log.i("MainActivity", text )

        sendBestelling(text)

    }

    private fun sendBestelling(text:String) {
//        attact  the viewmodel
        viewModel = ViewModelProvider(this).get(SharedView::class.java)
        val bestelling =  Bestelling()
        bestelling.bestellingOrder = text
        viewModel.insert(bestelling)
        sharePref.saveLong("timeRemainLeft", clock.mTimeLeftInMillis)
        sharePref.saveBoolean("Running", clock.mTimerRunning)
        navController.navigate(R.id.action_custom_pizza_to_main2)
    }

    private fun annuleren() {
        sharePref.saveLong("timeRemainLeft", clock.mTimeLeftInMillis)
        sharePref.saveBoolean("Running", clock.mTimerRunning)
        navController.navigate(R.id.action_custom_pizza_to_main2)
    }


}
