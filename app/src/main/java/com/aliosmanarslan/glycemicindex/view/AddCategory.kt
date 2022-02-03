package com.aliosmanarslan.glycemicindex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aliosmanarslan.glycemicindex.database.DB
import com.aliosmanarslan.glycemicindex.databinding.ActivityAddCategoryBinding
import com.aliosmanarslan.glycemicindex.models.Category
import com.aliosmanarslan.glycemicindex.util.FoodUtil

class AddCategory : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryBinding
    lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)

        val t=intent.getStringExtra("title")

        binding.addCategoryName.setText(t)
        binding.addCategoryBtn.setOnClickListener {

            if(binding.addCategoryName.text.toString().isEmpty()){

                binding.addCategoryName.error = "Hata: Kategori adını boş bırakmayınız!"
                binding.addCategoryName.requestFocus()
                return@setOnClickListener
            }else{

                val title = binding.addCategoryName.text.toString()
                val cid = Category().cid
                db.addCategory(title,cid)
                val intent= Intent(this,ListCategory::class.java)
                startActivity(intent)
            }
        }

        binding.updateCategoryBtn.setOnClickListener {

            if(binding.addCategoryName.text.toString().isEmpty()){
                binding.addCategoryName.error = "Hata: Kategori adını boş bırakmayınız!"
                binding.addCategoryName.requestFocus()
                return@setOnClickListener
            }else{

            val title=binding.addCategoryName.text.toString()
            db.updateCategory(FoodUtil.cid,title)
            val intent= Intent(this,ListCategory::class.java)
            startActivity(intent)
            }
        }

        binding.addCategoryToFood.setOnClickListener {
            val intent = Intent(binding.root.context, AddFood::class.java)
            startActivity(intent)
            finish()
        }
    }
}