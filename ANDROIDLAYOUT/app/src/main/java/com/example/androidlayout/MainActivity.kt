package com.example.androidlayout

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val tipViewModel: TipViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etBiaya = findViewById<EditText>(R.id.etBiaya)
        val rgTip = findViewById<RadioGroup>(R.id.rgTip)
        val switchBulatkan = findViewById<Switch>(R.id.switchBulatkan)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)

        tipViewModel.biayaInput.observe(this) {
            if (etBiaya.text.toString() != it) etBiaya.setText(it)
        }

        tipViewModel.bulatkan.observe(this) {
            switchBulatkan.isChecked = it
        }

        tipViewModel.tipResult.observe(this) {
            tvHasil.text = it
        }

        btnHitung.setOnClickListener {
            val biayaInput = etBiaya.text.toString()

            if (biayaInput.isEmpty()) {
                Toast.makeText(this, "Masukkan biaya layanan terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val biaya = biayaInput.toDoubleOrNull()
            if (biaya == null || biaya <= 0) {
                Toast.makeText(this, "Biaya layanan harus lebih dari 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val persenTip = when (rgTip.checkedRadioButtonId) {
                R.id.rb15 -> 0.15
                R.id.rb18 -> 0.18
                R.id.rb20 -> 0.20
                else -> {
                    Toast.makeText(this, "Pilih persentase tip", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            tipViewModel.setBiaya(biayaInput)
            tipViewModel.setBulatkan(switchBulatkan.isChecked)
            tipViewModel.setPersenTip(persenTip)
            tipViewModel.hitungTip()
        }
    }
}