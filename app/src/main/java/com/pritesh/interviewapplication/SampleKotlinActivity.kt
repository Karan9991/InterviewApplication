package com.pritesh.interviewapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SampleKotlinActivity : AppCompatActivity() {

    private lateinit var edtFirstName: EditText
    private lateinit var btnSubmit :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_kotlin)
        //edtFirstName = findViewById(R.id.edtFirstName) as EditText
        //btnSubmit = findViewById(R.id.btnSubmit) as Button

        edtFirstName.text =  Editable.Factory.getInstance().newEditable("Pritesh Patel")
        btnSubmit.setOnClickListener(View.OnClickListener { Toast.makeText(applicationContext, edtFirstName.text,Toast.LENGTH_SHORT).show() })
    }
}
