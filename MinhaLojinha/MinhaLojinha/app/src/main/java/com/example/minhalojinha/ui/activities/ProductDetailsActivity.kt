package com.example.minhalojinha.ui.activities

import android.os.Bundle
import com.example.minhalojinha.R
import com.example.minhalojinha.firestore.FirestoreClass
//import com.example.minhalojinha.models.Cart
import com.example.minhalojinha.models.Product
import com.example.minhalojinha.utils.Constants
import com.example.minhalojinha.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_product_details.*
//import kotlinx.android.synthetic.main.item_cart_layout.view.*

/**
 * Tela de detalhes do Product
 */
class ProductDetailsActivity : BaseActivity() {

    private lateinit var mProductDetails: Product

    // A global variable for product id.
    private var mProductId: String = ""

    /**
     * essa funcao é automaticamente criada pelo endroid quando a activity class é criada
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_product_details)

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
        }

        var productOwnerId: String = ""

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            productOwnerId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }

        setupActionBar()


        getProductDetails()
    }


    /**
     * Uma func pra setup a actionBar
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_product_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_product_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * uma func pra chamar a firestoreClass func que vai pegar os detalhes do produto da Cloud Firestore usando o Product Id
     */
    private fun getProductDetails() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of FirestoreClass to get the product details.
        FirestoreClass().getProductDetails(this@ProductDetailsActivity, mProductId)
    }

    /**
     *  func praa notificar o resultado bem sucedido do product detais com base na product id
     * @param product A model class with product details.
     */
    fun productDetailsSuccess(product: Product) {

        hideProgressDialog()

        mProductDetails = product

        // Populate the product details in the UI.
        GlideLoader(this@ProductDetailsActivity).loadProductPicture(
            product.image,
            iv_product_detail_image
        )

        tv_product_details_title.text = product.title
        tv_product_details_price.text = "$${product.price}"
        tv_product_details_description.text = product.description
        tv_product_details_nota.text = product.nota
    }

}