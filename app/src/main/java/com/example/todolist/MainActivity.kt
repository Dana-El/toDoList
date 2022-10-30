package com.example.todolist

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.Log
import android.view.KeyEvent


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
        //the following spinner is used to choose a certain category to display the tasks of
        val spinnerVal: Spinner = findViewById(R.id.mainSpinner)
        // the following spinner is used to choose a category for the task you are choosing
        val spinnerVal2: Spinner = findViewById(R.id.Spinnersecond)
        //the following array is for the options of spinnerVal
        var options = arrayOf("All Categories", "General")
        //the following array is for the options of spinnerVal2
        var options2 = arrayOf("General")
        //the following text adds a category if the enter key or the add button are pressed
        val etCategory :EditText = findViewById(R.id.inputCat)
        //the following text adds a task of a certain category chosen by spinnerVal2 by pressing the enter button or the add button
        val etTask :EditText = findViewById(R.id.inputTask)
        //the following two buttons are the add buttons used to add tasks or categories
        val button: FloatingActionButton = findViewById(R.id.btaddCat)
        val button2: FloatingActionButton = findViewById(R.id.btaddCat2)
        var checkBoxes = mutableMapOf<String,  MutableList<CheckBox> >()
        var tasks = mutableListOf<String>()
        //the following is a scroll linear layout so that you can view multiple tasks
        val layout = findViewById<LinearLayout>(R.id.linearScroll)
        var flag:String = "All Categories"
        var flag2:String = "General"
        //the following is the basic code for spinnerVal
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
        //the following is the basic code for spinnerVal2
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
            //the following code also shows certain tasks when each category is chosen
            var x :String = etCategory.text.toString()
            options = append(options, x)
            options2 = append(options2, x)
            spinnerVal.adapter =
                ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)
            spinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    flag = options[p2]
                    if(spinnerVal.selectedItem.toString()== "All Categories"){
                        //show all tasks if All Categories is chosen
                        layout.removeAllViews();
                        for (k in checkBoxes.keys){
                            for(i in checkBoxes[k]!!){
                                layout.addView(i)
                            }
                        }

                    }
                    else{
                        //show tasks of chosen categories
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
            //update spinnerVal2
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

        etCategory.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            //if enter key is pressed when adding a category, add the category
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
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
                                println(k)
                                println("*****************************")
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
                return@OnKeyListener true
            }
            false
        })
        etTask.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            //if enter key is pressed when entering a task add the task with the current chosen category
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val checkBox = CheckBox(this)
                checkBox.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100)
                var task :String = etTask.text.toString()

                var category :String = spinnerVal2.selectedItem.toString()

                checkBox.text =category+ ": "+task
                //add checkboxes to a map to later categorize them
                if(checkBoxes["General"]==null){
                    checkBoxes["General"]= mutableListOf()
                }
                tasks.add(category + ": " + task)
                if (checkBoxes[category] == null){
                    checkBoxes[category] = mutableListOf()

                }
                checkBoxes[category]?.add(checkBox)

                println(checkBox)
                println(checkBoxes[category])

                var chosen : String = spinnerVal.selectedItem.toString()
                if(chosen == category || chosen =="All Categories")
                    layout.addView(checkBox)


                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    //if task is checked, remove it from view
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
                return@OnKeyListener true
            }
            false
        })
        button2.setOnClickListener { view->
            //if second add button is clicked, add a checkbox with the category and task
            val checkBox = CheckBox(this)
            checkBox.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100)
            var task :String = etTask.text.toString()

            var category :String = spinnerVal2.selectedItem.toString()

            checkBox.text =category+ ": "+task
            //add checkboxes to a map to later categorize them
            if(checkBoxes["General"]==null){
                checkBoxes["General"]= mutableListOf()
            }
            tasks.add(category + ": " + task)
            if (checkBoxes[category] == null){
                checkBoxes[category] = mutableListOf()

            }
            checkBoxes[category]?.add(checkBox)

            println(checkBox)
            println(checkBoxes[category])

            var chosen : String = spinnerVal.selectedItem.toString()
            if(chosen == category || chosen =="All Categories")
                layout.addView(checkBox)


            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                //if task is checked, remove it from view
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


    }


}