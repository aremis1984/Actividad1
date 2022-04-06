package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var outputTextView: TextView
    private var dotCLicked :Boolean=false
    lateinit var shareBtn: Button
    var numClicked: Boolean= false
    var opClicked: Boolean= false
    var finalResult: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        }
    }

    fun operator (view: View)
    {
        if (numClicked && !dotCLicked) {
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
        outputTextView.append((view as Button).text)
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
            }
        }
    }

    fun shareResult(view: View)
    {
        shareBtn = findViewById(R.id.compartir)
        val intent= Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,finalResult)
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent,"Share To:"))
    }
}