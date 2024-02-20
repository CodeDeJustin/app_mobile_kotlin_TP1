package com.example.tp1_m14_justin_allard

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat
import com.example.tp1_m14_justin_allard.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    // DECLARATION DES VARIABLES
    private lateinit var birthdateButton: Button
    private lateinit var clearButton: Button
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etNumberOfCats: EditText
    private lateinit var etNumberOfDogs: EditText
    private lateinit var submitButton: Button
    private var age: Int = 0

    // METHODE ONCREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialiserVues()
        configurationBoutons()
        boutonClearEffacer()
    }

    // INITIALISATION ET CONFIGURATION
    private fun initialiserVues() {
        birthdateButton = findViewById(R.id.bBirthDate)
        clearButton = findViewById(R.id.bClear)
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etNumberOfCats = findViewById(R.id.etNumberOfCats)
        etNumberOfDogs = findViewById(R.id.etNumberOfDogs)
        submitButton = findViewById(R.id.bSubmit)
    }

    private fun configurationBoutons() {
        birthdateButton.setOnClickListener {
            showDatePicker()
        }
        clearButton.setOnClickListener {
            videChamps()
            birthdateButton.text = getString(R.string.bBirthDate)
        }
        submitButton.setOnClickListener {
            ouvrirSecondeActivite()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                boutonClearEffacer()
            }
            override fun afterTextChanged(s: Editable) {}
        }

        etFirstName.addTextChangedListener(textWatcher)
        etLastName.addTextChangedListener(textWatcher)
        etNumberOfCats.addTextChangedListener(textWatcher)
        etNumberOfDogs.addTextChangedListener(textWatcher)
    }

    // VIDER LES CHAMPS
    private fun videChamps() {
        etFirstName.text.clear()
        etLastName.text.clear()
        etNumberOfCats.text.clear()
        etNumberOfDogs.text.clear()
    }

    // BOUTON CLEAR POUR LES TEXTVIEWS
    private fun boutonClearEffacer() {
        val siVide = etFirstName.text.isEmpty() && etLastName.text.isEmpty() &&
                etNumberOfCats.text.isEmpty() && etNumberOfDogs.text.isEmpty()
        clearButton.isEnabled = !siVide
    }

    // DATE PICKER
    private fun showDatePicker() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val dateSelectionnee = Calendar.getInstance()
            dateSelectionnee.set(selectedYear, selectedMonth, selectedDay)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            birthdateButton.text = dateFormat.format(dateSelectionnee.time)

            calculerAge(dateSelectionnee) // Appeler la fonction calculerAge() avec la date de naissance selectionnee
        }, year, month, day)

        datePicker.datePicker.maxDate = currentDate.timeInMillis
        datePicker.show()
    }

    // CALCULER AGE
    private fun calculerAge(birthDate: Calendar) {
        val dateCourante = Calendar.getInstance()
        val age = dateCourante.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)

        this.age = if (birthDate.get(Calendar.DAY_OF_YEAR) > dateCourante.get(Calendar.DAY_OF_YEAR)) {
            age - 1
        } else {
            age
        }
    }

    // OUVRIR SECONDE ACTIVITE
    private fun ouvrirSecondeActivite() {
        if (etFirstName.text.isBlank() || etLastName.text.isBlank() || etNumberOfCats.text.isBlank() || etNumberOfDogs.text.isBlank() || birthdateButton.text == getString(R.string.bBirthDate)) {
            // AFFICHAGE DE L'ALERT DIALOG SI LES CHAMPS NE SONT PAS REMPLIS CORRECTEMENT
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.alert_dialog_title)
            builder.setMessage(R.string.alert_dialog_message)
            builder.setPositiveButton(R.string.ok) { _, _ -> }
            builder.show()
        } else {
            val intent = Intent(this, SecondActivity::class.java)
            val fullName = etFirstName.text.toString() + " " + etLastName.text.toString()
            val numberOfCats = etNumberOfCats.text.toString().toIntOrNull() ?: 0
            val numberOfDogs = etNumberOfDogs.text.toString().toIntOrNull() ?: 0
            intent.putExtra("fullName", fullName)
            intent.putExtra("age", age)
            intent.putExtra("numberOfCats", numberOfCats)
            intent.putExtra("numberOfDogs", numberOfDogs)
            startActivity(intent)
        }
    }
}

