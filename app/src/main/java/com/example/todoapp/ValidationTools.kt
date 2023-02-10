package com.example.todoapp

class FieldsAreEmptyException: Exception() {
    lateinit var missingFields: List<String>
}

class InvalidEmailException: Exception()

class ShortPasswordException: Exception()

class PasswordsMismatchException: Exception()

class ValidationTools {

    fun validateEmail(email: String) : Boolean {
        if ((email.length < 7) or !email.contains('@')) {
            throw InvalidEmailException()
        }
        return true
    }

    fun validatePassword(password: String) : Boolean {
        if ((password.length < 8)) {
            throw ShortPasswordException()
        }
        return true
    }

    fun validatePasswordConfirmation(password: String, passwordConfirm: String) : Boolean {
        if (password != passwordConfirm) {
            throw PasswordsMismatchException()
        }
        return true
    }

    fun validateRegisterFields(fullName: String, email: String, password: String, confirmPassword: String) : Boolean {
        var missingFields: MutableList<String> = mutableListOf()

        if (fullName.isEmpty()) {
            missingFields.add("fullName")
        }
        if (email.isEmpty()) {
            missingFields.add("email")
        }
        if (password.isEmpty()) {
            missingFields.add("password")
        }
        if (confirmPassword.isEmpty()) {
            missingFields.add("confirmPassword")
        }

        if (missingFields.isNotEmpty()) {
            val fieldsAreEmptyException = FieldsAreEmptyException()
            fieldsAreEmptyException.missingFields = missingFields

            throw fieldsAreEmptyException
        }

        return true
    }
}