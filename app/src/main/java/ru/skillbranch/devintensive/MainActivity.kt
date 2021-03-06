package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Display
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        Log.d("M_MainActivity", "onCreate $status $question")

        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)

        textTxt.text = benderObj.askQuestion()

        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener(this)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
        Log.d("M_MainActivity", "${benderObj.status.name} ${benderObj.question.name}")
    }

    private fun sendMessage() {
        if (messageValidation() == "") {
            val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase())
            messageEt.setText("")
            val (r, g, b) = color
            benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
            textTxt.text = phrase
        } else {
            textTxt.text = messageValidation()
        }
        hideKeyboard()
    }

    private fun messageValidation(): String {
        val mText = messageEt.text.toString()
        return if (mText.isEmpty()) {
            mText
        } else {
            when (benderObj.question) {
                Bender.Question.NAME -> if (mText.capitalize() == mText) "" else "Имя должно начинаться с заглавной буквы"
                Bender.Question.PROFESSION -> if (mText.decapitalize() == mText) "" else "Профессия должна начинаться со строчной буквы"
                Bender.Question.MATERIAL -> if (Regex("""\d+""").containsMatchIn(mText)) "Материаль не должен содержать цифр" else ""
                Bender.Question.BDAY -> if (Regex("""\d+""").matchEntire(mText)?.value == null) "Год моего рождения должен содержать только цифры" else ""
                Bender.Question.SERIAL -> if (mText.length != 7 || Regex("""\d+""").matchEntire(
                        mText
                    )?.value == null
                ) "Серийный номер содержит только цифры, и их 7" else ""
                Bender.Question.IDLE -> ""
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            sendMessage()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            sendMessage()
            return true
        }
        return false
    }

}
