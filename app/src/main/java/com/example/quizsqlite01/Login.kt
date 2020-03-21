package com.example.quizsqlite01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quizsqlite01.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        keRegistrasi.setOnClickListener{
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

    }

    fun login(view: View) {
        val lgEmail = login_email!!.text.toString()
        val lgPassword = login_password!!.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

//        Check field terisi semua atau tidak
        if (lgEmail.trim() != "" && lgPassword.trim() != ""){

//            Check email ada tidak di databse table users
            if (databaseHandler!!.checkEmail(lgEmail.trim { it <= ' ' })) {

//                Check password sudah sama atau belum dengan email
                if (databaseHandler!!.userPresent(lgEmail.trim { it <= ' ' }, lgPassword.trim { it <= ' ' })) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("EMAIL", lgEmail.trim { it <= ' ' })
                    login_email.setText(null)
                    login_password.setText(null)
                    startActivity(intent)

                } else {
                    Toast.makeText(applicationContext, "Invalid Password", Toast.LENGTH_LONG).show()
                }

            } else{
                Toast.makeText(applicationContext, "Invalid Email Address", Toast.LENGTH_LONG).show()
            }

        } else{
            Toast.makeText(applicationContext,"Your email and password still empty", Toast.LENGTH_LONG).show()
        }
    }
}
