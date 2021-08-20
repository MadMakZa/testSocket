package com.maxim.testsocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var button: Button

    private var active = false
    private var data = ""

    private val address = "5.61.43.154"
    private val port = 444
    private val message = "getjson"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tvText)
        button = findViewById(R.id.button)
        setTextView()



        button.setOnClickListener {
            if (button.text == "connect") {
                button.text = "disconnect"
                active = true
                CoroutineScope(IO).launch {
                    openConnect(address, port)
                }

            } else {
                button.text = "connect"
                active = false
            }
        }
    }


    private fun openConnect(mAddress: String, mPort: Int) {
        val client = Socket(mAddress, mPort)
        val output = PrintWriter(client.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(client.inputStream))

        println("Client sending [$message]")
        output.println(message)
        println("Client receiving [${input.readLine()}]")
        data += input.readLine()
        client.close()
    }

    private fun setTextView(){
        textView.text = data
    }
}






















