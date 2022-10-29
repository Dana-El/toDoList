package com.example.todolist

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.Log


//append function modified from function in https://www.techiedelight.com/add-new-element-array-kotlin/
fun append(arr: Array<String>, element: String): Array<String> {
    val list: MutableList<String> = arr.toMutableList()
    list.add(element)
    return list.toTypedArray()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        val spinnerVal: Spinner = findViewById(R.id.mainSpinner)
        val spinnerVal2: Spinner = findViewById(R.id.Spinnersecond)
        var options = arrayOf("All Categories")
        var options2 = arrayOf("General")
        val etCategory :EditText = findViewById(R.id.inputCat)
        val etTask :EditText = findViewById(R.id.inputTask)
        val button: FloatingActionButton = findViewById(R.id.btaddCat)
        val button2: FloatingActionButton = findViewById(R.id.btaddCat2)
        var checkBoxes = mapOf<String,  MutableList<CheckBox> >()
        var tasks = mutableListOf<String>()
        val layout = findViewById<LinearLayout>(R.id.linearScroll)

        var flag:String = "All Categories"
        var flag2:String = "General"
        spinnerVal.adapter =
            ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)
        spinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                flag = options[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        spinnerVal2.adapter =
            ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options2)
        spinnerVal2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                flag2 = options2[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        button.setOnClickListener { view->
            //if add button is clicked, append the new value taken by
            //etCategory to the options array, then update spinner
            var x :String = etCategory.text.toString()
            options = append(options, x)
            options2 = append(options2, x)
            spinnerVal.adapter =
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)
            spinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    flag = options[p2]
                    if(spinnerVal.selectedItem.toString()== "All Categories"){
                        layout.removeAllViews();
                        for (k in checkBoxes.keys){
                            for(i in checkBoxes[k]!!){
                                layout.addView(i)
                            }
                        }

                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
            spinnerVal2.adapter =
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options2)
            spinnerVal2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    flag2 = options2[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
            etCategory.text = null

        }

        button2.setOnClickListener { view->
            //if second add button is clicked, add a checkbox with the category and task

            val checkBox = CheckBox(this)
            checkBox.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100)
            var task :String = etTask.text.toString()

            var category :String = spinnerVal2.selectedItem.toString()

            checkBox.text =category+ ": "+task
            //add checkboxes to a map to later categorize them
            tasks.plus(category + ": " + task)
            checkBoxes[category]?.plus(checkBox)
            var chosen : String = spinnerVal.selectedItem.toString()
            if(chosen == category || chosen =="All Categories")
                layout.addView(checkBox)


            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    layout.removeView(checkBox)
                    tasks.remove(checkBox.text)
                    if(chosen !="All Categories")
                        checkBoxes[chosen]?.remove(checkBox)
                    else{
                        var temp = ""
                        for(i in checkBox.text){
                            if (i== ':')
                                break
                            else
                                temp = temp+ i
                        }
                        checkBoxes[temp]?.remove(checkBox)

                    }
                }
            }
            etTask.text = null

        }
        spinnerVal.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if(spinnerVal.selectedItem.toString()== "All Categories"){
                        layout.removeAllViews();
                        for ((k,v) in checkBoxes){
                            for(i in checkBoxes[k]!!){
                                layout.addView(i)
                            }
                        }

                    }
                    else{
                        layout.removeAllViews();
                        for(i in checkBoxes[spinnerVal.selectedItem.toString()]!!){
                            layout.addView(i)
                        }


                    }


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }





    }

}