package com.aliosmanarslan.glycemicindex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aliosmanarslan.glycemicindex.R
import com.aliosmanarslan.glycemicindex.adapters.CategoryAdapter
import com.aliosmanarslan.glycemicindex.database.DB
import com.aliosmanarslan.glycemicindex.databinding.ActivityListCategoryBinding
import com.aliosmanarslan.glycemicindex.models.Category

class ListCategory : AppCompatActivity() {

    private lateinit var binding: ActivityListCategoryBinding
    lateinit var db : DB
    lateinit var datas : ArrayList<Category>
    lateinit var adapter : CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)
        datas = db.allCategory()
        adapter = CategoryAdapter(datas)
        binding.CategoryList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )
        binding.CategoryList.adapter = adapter
        binding.bottomNavigation.setOnItemSelectedListener() {

            when (it.getItemId()) {
            R.id.item1 -> {
                true
            }
            R.id.item2 -> {
                val i = Intent(this, AddCategory::class.java)
                startActivity(i)
                //overridePendingTransition(R.anim.inanim, R.anim.outanim);
                true
            }
            R.id.item3 -> {
                Toast.makeText(this, "Geliştirici: Ali Osman Arslan", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(it)
        }
        }
    }

    override fun onResume() {
        super.onResume()
        datas = db.allCategory()
        adapter.notifyDataSetChanged()
    }
}