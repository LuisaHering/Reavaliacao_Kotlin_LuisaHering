package com.example.minhalojinha.ui.fragments

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.minhalojinha.R
import kotlinx.android.synthetic.main.dialog_progress.*

/**
 * Um base fragment class é usado para difinir as funcoes e membros que nos usaremos em todos os fragments.
 * Ele herda a Fragment class para que em outras fragment classes nos iremos substituir o Fragment  com BaseFragment.
 */
open class BaseFragment : Fragment() {

    /**
     * Isso é uma instancia de progress dialog que iniciaremos depois
     */
    private lateinit var mProgressDialog: Dialog
    // END

    /**
     * essa funcao é usada p mostrar o progress dialog com o titulo e mensagem ao user
     */
    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireActivity())

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.tv_progress_text.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    /**
     * essa funcao dismiss o progress dialog se estiver visibel ao usuario
     */
    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}