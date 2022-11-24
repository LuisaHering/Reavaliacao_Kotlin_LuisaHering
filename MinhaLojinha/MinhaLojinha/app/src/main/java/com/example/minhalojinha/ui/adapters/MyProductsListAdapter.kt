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
import com.example.minhalojinha.ui.fragments.ProductsFragment
import com.example.minhalojinha.utils.Constants
import com.example.minhalojinha.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.view.*

/**
 * Classe adapter pra Products list items
 */
open class MyProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment: ProductsFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Inflates the item views which is designed in xml layout file
     * inflates o item views que que é designed no xml layour file
     *
     * cria um novo
     * {@link ViewHolder} e inicializa alguns campos privados pra serem usados pelo RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
    }

    /**
     * Binds cada item no ArrayList pra um view
     * chamado quando o RecyclerView precisa de um novo {@link ViewHolder} do tipo dado p representar
     * an item.
     *
     * Esse novo ViewHolder deve ser construido com uma nova View que pode representar os itens
     * do tipo dado. Voce pode ou criar um novo View manualmente ou inflar ele do xml
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //model is the product your interacting w bc the bind takes care of every single element inside the list
        val model = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadProductPicture(model.image, holder.itemView.iv_item_image)

            holder.itemView.tv_item_name.text = model.title
            holder.itemView.tv_item_price.text = "$${model.price}"

            holder.itemView.ib_delete_product.setOnClickListener {

                fragment.deleteProduct(model.product_id)
            }

            holder.itemView.setOnClickListener {
                // Launch Product details screen.
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_PRODUCT_ID, model.product_id)
                intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, model.user_id)
                context.startActivity(intent)
            }
        }
    }

    /**
     * pega o numero de itens na lista
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * um ViewHolder descreve um item view and metadata sobre seu lugar dentro do recyclerView
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}