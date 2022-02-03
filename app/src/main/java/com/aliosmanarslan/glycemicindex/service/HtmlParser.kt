package com.aliosmanarslan.glycemicindex.service

import android.content.Context
import android.util.Log
import com.aliosmanarslan.glycemicindex.database.DB
import com.aliosmanarslan.glycemicindex.models.Category
import com.aliosmanarslan.glycemicindex.models.Food
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class HtmlParser {
    lateinit var db: DB

    fun getDataFromSource(context: Context) {

        db = DB(context)
        Thread(Runnable {
            val stringBuilder = StringBuilder()
            try {
                val doc: Document = Jsoup.connect("http://kolaydoktor.com/saglik-icin-yasam/diyet-ve-beslenme/besinlerin-glisemik-indeks-tablosu/0503/1").get()
                val table: Elements = doc.select("table")

                table.forEach {
                    val rows : Elements = it.select("tbody").select("tr")
                    var cid:String = ""

                    for((index, row) in rows.withIndex()){
                        if(index == 0){
                            val columns = row.select("p").text()

                            if(columns.trim().isEmpty()) continue
                            Log.d("Category", columns)

                            val category = Category()
                            category.title = columns
                            cid = Category().cid
                            db.addCategory(columns,cid)
                        }
                        else if (index == 1){
                            Log.d("Sütün adı: (${row.select("p").size}) : " , row.select("p").text())
                        }
                        else{
                            val elements: Elements = row.select("p")
                            val food = Food()
                            food.cid = cid
                            food.name = elements[0].text()
                            food.glycemicIndex = elements[1].text().trim()
                            food.carbohydrateAmount = elements[2].text().toString()
                            food.caloriesAmount = elements[3].text().toString()
                            db.addFood(food.cid!!,food.name!!,food.glycemicIndex!!,food.carbohydrateAmount!!,food.caloriesAmount!!)
                        }
                    }
                }

            } catch (e: Exception) {
                stringBuilder.append("Parser Error : ").append(e.message).append("\n")
                Log.d("Parser Error", e.toString())
            }

        }).start()
    }
}