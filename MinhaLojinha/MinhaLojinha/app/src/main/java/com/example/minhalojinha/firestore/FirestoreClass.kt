package com.example.minhalojinha.firestore

import android.app.Activity
import android.util.Log
import com.example.minhalojinha.models.User
import com.example.minhalojinha.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.fragment.app.Fragment
import com.example.minhalojinha.models.Product
import com.example.minhalojinha.ui.activities.*
import com.example.minhalojinha.ui.fragments.ProductsFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FirestoreClass {

    //iinstanciamento da firestore
    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * A function to make an entry of the registered user in the FireStore database.
     * SET
     */
    fun registerUser(activity: RegisterActivity, userInfo: User) {

        // "users" é o nome da colecao, se a colecao ja tiver criada entao ele nao criara novamente
        mFireStore.collection(Constants.USERS)
            // Document id para os campos de user, aqui o document é a user id
            .document(userInfo.id)
            //SetOption setado para merge, para merge data para data que agora n esta filled corretamente, para que n dele e ao inves mergeie os dados novos
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                //call function da base activity pra transferir os resultados
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }

    /**
     * Uma funcao para oegar o user id do usuario logado atual
     * mas estamos pegando da authetication module, n da database firestore
     * GET
     */
    fun getCurrentUserID(): String {
        //instancia do currentUser usando a FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        //uma variavel para assign o currentUserId se ele n for nulo or else ele sera blank
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    /**
     * Uma funcao para pegar os detalhes do logged user da FireStore Database
     * GET
     */
    fun getUserDetails(activity: Activity) {

        //aqui nos passamos a collection name de qual queremos a data
        mFireStore.collection(Constants.USERS)
            //a document id para pegar os campos do user
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                //Aqui recebemos o snapshot do documento que é convertido num objeto de User Data model
                val user = document.toObject(User::class.java)!!

                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MINHALOJINHA_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                //cria uma instancia do editor que nos ajuda a editar a SharedPreference. é basicamennte um pequeno storage q voce pode guardar no device
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                // Key: logged_in_username
                // example of Value: Joao vitor
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()

                //TODO passa o resultado para a Login Activity
                when (activity) {
                    is LoginActivity -> {
                        // chama funcao da base activity p transferir o resultado p a mesma
                        activity.userLoggedInSuccess(user)
                    }

                    is SettingsActivity -> {
                        // chama funcao da base activity p transferir o resultado p a mesma
                        activity.userDetailsSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                //esconde o progress dialog se tiver algum erro e imprime o erro no log
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                    is SettingsActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    /**
     * Uma funcao para update o user profile data na FireStore Database
     *
     * @param activity The activity is used for identifying the Base activity to which the result is passed.
     * @param userHashMap HashMap of fields which are to be updated.
     * UPDATE
     */
    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        // Nome da colecao
        mFireStore.collection(Constants.USERS)
            // ID do Documento que a data vai ser atualizada. Aqui a document id é o user id do logado.
            .document(getCurrentUserID())
            // A HashMap dos campos que deverao ser updated
            .update(userHashMap)
            .addOnSuccessListener {

                // notificar sucesso
                when (activity) {
                    is UserProfileActivity -> {
                        // chama a func do base activity pra a transferencia do resultado
                        activity.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Erro ao atualizar os user details.",
                    e
                )
            }
    }

    /**
     * Uma funcao para upload a imagem ao cloud storage
     */
    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {

        //getting a storage reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            //fazendo o nome do file (User_Profile_Image... Product_Image)
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        // FLOW PRODUCT CREATION 4 - colocamos o file online
        sRef.putFile(imageFileURI!!)
            //receberemos o taskSnapshot
            .addOnSuccessListener { taskSnapshot ->
                // o upload da imagem é bem sucedido
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // FLOW PRODUCT CREATION 5 - recebemos o download URL do snapshot, ent sabemos onde a imagem está localizada
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        //isso basicamente sera o link onde ficara stored na nossa db
                        Log.e("Downloadable Image URL", uri.toString())

                        // aqui chama uma funcao da base activity pra transferir o resultado
                        when (activity) {
                            is UserProfileActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }

                            is AddProductActivity -> {
                                // FLOW PRODUCT CREATION 6 - chamamos o image upload success
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }
                    }
            }
            .addOnFailureListener { exception ->

                //esconde o progress dialog se tiver qualquer erro e printa o erro no log
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }

                    is AddProductActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    /**
     * Uma funcao para fazer uma entry do produto do usuario na cloud firestore database
     */
    fun uploadProductDetails(activity: AddProductActivity, productInfo: Product) {

        // FLOW PRODUCT CREATION 9 - lanca para a a cloud
        mFireStore.collection(Constants.PRODUCTS)
            .document()
            .set(productInfo, SetOptions.merge())
            .addOnSuccessListener {

                activity.productUploadSuccess()
            }
            .addOnFailureListener { e ->

                activity.hideProgressDialog()

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    e
                )
            }
    }

    /**
     * A function to get the products list from cloud firestore.
     *
     * @param fragment The fragment is passed as parameter as the function is called from fragment and need to the success result.
     */
    fun getProductsList(fragment: Fragment) {
        // nome da colecao p PRODUCTS
        mFireStore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .get() // get o snapshot
            .addOnSuccessListener { document ->

                // aqui pegamos a lista de boards como documents
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // um for loop per a lista de documents p convertelos em Products ArrayList
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id

                    productsList.add(product)
                }

                when (fragment) {
                    is ProductsFragment -> {
                        fragment.successProductsListFromFireStore(productsList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Esconde o progress dialog se tem qualquer erro baseado na base class instance
                when (fragment) {
                    is ProductsFragment -> {
                        fragment.hideProgressDialog()
                    }
                }

                Log.e("Get Product List", "Erro ao pegar a Product List.", e)
            }
    }

    /**
     * Uma func pra deletar o Product da cloud firestore
     */
    fun deleteProduct(fragment: ProductsFragment, productId: String) {

        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .delete()
            .addOnSuccessListener {

                // notifica o sucesso a base class
                fragment.productDeleteSuccess()
            }
            .addOnFailureListener { e ->

                fragment.hideProgressDialog()

                Log.e(
                    fragment.requireActivity().javaClass.simpleName,
                    "Erro ao deletar o produto.",
                    e
                )
            }
    }

    /**
     * Uma func pra GET os detalhes do produto baseados no product id.
     */
    fun getProductDetails(activity: ProductDetailsActivity, productId: String) {

        // O nome da colecao pra PRODUCTS
        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .get() // document snapshots
            .addOnSuccessListener { document ->

                // aqui pegamos os product details em forma de document
                Log.e(activity.javaClass.simpleName, document.toString())

                // converte o snapshot ao objeto de Product data model class
                val product = document.toObject(Product::class.java)!!

                activity.productDetailsSuccess(product)
            }
            .addOnFailureListener { e ->

                activity.hideProgressDialog()

                Log.e(activity.javaClass.simpleName, "Erro ao pegar o produto.", e)
            }
    }

}
