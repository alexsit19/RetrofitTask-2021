package com.example.retrofittask_2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofittask_2021.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.place_for_fragment, CatListFragment(), "catlistfragment")
            .commit()

    }
}