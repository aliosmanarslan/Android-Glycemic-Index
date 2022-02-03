package com.aliosmanarslan.glycemicindex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.aliosmanarslan.glycemicindex.R
import com.aliosmanarslan.glycemicindex.adapters.FoodAdapter
import com.aliosmanarslan.glycemicindex.databinding.ActivityListFoodBinding
import com.aliosmanarslan.glycemicindex.util.FoodUtil
import android.widget.Toast

class ListFood : AppCompatActivity() {

    private lateinit var binding: ActivityListFoodBinding
    lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewListener()

        val data = FoodUtil.foodList
        adapter = FoodAdapter(data)
        binding.foodList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false )
        binding.foodList.adapter = adapter
        binding.bottomNavigation.setOnItemSelectedListener() {

            when (it.getItemId()) {
                R.id.item1 -> {
                    val i = Intent(this, ListCategory::class.java)
                    startActivity(i)
                    true
                }
                R.id.item2 -> {
                    val i = Intent(this, AddFood::class.java)
                    startActivity(i)
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

    fun searchViewListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        adapter.searchFoods(p0)
                    } else {
                        adapter.searchFoods("")
                        return false
                    }
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        adapter.searchFoods(p0)
                    } else {
                        adapter.searchFoods("")
                        return false
                    }
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}