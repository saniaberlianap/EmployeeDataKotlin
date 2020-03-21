package com.example.quizsqlite01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quizsqlite01.`object`.EmpModelUser
import com.example.quizsqlite01.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.sql.Types.NULL

class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)



    }

    fun register(view: View) {
        val regisUsername = rUsername!!.text.toString()
        val regisEmail = rUserEmail!!.text.toString()
        val regisPassword = rUserpassword!!.text.toString()
        val regisConfirmPassword = rConfirmUserpassword!!.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

//        Check field harus terisi semua
        if(regisUsername != "" && regisEmail != "" && regisPassword != "" && regisConfirmPassword != "" ){

//            check email user satu dengan user lainnya tidak boleh sama
            if (!databaseHandler!!.checkEmail(regisEmail.trim())){

//             check password dan confirm password sudah sam aatau belum
                if (regisPassword == regisConfirmPassword){
                    var regis = EmpModelUser(IdUser = NULL,
                        username = regisUsername.trim(),
                        email  = regisEmail.trim(),
                        userpassword = regisPassword.trim())

                    databaseHandler!!.addUserData(regis)

//
                    Toast.makeText(applicationContext,"Done $regisUsername, your registration succed!", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(applicationContext,"Passwords aren't the same!", Toast.LENGTH_LONG).show()
                }
            } else{
                Toast.makeText(applicationContext,"This email : '$regisEmail' already used", Toast.LENGTH_LONG).show()
            }
        } else{
            Toast.makeText(applicationContext,"All field still empty, please fill the field", Toast.LENGTH_LONG).show()
        }
    }
}




