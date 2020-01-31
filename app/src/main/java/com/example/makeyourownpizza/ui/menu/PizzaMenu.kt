package com.example.makeyourownpizza.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.makeyourownpizza.R
import com.example.makeyourownpizza.data.Bestelling
import com.example.makeyourownpizza.timer.CountDown
import com.example.makeyourownpizza.ui.main.MainFragment
import com.example.makeyourownpizza.ui.shared.SharedView
import com.example.makeyourownpizza.utility.SharedPreference

private lateinit var navController: NavController
private lateinit var viewModel: SharedView
private lateinit var sharePref: SharedPreference
private lateinit var clock: CountDown

class PizzaMenu : Fragment(), View.OnClickListener{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_pizza_menu, container, false)
        val customPizzaBtn = view.findViewById<Button>(R.id.makeOwnPizza)
        val salami = view.findViewById<Button>(R.id.salami)
        val magerita = view.findViewById<Button>(R.id.magerita)
        val quatrostagioni = view.findViewById<Button>(R.id.quatroStationi)
        val napolitana = view.findViewById<Button>(R.id.napolitana)
        val annuleren = view.findViewById<Button>(R.id.BackToMain)
        val vegan = view.findViewById<Button>(R.id.vegan)
        val quatroFormazzi = view.findViewById<Button>(R.id.quatroFormazzi)

        sharePref = SharedPreference(view.context)
        clock = CountDown(view, view.context)
        val heeftBesteld = sharePref.getInt("HeeftBesteld")

        if (heeftBesteld == 1){
            clock.StartTimer()
        }

        customPizzaBtn.setOnClickListener(this)
        salami.setOnClickListener(this)
        magerita.setOnClickListener(this)
        quatroFormazzi.setOnClickListener(this)
        quatrostagioni.setOnClickListener(this)
        napolitana.setOnClickListener(this)
        vegan.setOnClickListener(this)
        annuleren.setOnClickListener(this)


        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host
        )

        viewModel = ViewModelProvider(this).get(SharedView::class.java)


        return view
    }

    override fun onClick(v: View?) {

        val bestelling = Bestelling()

        sharePref.saveLong("timeRemainLeft", clock.mTimeLeftInMillis)
        sharePref.saveBoolean("Running", clock.mTimerRunning)
            when(v?.id){
                R.id.makeOwnPizza ->  navController.navigate(R.id.action_pizzaMenu_to_custom_pizza)

                R.id.BackToMain -> navController.navigate(R.id.action_pizzaMenu_to_main)

                else ->{
                    bestelling.bestellingOrder = view?.findViewById<Button>(v?.id!!)?.text.toString()
                    viewModel.insert(bestelling)
                    navController.navigate(R.id.action_pizzaMenu_to_main)
                }
            }
    }

}
