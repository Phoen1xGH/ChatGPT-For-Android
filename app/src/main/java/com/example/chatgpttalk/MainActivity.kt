package com.example.chatgpttalk

import AdapterList
import GPTResponse
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.library.bubbleview.BubbleTextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

private var requestField : TextInputEditText? = null
private var sendButton : FloatingActionButton? = null
val listMessage : ArrayList<String> = arrayListOf()
private var messUser = ""
private var messGPT = ""
var view : View? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestField = findViewById(R.id.requestField)
        sendButton  = findViewById(R.id.sendButton)
        view = findViewById(R.id.mainView)
    }

    fun onClickSend(view: View?) {
        if (requestField?.text?.toString()?.trim().equals("")!!)
                Toast.makeText(this, "Введите запрос", Toast.LENGTH_LONG).show()
        else {
            val obj = DoAsync()
            messUser = requestField!!.text.toString()

            try {
                obj.execute(requestField!!.text.toString())
            }catch (ex : Exception){
                Toast.makeText(this, "Не удалось выполнить запрос", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun onClickUserMessage(view: View?) {
        val message : BubbleTextView = findViewById<RelativeLayout>(R.id.userLayout).findViewById(R.id.message_user)
        messageCopy(message)
    }

    fun onClickGPTMessage(view: View?){
        val message : BubbleTextView = findViewById<RelativeLayout>(R.id.gptLayout).findViewById(R.id.message_gpt)
        messageCopy(message)
    }

    private fun messageCopy(message : BubbleTextView){
        val clipboard = message.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("Order Number", message.text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(this, "Сообщение скопировано", Toast.LENGTH_SHORT).show()
    }

    class DoAsync : AsyncTask<String, String, String>() {
        @Deprecated("Deprecated in Java")
        @SuppressLint("SetTextI18n")
        override fun onPreExecute() {
            super.onPreExecute()
            requestField?.isEnabled = false
            sendButton?.isEnabled = false
            requestField?.setTextColor(Color.parseColor("#03DAC5"))
            requestField?.setText("Ожидайте...")
            displayLog(view, messUser)
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg message: String?) : String {
            val obj = GPTResponse()
            val response = obj.getResponse(message[0]!!)
            return response
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
                requestField?.isEnabled = true
                requestField?.setText("")
                requestField?.setTextColor(Color.parseColor("#CCCCCC"))
                sendButton?.isEnabled = true
                messGPT = result.toString()
                displayLog(view, messGPT)
        }

        private fun displayLog(v: View?, message : String) {
            val messageList = v!!.findViewById<ListView>(R.id.messagesLog)
            listMessage.add(message)
            val adapter = AdapterList(v!!.context, listMessage)
            messageList.adapter = adapter
        }
    }
}




