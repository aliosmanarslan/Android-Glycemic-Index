package com.aliosmanarslan.glycemicindex.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.aliosmanarslan.glycemicindex.models.Category
import com.aliosmanarslan.glycemicindex.models.Food
import com.aliosmanarslan.glycemicindex.service.HtmlParser

class DB(var context: Context, name: String? = "projectali.db", factory: SQLiteDatabase.CursorFactory? = null, version: Int = 1) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(
            "CREATE TABLE \"Food\" (\n" +
                    "\t\"fid\"\tINTEGER,\n" +
                    "\t\"cid\"\tTEXT,\n" +
                    "\t\"name\"\tTEXT,\n" +
                    "\t\"glycemicIndex\"\tTEXT,\n" +
                    "\t\"carbohydrateAmount\"\tTEXT,\n" +
                    "\t\"caloriesAmount\"\tTEXT,\n" +
                    "\tPRIMARY KEY(\"fid\" AUTOINCREMENT),\n" +
                    "\tFOREIGN KEY(\"cid\") REFERENCES \"Category\"(\"cid\")\n" +
                    ");"
        )
        p0!!.execSQL(
            "CREATE TABLE \"Category\" (\n" +
                    "\t\"cid\"\tTEXT,\n" +
                    "\t\"title\"\tTEXT,\n" +
                    "\tPRIMARY KEY(\"cid\")\n" +
                    ");"
        )

        val service = HtmlParser()
        service.getDataFromSource(context)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS product")
        onCreate(p0)
    }

    fun addCategory(title:String, cid:String){
        val write = this.writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("cid", cid)
        val insertCount = write.insert("Category", null,values)
    }

    fun addFood(cid:String, name:String, glycemicIndex:String, carbohydrateAmount:String, caloriesAmount:String){
        val write = this.writableDatabase
        val values = ContentValues()
        values.put("cid", cid)
        values.put("name", name)
        values.put("glycemicIndex", glycemicIndex)
        values.put("carbohydrateAmount", carbohydrateAmount)
        values.put("caloriesAmount", caloriesAmount)
        val insertCount = write.insert("Food",null,values)
    }

    fun allCategory() : ArrayList<Category> {
        val list = ArrayList<Category>()
        val read = this.readableDatabase
        val querySql = "select * from Category"
        val cursor = read.rawQuery(querySql, null)
        while ( cursor.moveToNext() ) {
            var cid:String = cursor.getString(0)
            var title:String = cursor.getString(1)
            val p = Category(cid, title)
            list.add(p)
        }
        return list
    }

    fun allFood(cid:String) : ArrayList<Food> {
        val list = ArrayList<Food>()
        val read = this.readableDatabase
        val querySql = "select * from Food where cid =?"
        val cursor = read.rawQuery(querySql, arrayOf(cid))
        while ( cursor.moveToNext() ) {
            var fid:Int = cursor.getInt(0)
            var cid:String = cursor.getString(1)
            var name:String = cursor.getString(2)
            var glycemicIndex:String = cursor.getString(3)
            var carbohydrateAmount:String = cursor.getString(4)
            var caloriesAmount:String = cursor.getString(5)
            val p = Food(fid, cid,name,glycemicIndex,carbohydrateAmount,caloriesAmount)
            list.add(p)
        }
        return list
    }

    fun allFood() : ArrayList<Food> {
        val list = ArrayList<Food>()
        val read = this.readableDatabase
        val querySql = "select * from Food"
        val cursor = read.rawQuery(querySql, null)
        while ( cursor.moveToNext() ) {
            var fid:Int = cursor.getInt(0)
            var cid:String = cursor.getString(1)
            var name:String = cursor.getString(2)
            var glycemicIndex:String = cursor.getString(3)
            var carbohydrateAmount:String = cursor.getString(4)
            var caloriesAmount:String = cursor.getString(5)
            val p = Food(fid, cid,name,glycemicIndex,carbohydrateAmount,caloriesAmount)
            list.add(p)
        }
        return list
    }

    fun deleteCategory(cid:String):Int{
        val write =this.writableDatabase
        val count = write.delete("Category", "cid = ?", arrayOf<String>(java.lang.String.valueOf(cid)))
        write.delete("Food", "cid = ?",arrayOf<String>(java.lang.String.valueOf(cid)))
        return count
    }

    fun deleteFood(fid:Int):Int{
        val write = this.writableDatabase
        val count = write.delete("Food","fid="+fid,null)
        return count
    }

    fun updateCategory(cid: String,title: String):Int{
        val write = this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put("cid",cid)
        contentValues.put("title",title)
        return write.update("Category", contentValues, "cid = ?", arrayOf(cid.toString()))

    }

    fun updateFood(fid:Int, name:String, glycemicIndex:String, carbohydrateAmount:String, caloriesAmount:String): Int {
        val write = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("glycemicIndex", glycemicIndex)
        contentValues.put("carbohydrateAmount", carbohydrateAmount)
        contentValues.put("caloriesAmount", caloriesAmount)
        return write.update("Food", contentValues, "fid = ?", arrayOf(fid.toString()))
    }
}