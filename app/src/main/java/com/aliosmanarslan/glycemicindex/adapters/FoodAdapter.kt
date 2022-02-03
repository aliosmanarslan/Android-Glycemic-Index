package com.aliosmanarslan.glycemicindex.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.glycemicindex.database.DB
import com.aliosmanarslan.glycemicindex.databinding.FoodRowBinding
import com.aliosmanarslan.glycemicindex.models.Food
import com.aliosmanarslan.glycemicindex.view.FoodDetail

class FoodAdapter( val arr: ArrayList<Food> ) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    val searchList = ArrayList<Food>(arr)

    class ViewHolder ( val binding: FoodRowBinding) : RecyclerView.ViewHolder( binding.root  ) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = FoodRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bind)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=searchList.get(position)

        holder.binding.apply {
            txtName.setText(item.name)
            txtGlyindex.setText("Glisemik indeks: " + item.glycemicIndex)
            txtKarbon.setText("Karbonhidrat : " + item.carbohydrateAmount)
            txtCalori.setText("Kalori : " + item.caloriesAmount)

            rCardView.setOnClickListener {
                val intent = Intent(holder.binding.root.context, FoodDetail::class.java)

                notifyDataSetChanged()
                intent.putExtra("fid",item.fid)
                intent.putExtra("name",item.name)
                intent.putExtra("glycemicIndex",item.glycemicIndex)
                intent.putExtra("carbohydrateAmount",item.carbohydrateAmount)
                intent.putExtra("caloriesAmount",item.caloriesAmount)
                intent.putExtra("p",position)

                holder.binding.root.context.startActivities(arrayOf(intent))
            }
            rCardView.setOnLongClickListener {
                val alert = AlertDialog.Builder(holder.binding.root.context)

                alert.setTitle("Silme İşlemi!!")
                alert.setMessage("Silmek İstediğinizden Emin Misiniz?")
                alert.setNegativeButton("İptal", DialogInterface.OnClickListener { dialogInterface, i ->  })
                alert.setPositiveButton("Evet", DialogInterface.OnClickListener { dialogInterface, i ->
                    val db = DB(holder.binding.root.context)
                    val count = db.deleteFood(item.fid!!)

                    if ( count > 0 ) {
                        Toast.makeText(holder.binding.root.context, "Silme İşlemi Başarılı!!", Toast.LENGTH_SHORT).show()
                        arr.removeAt(position)
                        searchList.removeAt(position)
                        notifyItemRemoved(position)
                    }else {
                        Toast.makeText(holder.binding.root.context, "Silme İşlemi Hatası!", Toast.LENGTH_SHORT).show()
                    }
                })
                alert.show()
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    fun searchFoods(search : String){
        searchList.clear()
        for (array in arr){
            if ((array.name!!.lowercase().contains(search.lowercase())) || (array.glycemicIndex!!.contains(search))
                || (array.carbohydrateAmount!!.contains(search)) || (array.caloriesAmount!!.contains(search))){
                searchList.add(array)
            }
        }
        notifyDataSetChanged()
    }
}