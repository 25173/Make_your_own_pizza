package com.example.makeyourownpizza.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.makeyourownpizza.R
import com.example.makeyourownpizza.data.Bestelling
import kotlinx.android.synthetic.main.item.view.*

class BestellingAdapter internal constructor(val context: Context,
                                             val bestelling: List<Bestelling>,
                                             val itemListerner: BestellingItemListerner) :
    RecyclerView.Adapter<BestellingAdapter.BestellingViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var bestellingNummerLijst = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestellingViewHolder {
        val itemView = inflater.inflate(R.layout.item,parent,false)
        return BestellingViewHolder(itemView)
    }

    override fun getItemCount() = bestelling.size

    override fun onBindViewHolder(holder: BestellingViewHolder, position: Int) {
        val bestellingData = bestelling[position]
        with(holder){
            bestellingItemView?.let {
                it.text= bestellingData.bestellingOrder
                it.contentDescription = bestellingData.bestellingOrder
            }
            val bestellingNum = getBestellingNummer(bestellingNummerLijst)
            bestellingNummer.text = bestellingNum.toString()
            holder.itemView.DeleteButton.setOnClickListener {
                itemListerner.onBestellingDeleteBtn(bestellingData)
            }
        }

    }

    private fun getBestellingNummer(bestellingNumber: Int): Any {
        val nummer  =  bestellingNumber + 1
        bestellingNummerLijst += 1
        return nummer
    }


    inner class BestellingViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val bestellingItemView = itemView.findViewById<TextView>(R.id.Bestelling_item)
        val bestellingNummer = itemView.findViewById<TextView>(R.id.opnummering)
    }


    interface BestellingItemListerner {
        fun onBestellingDeleteBtn(bestelling: Bestelling)
    }

}