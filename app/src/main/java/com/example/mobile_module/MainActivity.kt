package com.example.mobile_module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.*
import com.example.mobile_module.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val popupMenuButton = findViewById<Button>(R.id.popupMenuButton)
        val popupMenu = PopupMenu(this, popupMenuButton)
        popupMenu.inflate(R.menu.menu)
        popupMenuButton.setOnClickListener {
            popupMenu.setOnMenuItemClickListener {
                when (it.title)
                {
                    "Работа с переменными" -> {addVariableBlock()
                    true}
                    else -> {
                        Toast.makeText(this, it.itemId, Toast.LENGTH_SHORT).show()
                    true}
                }
            }
            popupMenu.show()
        }
    }
    fun addVariableBlock()
    {
        val maincontainer = findViewById<LinearLayout>(R.id.blocksContainer)
        val block = layoutInflater.inflate(R.layout.activity_variables_block,maincontainer,true)
    }
}