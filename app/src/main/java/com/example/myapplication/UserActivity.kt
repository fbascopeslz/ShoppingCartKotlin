package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init() {
        binding.btnRegister.setOnClickListener {
            if(checkAllFields()) {
                val intent = Intent(this, ProductActivity::class.java)
                intent.putExtra("userName", binding.etName.text.toString())
                this.startActivity(intent)
            }
        }
    }

    fun checkAllFields(): Boolean {
        var ok: Boolean = true

        if (binding.etName.length() == 0) {
            binding.etName.setError("El nombre es obligatorio!!!")
            ok = false
        }
        if (binding.etEmail.length() == 0) {
            binding.etEmail.setError("El email es obligatorio!!!")
            ok = false
        }
        if (binding.etPassword.length() == 0) {
            binding.etPassword.setError("La contraseña es obligatoria!!!")
            ok = false
        }
        if(binding.etPassword.length() < 8) {
            binding.etPassword.setError("La contraseña debe de tener un minimo de 8 caracteres!!!")
            ok = false
        }
        if (binding.etPassword.text.toString() != binding.etRepassword.text.toString()) {
            binding.etRepassword.setError("Las contraseña no coinciden!!!")
            ok = false
        }

        return ok
    }
}
