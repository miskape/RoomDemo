package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.myapplication.database.UserDatabase
import com.example.myapplication.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Create an instance of the database
        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, "user-database"
        ).build()

        val user = User(1, "Miska", "Peltoniemi")

        // Using Kotlin Coroutines, async is yikes and don't look nice
        // Clear user
        lifecycleScope.launch {
            println("Clear")
            db.userDao().delete(user)
        }
        // Save user
        lifecycleScope.launch {
            println("Save")
            db.userDao().insertAll(user)
        }
        // Get users
        lifecycleScope.launch {
            println("Get")
            val getUsers = db.userDao().getAll()
            println(getUsers)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}