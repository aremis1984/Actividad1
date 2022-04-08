package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var outputTextView: TextView
    private var dotCLicked :Boolean=false
    lateinit var shareBtn: Button
    private var numClicked: Boolean= false
    private var opClicked: Boolean= false
    private var hasError: Boolean= false
    private var finalResult: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle :Bundle ?=intent.extras
        if (bundle !== null){
            finalResult = bundle.getString("final_result").toString()
            outputTextView = findViewById(R.id.resultado)
            outputTextView.text = finalResult
            shareBtn = findViewById(R.id.compartir)
            shareBtn.isEnabled = true
        }
        shareBtn = findViewById(R.id.compartir)
        shareBtn.setOnClickListener {
            val intent = Intent(this, ShareActivity::class.java)
            intent.putExtra("final_result", finalResult)
            startActivity(intent)
        }
    }

    fun delete(view: View)
    {
        outputTextView = findViewById(R.id.resultado)
        if(outputTextView.text !== "") {
            outputTextView.text = ""
            numClicked = false
            opClicked = false
            dotCLicked = false
            shareBtn = findViewById(R.id.compartir)
            shareBtn.isEnabled = false
        }
    }

    fun deleteLast (view: View) {
        outputTextView = findViewById(R.id.resultado)
        var textChain = outputTextView.text.toString()
        if (textChain.isNotEmpty()) {
            textChain = textChain.substring(0, textChain.length - 1)
            outputTextView.text = textChain
            shareBtn = findViewById(R.id.compartir)
            shareBtn.isEnabled = false
            opClicked = false
        }
    }

    fun operator (view: View)
    {
        var textChain = outputTextView.text.toString()
        if (!dotCLicked && (numClicked || !opClicked && textChain.isNotEmpty())) {
            outputTextView = findViewById(R.id.resultado)
            outputTextView.append((view as Button).text)
            numClicked = false
            opClicked = true
            shareBtn = findViewById(R.id.compartir)
            shareBtn.isEnabled = false
        }
    }

    fun decimal(view: View)
    {
        if(numClicked && !dotCLicked && !opClicked) {
            outputTextView = findViewById(R.id.resultado)
            outputTextView.append(".")
            numClicked = false
            opClicked = false
            dotCLicked = true
        }
    }

    fun number(view: View)
    {
        outputTextView = findViewById(R.id.resultado)
        if (hasError) outputTextView.text = (view as Button).text else outputTextView.append((view as Button).text)
        numClicked = true
        dotCLicked = false
        opClicked = false
        shareBtn = findViewById(R.id.compartir)
        shareBtn.isEnabled = false
    }

    fun calResult(view: View)
    {
        if (numClicked && !dotCLicked && !opClicked) {
            val textChain = outputTextView.text.toString()
            val expression= ExpressionBuilder(textChain).build()
            try {
                val result = expression.evaluate()
                outputTextView.text = result.toString()
                finalResult = result.toString()
                numClicked = true
                opClicked = false
                dotCLicked = false
                shareBtn = findViewById(R.id.compartir)
                shareBtn.isEnabled = true
            }catch (ex:Exception) {
                outputTextView.text="Error"
                numClicked = false
                opClicked = false
                dotCLicked = false
                hasError = true
            }
        }
    }
}