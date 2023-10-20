package com.example.appt10

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyMy(context: Context) :SQLiteOpenHelper(context,"MYNETM",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //tạo table, cột
        db?.execSQL("CREATE TABLE MYNE(_id integer primary key autoincrement,user TEXT,password TEXT,email TEXT,phone INT)")
        //thêm data vào database
        db?.execSQL("Insert into MYNE(user,password,email,phone) values ('mot','Abc123456','mot@gmail.com','012')")
        db?.execSQL("Insert into MYNE(user,password,email,phone) values ('hai','Abc123456','hai@gmail.com','565')")
        db?.execSQL("Insert into MYNE(user,password,email,phone) values ('haihai','Abc123456','haihai@gmail.com','545')")
        db?.execSQL("Insert into MYNE(user,password,email,phone) values ('mymy','Abc123456','mymy@gmail.com','564')")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}