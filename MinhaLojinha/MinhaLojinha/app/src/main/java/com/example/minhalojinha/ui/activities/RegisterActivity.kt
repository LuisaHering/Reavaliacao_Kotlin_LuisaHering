package com.example.minhalojinha.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.minhalojinha.R
import com.example.minhalojinha.firestore.FirestoreClass
import com.example.minhalojinha.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login.setOnClickListener {

            // Abre a tela de register quando o user clica no text
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener {
            registerUser()
        }

    }

    /**
     * funcao para validar as entries de new user //se o campo estiver vazio...
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * funcao para registrar o user com email e senha usando FirebaseAuth, é como populamos a colecao que vemos na firestoren Users
     */
    private fun registerUser() {

        // Checa com a validade fuction se os campos foram validos ou n
        if (validateRegisterDetails()) {

            // mostra o dialogo de progress
            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }

            //cria uma instancia e register o user com email e senha
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    //addOnCompleteListener é tipo falar eu quero executar algo quando isso for concluido
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // se o registro for bem sucedido
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            //instancia do User data model class
                            val user = User(
                                firebaseUser.uid,
                                et_first_name.text.toString().trim { it <= ' ' },
                                et_last_name.text.toString().trim { it <= ' ' },
                                et_email.text.toString().trim { it <= ' ' }
                            )

                            //passa os valores requeridos no construtor
                            FirestoreClass().registerUser(this@RegisterActivity, user)


//                            FirebaseAuth.getInstance().signOut()
//                            finish()
                        } else {
                            hideProgressDialog()
                            // se o registering nao for bem sucedido mostra a mensagem de erro
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }

    /**
     * funcao para notificar o resultado de sucesso da entry na Firestore quando o usuario for registrado com sucesso
     */
    fun userRegistrationSuccess() {

        // esconde o progress dialog
        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()


       //aqui o novo usuario registrado é automaticamente signed-in ent nos só signout o usuario do firebase
        FirebaseAuth.getInstance().signOut()
        finish()
    }

}