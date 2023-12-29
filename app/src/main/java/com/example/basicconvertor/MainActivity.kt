package com.example.basicconvertor

import DataCollector
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.basicconvertor.ui.theme.BasicConvertorTheme
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity(), AdapterView.OnItemSelectedListener{
    val convertor = DataCollector()


    override fun onCreate(savedInstanceState: Bundle?) {
        runBlocking {
            convertor.build()
        }
        val keys : Array<String>  = convertor.rateCode?.keys?.map { it.toString() }?.toTypedArray()?: arrayOf()



        super.onCreate(savedInstanceState)
        setContentView(R.layout.simplelayout)
        val fromS : Spinner = findViewById(R.id.fromT)
        val toS : Spinner = findViewById(R.id.toT)

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,keys)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        fromS.adapter = adapter
        toS.adapter = adapter
        fromS.onItemSelectedListener = this
        toS.onItemSelectedListener = this

        val amount : EditText = findViewById(R.id.editTextText)
        val result : TextView = findViewById(R.id.textView3)
        val conv : Button = findViewById(R.id.button)
        conv.setOnClickListener(){
            fromS.selectedItem
            toS.selectedItem
            amount.text
            result.setText(String.format("%.3f",convertor.convert(amount.text.toString().toDouble(),fromS.selectedItem.toString(),toS.selectedItem.toString())))

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}

