package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ShareActivity : AppCompatActivity() {
    private var result: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        val bundle :Bundle ?=intent.extras
        if (bundle !== null){
            result = bundle.getString("final_result").toString()
            val resultView = findViewById<TextView>(R.id.result_to_share)
            resultView.text = result
        }
    }

    fun shareResult(view: View)
    {
        val intent= Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, result)
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent,"Share To:"))
    }

    fun back(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("final_result", result)
        startActivity(intent)
    }
}