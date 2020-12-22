package ru.hse.dormitoryproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = resources.getString(R.string.app_name)
        toolbar.isClickable = true

        toolbar.setOnMenuItemClickListener {
    when (it.itemId) {
        R.id.go_to_search -> {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)

        }
        R.id.action_cancel -> {
            val prevFragment = supportFragmentManager.findFragmentByTag(resources.getString(R.string.tag_search_fragment))
            if (prevFragment != null)
                supportFragmentManager.beginTransaction().remove(prevFragment).commit()
        }
        else -> Toast.makeText(this@MainActivity, "No activity!", Toast.LENGTH_SHORT).show()
        }
    true
    }
    }
}