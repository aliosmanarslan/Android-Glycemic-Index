package com.aliosmanarslan.glycemicindex.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.glycemicindex.database.DB
import com.aliosmanarslan.glycemicindex.databinding.CategoryRowBinding
import com.aliosmanarslan.glycemicindex.models.Category
import com.aliosmanarslan.glycemicindex.util.FoodUtil
import com.aliosmanarslan.glycemicindex.view.AddCategory
import com.aliosmanarslan.glycemicindex.view.ListFood

class CategoryAdapter( val arr: ArrayList<Category> ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    class ViewHolder ( val binding: CategoryRowBinding) : RecyclerView.ViewHolder( binding.root  ) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = CategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item=arr.get(position)
        holder.binding.apply {
            txtCategory.setText(item.title)
            rCardView.setOnClickListener {

                val db = DB(root.context)
                FoodUtil.foodList.clear()
                FoodUtil.cid = item.cid
                FoodUtil.foodList.addAll(db.allFood(item.cid))

                val i = Intent(holder.binding.root.context,ListFood::class.java)
                notifyDataSetChanged()
                holder.binding.root.context.startActivity(i)
            }
            rCardView.setOnLongClickListener {
                val alert = AlertDialog.Builder(holder.binding.root.context)

                alert.setTitle("Silme İşlemi!!")
                alert.setMessage("Silmek İstediğinizden Emin Misiniz?")
                alert.setNegativeButton("Güncelle", DialogInterface.OnClickListener { dialogInterface, i ->
                    val i = Intent(holder.binding.root.context,AddCategory::class.java)
                    FoodUtil.cid = item.cid
                    i.putExtra("title",item.title)
                    holder.binding.root.context.startActivity(i)
                })
                alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                    val db = DB(holder.binding.root.context)
                    val count = db.deleteCategory(item.cid)

                    if ( count > 0 ) {
                        Toast.makeText(holder.binding.root.context, "Silme İşlemi Başarılı!!", Toast.LENGTH_SHORT).show()
                        arr.removeAt(position)
                        notifyItemRemoved(position)

                    }else {
                        Toast.makeText(holder.binding.root.context, "Silme İşlemi Hatası!!", Toast.LENGTH_SHORT).show()
                    }
                })
                alert.show()
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}