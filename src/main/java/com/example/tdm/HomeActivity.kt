package com.example.tdm

import android.app.Activity
import com.example.tdm.MyApplication.Companion.pharmacies
import com.example.tdm.MyApplication.Companion.pharmaciesChanged
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tdm.DATA.ViewModel.CommandeViewModel
import com.example.tdm.DATA.ViewModel.PharmacyViewModel
import com.example.tdm.DATA.ViewModel.TraitementViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var mPharmacyViewModel  : PharmacyViewModel
    private lateinit var mCommandeViewModel  : CommandeViewModel
    private lateinit var mTraitementViewModel  : TraitementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mPharmacyViewModel = ViewModelProvider(this)[PharmacyViewModel::class.java]
        mCommandeViewModel = ViewModelProvider(this)[CommandeViewModel::class.java]
        mTraitementViewModel = ViewModelProvider(this)[TraitementViewModel::class.java]

        val navController = findNavController(R.id.fragmentContainerHome)
        HomeBottomNav.setupWithNavController(navController)

        val pref  = getSharedPreferences("pref",Context.MODE_PRIVATE) ?: return

        btn_logOut.setOnClickListener {
            with(pref.edit()) {
                putString("status","not connected")
                putInt("id_client", -1)
                apply()
            }

//            savePharmaciesToLocalBDD()
//            saveCommandesToLocalBDD()
//            saveTraitementsToLocalBDD()

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view =  super.onCreateView(name, context, attrs)
        return view
    }

    private fun savePharmaciesToLocalBDD() {
        val data = pharmacies
        if (pharmaciesChanged ==true){

            for (pharmacie in data){
                mPharmacyViewModel.addPharmacy(pharmacie)
            }
        }
        //Toast.makeText(this, "inserted ${data.size}", Toast.LENGTH_SHORT).show()
    }

    private fun saveCommandesToLocalBDD() {
        val data = MyApplication.commandes
        if (MyApplication.commandesChanged ==true){

            for (commande in data){
                mCommandeViewModel.addCommande(commande)
            }
        }
        //Toast.makeText(this, "inserted ${data.size} commandes", Toast.LENGTH_SHORT).show()
    }

    private fun saveTraitementsToLocalBDD() {
        val data = MyApplication.Traitements
        if (MyApplication.TraitementsChanged ==true){

            for (traitement in data){
                mTraitementViewModel.addTraitement(traitement)
            }
        }
       // Toast.makeText(this, "inserted ${data.size} traitements", Toast.LENGTH_SHORT).show()
    }
}