package com.example.minhalojinha.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minhalojinha.R
import com.example.minhalojinha.firestore.ApiClass
import com.example.minhalojinha.ui.activities.SettingsActivity
import com.example.minhalojinha.ui.adapters.DashboardListAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*

// Dashboard Fragment
class DashboardFragment : BaseFragment() {


    private lateinit var mRootView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return mRootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_settings -> {

                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        //getQuoteFromApi()
    }

    /**
     * uma funcao que conecta com a API zenquotes e faz um request
     */
    fun getQuoteFromApi() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))
        ApiClass().getQuoteZenList(this@DashboardFragment)
    }


    /**
     * Funcao caso o response tiver retorno
     */
    fun successResponseQuote(quotesArray: ArrayList<String>) {
        hideProgressDialog()

        if (quotesArray.size > 0) {
            rv_dashboard_items.visibility = View.VISIBLE
            tv_no_quotes_found.visibility = View.GONE

            rv_dashboard_items.layoutManager = LinearLayoutManager(activity)
            rv_dashboard_items.setHasFixedSize(true)

            val adapterQuotes = DashboardListAdapter(requireActivity(), quotesArray, this@DashboardFragment)
            rv_dashboard_items.adapter = adapterQuotes
        } else {
            rv_dashboard_items.visibility = View.GONE
            tv_no_quotes_found.visibility = View.VISIBLE
        }
    }
}