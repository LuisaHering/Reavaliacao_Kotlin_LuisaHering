package com.example.minhalojinha.ui.activities

import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.view.View

import com.example.minhalojinha.R
import com.example.minhalojinha.firestore.FirestoreClass
import com.example.minhalojinha.models.User
import com.example.minhalojinha.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Click listener do login button
        btn_login.setOnClickListener(this)
        // Click listener do register text
        tv_register.setOnClickListener(this)
    }

    /**
     * no login screen os componentes clicaveis sao Login Button and Register Text
     */
    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {

                R.id.btn_login -> {
                   logInRegisteredUser()
                }

                R.id.tv_register -> {
                    // Abre a tela de register quando o user clica no text register
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    /**
     * function para validar os campos de login do usuario
     */
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * function para login, o usuario podera logar usando o email e senha registrados com Firebase Autentication
     */
    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Get the text from editText and trim the space
            val email = et_email.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                       FirestoreClass().getUserDetails(this@LoginActivity)
                    } else {
                        // esconde o progress dialog
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    /**
     * Funcao para notificar  o usuario que logou com sucesso e pegar os uder details da FireStore database depois da autenticacao
     */
    fun userLoggedInSuccess(user: User) {

        // Hide the progress dialog.
        hideProgressDialog()

        // o equal zero é pra estabelecer se é a primeira vez que o usuario logou na aplicacao
        if (user.profileCompleted == 0) {
            //se o perfil do usuario estiver incompleto, launch o userProfileActivity
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            //o user que era um objeto normal antes, agora é um parcelable objects
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else {
            //redirecionar o user para a Dashboard screen depois do login
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
        }

        finish()
    }
}