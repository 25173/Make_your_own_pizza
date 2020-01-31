package com.example.makeyourownpizza.ui.main



import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourownpizza.R
import com.example.makeyourownpizza.data.Bestelling
import com.example.makeyourownpizza.ui.shared.SharedView
import com.example.makeyourownpizza.timer.CountDown
import com.example.makeyourownpizza.utility.SharedPreference


private var TAG = "MainActivity"
private lateinit var viewModel: SharedView
private lateinit var navController: NavController
private lateinit var recyclerView: RecyclerView
private lateinit var adapter: BestellingAdapter
private lateinit var clock: CountDown
private lateinit var sharePref: SharedPreference
private var heeftBesteld:Int = 0

class MainFragment : Fragment(),
BestellingAdapter.BestellingItemListerner, View.OnClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = "virtu"
        }
        setHasOptionsMenu(false)
        //share Preferences die alle informatie bijhoud voor de tafel nummer en de klok
        sharePref = SharedPreference(view.context)
        heeftBesteld = sharePref.getInt("HeeftBesteld")

//       de timer zetten
        clock = CountDown(view, view.context)
        //        de tafel nummer
        setTable(view)

        //        navigatie controler
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)

//        Recycleview
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //        viewmodel
        viewModel = ViewModelProvider(this).get(SharedView::class.java)

        viewModel.alleBestellingen.observe(this.viewLifecycleOwner, Observer {
                adapter = BestellingAdapter(requireContext(),it,this)
                recyclerView.adapter = adapter
        })

        val makePizzaButton = view.findViewById<Button>(R.id.Make_pizza_btn)
        val drinkBtn = view.findViewById<Button>(R.id.drinken_btn)
        val pizzaMenuBtn = view.findViewById<Button>(R.id.pizza_menu_btn)
        val bestelBtn = view.findViewById<Button>(R.id.bestellen_btn)
        val betalenBtn = view.findViewById<Button>(R.id.betalen_Btn)
        makePizzaButton.setOnClickListener(this)
        drinkBtn.setOnClickListener (this)
        pizzaMenuBtn.setOnClickListener(this)
        bestelBtn.setOnClickListener(this)
        betalenBtn.setOnClickListener(this)

        if (heeftBesteld ==1 ){
            betalenBtn.visibility = View.VISIBLE
            clock.StartTimer()
        }else if (heeftBesteld == 2  ){
            betalenBtn.isEnabled = false
            bestelBtn.isEnabled = false
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onBestellingDeleteBtn(bestelling: Bestelling){
        viewModel.deleteBestelling(bestelling)
        navController.navigate(R.id.main)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("SetTextI18n")
    private fun setTable(view: View) {
        val tableId = sharePref.getValueString(getString(R.string.set_table))
        val tableText = view.findViewById<TextView>(R.id.table_id_text)
        tableText.text = "Tafel $tableId"


    }

    override fun onClick(v: View?) {
        sharePref.saveLong("timeRemainLeft", clock.mTimeLeftInMillis)
        sharePref.saveBoolean("Running", clock.mTimerRunning)
        when(v?.id){
            R.id.bestellen_btn->{
                viewModel.delete()
                sharePref.saveInt("HeeftBesteld", 1)
                navController.navigate(R.id.main)
            }
            R.id.pizza_menu_btn ->{
                navController.navigate(
                    R.id.action_main_to_pizzaMenu
                )
            }
            R.id.drinken_btn -> {
                navController.navigate(
                    R.id.action_main_to_drinkMenu3
                )
            }
            R.id.Make_pizza_btn ->{
                navController.navigate(
                    R.id.action_main_to_make_pizza
                )
            }
            R.id.betalen_Btn ->{
                sharePref.clearSharedPreference("Running")
                sharePref.clearSharedPreference("timeRemainLeft")
                sharePref.saveInt("HeeftBesteld", 2)
                clock.onPause()
                navController.navigate(R.id.main)

            }

        }

    }

}

