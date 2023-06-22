package com.example.shop_list_pro.presentation.EditItemActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.shop_list_pro.R
import com.example.shop_list_pro.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

//    private lateinit var viewModel: ShopItemViewModel
//    private lateinit var title_name: TextInputLayout
//    private lateinit var title_count: TextInputLayout
//    private lateinit var et_name: EditText
//    private lateinit var et_count: EditText
//    private lateinit var save_button: Button
//    private lateinit var add_button: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        title_name = findViewById(R.id.title_name)
//        title_count = findViewById(R.id.title_count)
//        et_name = findViewById(R.id.et_name)
//        et_count = findViewById(R.id.et_count)
//        save_button = findViewById(R.id.save_button)
//        add_button = findViewById(R.id.add_button)
//        addTextChangeListeners()
//        when(screenMode){
//            MODE_EDIT -> launchEditMode()
//            MODE_ADD -> launchAddMode()
//        }
//        observeViewModel()


        val fragment =  when(screenMode){
            MODE_EDIT -> ShopItemFragment.newInstansceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstansceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")

        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }

//    private fun observeViewModel() {
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//
//            title_count.error = message
//        }
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//            title_name.error = message
//        }
//
//        viewModel.finishScreen.observe(this) {
//            finish()
//        }
//    }
//
//    private fun addTextChangeListeners() {
//        et_name.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//        et_count.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputCount()
//            }
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//    }

//    private fun launchAddMode() {
//        save_button.setOnClickListener(){
//            viewModel.addShopItem(et_name.text?.toString(), et_count.text?.toString())
//        }
//        add_button.setOnClickListener(){
//            viewModel.addShopItemElement(et_name.text?.toString(), et_count.text?.toString())
//            et_name.text = null
//            et_count.text = null
//        }
//
//    }
//
//    private fun launchEditMode() {
//        val saveButton = findViewById<Button>(R.id.save_button)
//        val saveButtonLayoutParams = saveButton.layoutParams as ConstraintLayout.LayoutParams
//        saveButtonLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
//        saveButton.layoutParams = saveButtonLayoutParams
//
//        val addButton = findViewById<Button>(R.id.add_button)
//        addButton.visibility = View.GONE
//
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItem.observe(this){
//            et_name.setText(it.name.toString())
//            et_count.setText(it.count.toString())
//        }
//        save_button.setOnClickListener(){
//            viewModel.editShopItem(et_name.text?.toString(), et_count.text?.toString())
//
//        }
//
//    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Parametr screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Parametr shop item idis absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }


        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}