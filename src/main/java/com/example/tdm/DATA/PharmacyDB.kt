package com.example.tdm.DATA

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tdm.DATA.Converters.MedicamentConverter
import com.example.tdm.DATA.DAO.CommandeDAO
import com.example.tdm.DATA.Model.Pharmacy
import com.example.tdm.DATA.DAO.PharmacyDAO
import com.example.tdm.DATA.DAO.TraitementDAO
import com.example.tdm.DATA.Model.Commande
import com.example.tdm.DATA.Model.Medicament
import com.example.tdm.DATA.Model.Traitement

@Database(entities = [Pharmacy::class , Commande::class, Traitement ::class] , version = 1 , exportSchema = false)
@TypeConverters(MedicamentConverter::class)
abstract class PharmacyDB: RoomDatabase() {

    abstract fun pharmacyDAO(): PharmacyDAO
    abstract fun commandeDAO(): CommandeDAO
    abstract fun traitementDAO(): TraitementDAO

    companion object {
        @Volatile
        private var INSTANCE : PharmacyDB? =null

        fun getDatabase(context :Context):PharmacyDB{
            val tempInstance = INSTANCE
            if (tempInstance !=null){
                return tempInstance
            }
            synchronized(this){
                val instance  = Room.databaseBuilder(
                    context.applicationContext,
                    PharmacyDB::class.java,
                    "pharmacy database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}