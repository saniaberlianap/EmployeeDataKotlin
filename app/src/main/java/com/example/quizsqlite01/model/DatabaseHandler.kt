package com.example.quizsqlite01.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.quizsqlite01.`object`.EmpModelClass
import com.example.quizsqlite01.`object`.EmpModelUser

class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {

        private val DATABASE_VERSION = 1

//        nama database
        private val DATABASE_NAME = "EmployeeDatabaseKuis"

//        table beserta atributnya untuk employee
        private val TABLE_CONTACTS = "EmployeeTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
        private val KEY_NOHP = "nohp"

//        table beserta atributnya untuk login dan register
        private val TABLE_USERS = "UserTable"
        private val USER_ID = "userid"
        private val USER_NAME = "username"
        private val USER_EMAIL = "useremail"
        private val USER_PASSWORD = "userpassword"
    }



    override fun onCreate(db: SQLiteDatabase?) {

//        membuat tabel beserta definisi kolomnya (employee)
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_NOHP + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)


        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_NAME + " TEXT,"
                + USER_EMAIL + " TEXT,"
                + USER_PASSWORD + " TEXT"
                + ");")
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }

//    fungsi menambahkan user di table user ( untuk register dan login )
    fun addUserData(emp: EmpModelUser):Long{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, emp.username)
        contentValues.put(USER_EMAIL, emp.email)
        contentValues.put(USER_PASSWORD ,emp.userpassword )

//    menambahkan data ke tabel
        val success = db.insert(TABLE_USERS, null, contentValues)
//    menutup database
        db.close()

    return success
}

//    check user tersebut exist atau tidak
    fun userPresent(email: String, password: String): Boolean{

        val db = this.readableDatabase
        val columns = arrayOf(USER_NAME)
        val selection = "$USER_EMAIL = ? AND $USER_PASSWORD =?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(TABLE_USERS,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false
    }

//    check apa email user satu sama dengan user yang lainnya
    fun checkEmail(email: String): Boolean{

        val db = this.readableDatabase
        val columns = arrayOf(USER_ID)
        val selection = "$USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(TABLE_USERS,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }


    // fungsi untuk menambahkan data karyawan
    fun addEmployee(emp: EmpModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName)
        contentValues.put(KEY_EMAIL,emp.userEmail )
        contentValues.put(KEY_NOHP,emp.userNohp)
        // menambahkan data pada tabel
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }



    // fungsi untuk menampilkan data dari tabel ke UI
    fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        var userNohp: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                userNohp = cursor.getString(cursor.getColumnIndex("nohp"))
                val emp= EmpModelClass(userId = userId, userName = userName, userEmail = userEmail, userNohp = userNohp)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    // fungsi untuk memperbarui data karyawan
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName)
        contentValues.put(KEY_EMAIL,emp.userEmail )
        contentValues.put(KEY_NOHP,emp.userNohp )

        // memperbarui data
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)

        // menutup koneksi ke database
        db.close()
        return success
    }
    // fungsi untuk menghapus data
    fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        // employee id dari data yang akan dihapus
        contentValues.put(KEY_ID, emp.userId)
        // query untuk menghapus ata
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)

        // menutup koneksi ke database
        db.close()
        return success
    }
}