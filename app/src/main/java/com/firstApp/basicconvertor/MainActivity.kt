package com.example.basicconvertor

import DataCollector
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.firstApp.basicconvertor.R
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity(), AdapterView.OnItemSelectedListener{
    val convertor = DataCollector()



    override fun onCreate(savedInstanceState: Bundle?) {
        runBlocking {
            convertor.build()
        }
        val keys: Array<String> =convertor.rateCode?.keys?.map { it.toString() }?.toTypedArray() ?: arrayOf()
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,keys)
        //this is the dialog for selecting currency
        fun showMyDialog(text : TextView): View.OnClickListener { return View.OnClickListener {
            val dialog = Dialog(this@MainActivity)

            dialog.setContentView(R.layout.spinner_dialog)
            dialog.window?.setLayout(800,1200)

            dialog.show()
            val list : ListView = dialog.findViewById(R.id.list)
            val searchCur : EditText = dialog.findViewById(R.id.edit_text)

            list.adapter = adapter

            //search filterize method
            searchCur.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Code to execute before the text changes
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })

            list.setOnItemClickListener(object : AdapterView.OnItemClickListener{
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    text.setText(adapter.getItem(position))
                    dialog.dismiss()

                }
            })
        }
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.simplelayout)


        val fromS: TextView = findViewById(R.id.curFrom)
        val toS: TextView = findViewById(R.id.curTo)


        val amount: EditText = findViewById(R.id.editTextText)
        val result: TextView = findViewById(R.id.textView3)
        val conv: Button = findViewById(R.id.button)
        this.findViewById<ImageView>(R.id.x).setOnClickListener(){
            openWebPage("https://x.com/73azn")
        }
        this.findViewById<ImageView>(R.id.insta).setOnClickListener(){
            openWebPage("https://www.instagram.com/73azn/")
        }
        this.findViewById<ImageView>(R.id.github).setOnClickListener(){
            openWebPage("https://github.com/73azn")
        }
        fromS.setOnClickListener(showMyDialog(fromS))
        toS.setOnClickListener(showMyDialog(toS))
        conv.setOnClickListener() {

            amount.text
            result.setText(
                "${String.format("%.2f",convertor.convert(amount.text.toString().toDouble(),fromS.text.toString(),toS.text.toString()))} ${toS.text}")

        //under here are the spinner dialog





        }
    }


    private fun openWebPage(url: String) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(webIntent)
    }



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}

private fun ImageView.setOnClickListener() {

}

