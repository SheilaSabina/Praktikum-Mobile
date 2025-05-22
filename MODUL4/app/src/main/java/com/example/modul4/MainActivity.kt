package com.example.modul4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val listFragment = ListFragment()
        val fragment = fragmentManager.findFragmentByTag(ListFragment::class.java.simpleName)

        if (fragment !is ListFragment) {
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, listFragment, ListFragment::class.java.simpleName)
                .commit()
        }
    }
}