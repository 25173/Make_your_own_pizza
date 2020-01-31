package com.example.makeyourownpizza

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.makeyourownpizza.utility.SharedPreference
import android.widget.Button as Button1

private lateinit var navController: NavController


class TableId : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =inflater.inflate(R.layout.fragment_table_id, container, false)
        val sharedPref = SharedPreference(view.context)

        val setTableId = view.findViewById<Button1>(R.id.set_table_id)

        setTableId.setOnClickListener {
            setTabelId(view,sharedPref)
        }

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        setHasOptionsMenu(false)
        //        navigatie controler
        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host
        )


        return view
    }



    @SuppressLint("CommitPrefEdits")
    private fun setTabelId(view: View, sharedPref:SharedPreference) {
         val tableId= view.findViewById<EditText>(R.id.table_id_edittext)
        Log.i("MainActivity", tableId.text.toString())
        sharedPref.saveString(getString(R.string.set_table),tableId.text.toString())
        sharedPref.saveInt("HeeftBesteld",0)
        navController.navigate(R.id.action_table_id_to_main)
    }


}

