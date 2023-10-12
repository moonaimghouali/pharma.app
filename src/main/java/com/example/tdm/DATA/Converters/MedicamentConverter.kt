package com.example.tdm.DATA.Converters

import androidx.room.TypeConverter
import com.example.tdm.DATA.Model.Medicament

class MedicamentConverter {

    @TypeConverter
    fun ListMedicamentToString(med : List<Medicament>): String? {
        var result = ""
        for (medicament in med){
            result = result+medicament.medicament+":"+medicament.quantite+";"
        }
        return result
    }

    @TypeConverter
    fun StringToListMedicament(str : String): List<Medicament>{
        var result : MutableList<Medicament> = mutableListOf()

        val list = str.split(";")
        for (word in list){
            val list2 = word.split(":")
            result.add(Medicament(list2[0], list2[1]))
        }
        return result
    }
}