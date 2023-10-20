package com.example.appt10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    private lateinit var edUserName: EditText
    private lateinit var edPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login) // Đảm bảo layout cho LoginActivity đã được đặt.

        edUserName = findViewById(R.id.et_email) // Thay R.id.edUserName bằng ID thực tế của EditText tên người dùng trong layout.
        edPassword = findViewById(R.id.et_password) // Thay R.id.edPassword bằng ID thực tế của EditText mật khẩu trong layout.

        val btnLogin = findViewById<Button>(R.id.btn_signin) // Thay R.id.btnLogin bằng ID thực tế của nút "Đăng nhập" trong layout.

        btnLogin.setOnClickListener {
            val userName = edUserName.text.toString()
            val password = edPassword.text.toString()

            if (checkLogin(userName, password)) {
                // Đăng nhập thành công, chuyển đến MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Đóng LoginActivity để ngăn quay lại nó bằng nút Back
            } else {
                // Đăng nhập không thành công, hiển thị thông báo lỗi
                Toast.makeText(this, "Tên người dùng/Mật khẩu không đúng", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLogin(userName: String, password: String): Boolean {
        // Thực hiện kiểm tra đăng nhập ở đây
        // Trả về true nếu đăng nhập thành công, ngược lại trả về false
        // Bạn cần sửa phương thức này để xác thực thông tin đăng nhập
        // Ví dụ: Kiểm tra tên người dùng và mật khẩu với thông tin trong cơ sở dữ liệu
        if (userName == "tramy" && password == "123") {
            return true
        }
        return false
    }
}
