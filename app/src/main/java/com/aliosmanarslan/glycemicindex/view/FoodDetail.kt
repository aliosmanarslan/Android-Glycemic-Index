package com.aliosmanarslan.glycemicindex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.aliosmanarslan.glycemicindex.R
import com.aliosmanarslan.glycemicindex.database.DB
import com.aliosmanarslan.glycemicindex.databinding.ActivityFoodDetailBinding
import com.aliosmanarslan.glycemicindex.util.FoodUtil

class FoodDetail : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding
    var fid:Int=0
    lateinit var db:DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db= DB(this)

        var fid=intent.getIntExtra("fid",0)
        val name=intent.getStringExtra("name")
        val glycemicIndex=intent.getStringExtra("glycemicIndex")
        val carbohydrateAmount=intent.getStringExtra("carbohydrateAmount")
        val caloriesAmount=intent.getStringExtra("caloriesAmount")

        val p = intent.getIntExtra("p", -1)

        binding.detailFoodName.setText(name)
        binding.detailFoodGlyIndex.setText(glycemicIndex)
        binding.detailFoodCarAmount.setText(carbohydrateAmount)
        binding.detailFoodCalAmount.setText(caloriesAmount)


        binding.detailFoodUpdateBtn.setOnClickListener {

            val name = binding.detailFoodName.text.toString()
            val glycemicIndex=binding.detailFoodGlyIndex.text.toString()
            val carbohydrateAmount=binding.detailFoodCarAmount.text.toString()
            val caloriesAmount=binding.detailFoodCalAmount.text.toString()

            if(name.isEmpty()){
                binding.detailFoodName.error = "Uyarı: Besin adını boş bırakmayınız!"
                binding.detailFoodName.requestFocus()
                return@setOnClickListener
            }else if (glycemicIndex.isEmpty()){
                binding.detailFoodGlyIndex.error = "Uyarı: Bu alan boş geçilmez!"
                binding.detailFoodGlyIndex.requestFocus()
                return@setOnClickListener
            }else if (carbohydrateAmount.isEmpty()){
                binding.detailFoodCarAmount.error = "Uyarı: Bu alan boş geçilmez!"
                binding.detailFoodCarAmount.requestFocus()
                return@setOnClickListener
            }else if (caloriesAmount.isEmpty()){
                binding.detailFoodCalAmount.error = "Uyarı: Bu alan boş geçilmez!"
                binding.detailFoodCalAmount.requestFocus()
                return@setOnClickListener
            }else{
                db.updateFood(fid,name,glycemicIndex,carbohydrateAmount,caloriesAmount)

                FoodUtil.foodList[p].name = name
                FoodUtil.foodList[p].glycemicIndex = glycemicIndex
                FoodUtil.foodList[p].carbohydrateAmount = carbohydrateAmount
                FoodUtil.foodList[p].caloriesAmount = caloriesAmount

                Toast.makeText(this@FoodDetail, "Güncelleme İşlemi Başarılıyla Tamamlandı", Toast.LENGTH_LONG).show()

                val intent = Intent(binding.root.context, ListFood::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.updateFoodToCategory.setOnClickListener {
            val intent = Intent(binding.root.context, AddCategory::class.java)
            startActivity(intent)
            finish()
        }
    }
}