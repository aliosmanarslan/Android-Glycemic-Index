package com.aliosmanarslan.glycemicindex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aliosmanarslan.glycemicindex.database.DB
import com.aliosmanarslan.glycemicindex.databinding.ActivityAddFoodBinding
import com.aliosmanarslan.glycemicindex.util.FoodUtil

class AddFood : AppCompatActivity() {

    private lateinit var binding: ActivityAddFoodBinding
    lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addFoodToAddCategory.setOnClickListener {
            val intent = Intent(binding.root.context, AddCategory::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun btnAddFood(view: View){

        db = DB(this)

        val name= binding.addFoodNameTxt.text.toString()
        val glycemicIndex=binding.addFoodGlyIndexTxt.text.toString()
        val carbohydrateAmount=binding.addFoodFoodCarAmountTxt.text.toString()
        val caloriesAmount=binding.addFoodFoodCalAmountTxt.text.toString()

        if(name.isEmpty()){
            binding.addFoodNameTxt.error = "Uyarı: Besin adını boş bırakmayınız!"
            binding.addFoodNameTxt.requestFocus()
            return
        }else if (glycemicIndex.isEmpty()){
            binding.addFoodGlyIndexTxt.error = "Uyarı: Bu alan boş geçilmez!"
            binding.addFoodGlyIndexTxt.requestFocus()
            return
        }else if (carbohydrateAmount.isEmpty()){
            binding.addFoodFoodCarAmountTxt.error = "Uyarı: Bu alan boş geçilmez!"
            binding.addFoodFoodCarAmountTxt.requestFocus()
            return
        }else if (caloriesAmount.isEmpty()){
            binding.addFoodFoodCalAmountTxt.error = "Uyarı: Bu alan boş geçilmez!"
            binding.addFoodFoodCalAmountTxt.requestFocus()
            return
        }else{
            db.addFood(FoodUtil.cid,name, glycemicIndex, carbohydrateAmount, caloriesAmount)
            Toast.makeText(this@AddFood, "Ekleme İşlemi Başarılıyla Tamamlandı", Toast.LENGTH_LONG).show()
            val i = Intent(this, ListCategory::class.java)
            startActivity(i)
            finish()
        }

    }

}