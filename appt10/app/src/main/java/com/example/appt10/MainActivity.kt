package com.example.appt10

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appt10.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {


    lateinit var db: SQLiteDatabase
    lateinit var rs : Cursor
    lateinit var adapter: SimpleCursorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var helper = MyMy(applicationContext)
        db = helper.readableDatabase
        rs = db.rawQuery(/* sql = */ "SELECT * FROM MYNE LIMIT 50",/* selectionArgs = */ null)

        /*if (rs.moveToFirst()){
            binding.edtUser.setText(rs.getString(1))
            binding.edtpass.setText(rs.getString(2))
            binding.edtEmail.setText(rs.getString(3))
            binding.edtPhone.setText(rs.getString(4))
        }*/

        //Up
        binding.btnUp.setOnClickListener {
            if (rs.moveToNext()){
                binding.edtUser.setText(rs.getString(1))
                binding.edtpass.setText(rs.getString(2))
                binding.edtEmail.setText(rs.getString(3))
                binding.edtPhone.setText(rs.getString(4))
            }
            else if (rs.moveToFirst()){
                binding.edtUser.setText(rs.getString(1))
                binding.edtpass.setText(rs.getString(2))
                binding.edtEmail.setText(rs.getString(3))
                binding.edtPhone.setText(rs.getString(4))

            }
            else
                Toast.makeText(applicationContext,"No data found",Toast.LENGTH_SHORT,).show()
        }

        //lvFull
        adapter = SimpleCursorAdapter(
            applicationContext,android.R.layout.simple_expandable_list_item_2,rs,
            arrayOf("user","email"), intArrayOf(android.R.id.text1,android.R.id.text2),0
        )
      /*  *//*adapter = SimpleCursorAdapter(
            applicationContext,android.R.layout.simple_expandable_list_item_1,rs,
            arrayOf("phone"), intArrayOf(android.R.id.text1),0*//*
        )*/
        binding.lvFull.adapter = adapter
        //ViewAll
        binding.btnViewAll.setOnClickListener {
            binding.searchView.visibility = View.VISIBLE
            binding.lvFull.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
            binding.searchView.queryHint = "Tìm kiếm trong ${rs.count} bản ghi"
        }

        //Search
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                rs = db.rawQuery("SELECT * FROM MYNE WHERE user LIKE '%${newText}' or email LIKE '%${newText}'",null)
                adapter.changeCursor(rs)
                return true
            }
        })

        //ADD
        binding.btnInsert.setOnClickListener {
            var cv = ContentValues()
            cv.put("user",binding.edtUser.text.toString())
            cv.put("password",binding.edtpass.text.toString())
            cv.put("email",binding.edtEmail.text.toString())
            cv.put("phone",binding.edtPhone.text.toString())
            db.insert("MYNE",null,cv)
            rs.requery()
            adapter.notifyDataSetChanged()

            var ad = AlertDialog.Builder(this)
            ad.setTitle("ADD Record")
            ad.setMessage("ADD thành công")
            ad.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                binding.edtUser.setText("")
                binding.edtpass.setText("")
                binding.edtEmail.setText("")
                binding.edtPhone.setText("")
                binding.edtUser.requestFocus()
            })
            ad.show()
        }

        //Down
        binding.btndow.setOnClickListener {
            if (rs.moveToPrevious()){
                binding.edtUser.setText(rs.getString(1))
                binding.edtpass.setText(rs.getString(2))
                binding.edtEmail.setText(rs.getString(3))
                binding.edtPhone.setText(rs.getString(4))
            }
            else if (rs.moveToLast()){
                binding.edtUser.setText(rs.getString(1))
                binding.edtpass.setText(rs.getString(2))
                binding.edtEmail.setText(rs.getString(3))
                binding.edtPhone.setText(rs.getString(4))
            }
            else
                Toast.makeText(applicationContext,"No data found",Toast.LENGTH_SHORT,).show()
        }


        //UPDATE
        binding.btnUpdate.setOnClickListener {
            var cv = ContentValues()
            cv.put("user", binding.edtUser.text.toString())
            cv.put("password", binding.edtpass.text.toString())
            cv.put("email", binding.edtEmail.text.toString())
            cv.put("phone", binding.edtPhone.text.toString())
            db.update("MYNE", cv, "_id=?", arrayOf(rs.getString(0)))
            rs.requery()
            adapter.notifyDataSetChanged()

            var ad = AlertDialog.Builder(this)
            ad.setTitle("Update Record")
            ad.setMessage("Update thành công")
            ad.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                binding.edtUser.setText("")
                binding.edtpass.setText("")
                binding.edtEmail.setText("")
                binding.edtPhone.setText("")
                binding.edtUser.requestFocus()
            })
            ad.show()
        }



        ///CLear
        binding.btnClear.setOnClickListener {
            binding.edtUser.setText("")
            binding.edtpass.setText("")
            binding.edtEmail.setText("")
            binding.edtPhone.setText("")
            binding.edtUser.requestFocus()
        }


        //Delete
        binding.btnDelete.setOnClickListener {
            db.delete("MYNE","_id=?", arrayOf(rs.getString(0)))
            rs.requery()
            adapter.notifyDataSetChanged()
            //Tbao
            var ad = AlertDialog.Builder(this)
            ad.setTitle("Delete Record")
            ad.setMessage("Delete thành công")
            ad.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                if (rs.moveToFirst()){
                    binding.edtUser.setText("")
                    binding.edtpass.setText("")
                    binding.edtEmail.setText("")
                    binding.edtPhone.setText("")
                    binding.edtUser.requestFocus()
                }
                else
                {
                    binding.edtUser.setText("No data found")
                    binding.edtpass.setText("No data found")
                    binding.edtEmail.setText("No data found")
                    binding.edtPhone.setText("No data found")
                }

            })
            ad.show()
        }

        //context list view lvFull
        registerForContextMenu(binding.lvFull)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(100,11,1,"DELETE")
        menu?.setHeaderTitle("Removing data")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId==11)
        {
            //Toast.makeText(applicationContext,"Click lên item trên menu",Toast.LENGTH_SHORT).show()
            db.delete("MYNE","_id=?", arrayOf(rs.getString(0)))
            rs.requery()
            adapter.notifyDataSetChanged()
            binding.searchView.queryHint = "Tìm kiếm trong ${rs.count} bản ghi"

        }
        return super.onContextItemSelected(item)
    }
}