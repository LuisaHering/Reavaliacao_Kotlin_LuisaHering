package com.example.minhalojinha.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minhalojinha.R
import com.example.minhalojinha.models.Product
import com.example.minhalojinha.ui.activities.ProductDetailsActivity
import com.example.minhalojinha.ui.fragments.DashboardFragment
import com.example.minhalojinha.ui.fragments.ProductsFragment
import com.example.minhalojinha.utils.Constants
import com.example.minhalojinha.utils.GlideLoader
import kotlinx.android.synthetic.main.item_dashboard_layout.view.*

/**
 * Classe adapter pra Products list items
 */
open class DashboardListAdapter(
    private val context: Context,
    private var quotesList: ArrayList<String>,
    private val fragment: DashboardFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //model is the product your interacting w bc the bind takes care of every single element inside the list
        val model = quotesList[position]

        if (holder is MyViewHolder) {

            holder.itemView.tv_quote.text = model
        }
    }

    /**
     * pega o numero de itens na lista
     */
    override fun getItemCount(): Int {
        return quotesList.size
    }

    /**
     * um ViewHolder descreve um item view and metadata sobre seu lugar dentro do recyclerView
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}