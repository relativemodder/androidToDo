package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.ActivityRegistrationBinding
import com.example.todoapp.utils.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    private fun validateAllFields() : Boolean {
        var fullNameError: String? = null
        var emailError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null

        var areFieldsValid = true

        val fullName = binding.fullNameEdit.text.toString()
        val email = binding.emailEdit.text.toString()
        val password = binding.passwordEdit.text.toString()
        val confirm = binding.confirmPasswordEdit.text.toString()

        val validationTools = ValidationTools()

        try {
            validationTools.validateRegisterFields(fullName, email, password, confirm)
        }
        catch (e: FieldsAreEmptyException) {
            if (e.missingFields.contains("fullName")) fullNameError = getString(R.string.missing_value)
            if (e.missingFields.contains("email")) emailError = getString(R.string.missing_value)
            if (e.missingFields.contains("password")) passwordError = getString(R.string.missing_value)
            if (e.missingFields.contains("confirmPassword")) confirmPasswordError = getString(R.string.missing_value)

            areFieldsValid = false
        }

        try {
            validationTools.validateEmail(email)
        }
        catch (e: InvalidEmailException) {
            emailError = getString(R.string.invalid_email)
            areFieldsValid = false
        }

        try {
            validationTools.validatePassword(password)
        }
        catch (e: ShortPasswordException) {
            passwordError = getString(R.string.password_is_short)
            areFieldsValid = false
        }

        try {
            validationTools.validatePasswordConfirmation(password, confirm)
        }
        catch (e: PasswordsMismatchException) {
            confirmPasswordError = getString(R.string.password_mismatch)
            areFieldsValid = false
        }

        binding.fullNameEdit.error = fullNameError
        binding.emailEdit.error = emailError
        binding.passwordEdit.error = passwordError
        binding.confirmPasswordEdit.error = confirmPasswordError

        return areFieldsValid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener { _ ->
            if (validateAllFields()) {
                Toast.makeText(applicationContext, getString(R.string.success), Toast.LENGTH_SHORT).show()
            }
        }

        binding.signInBtn.setOnClickListener { _ ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}