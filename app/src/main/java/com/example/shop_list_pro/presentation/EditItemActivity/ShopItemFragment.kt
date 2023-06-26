package com.example.shop_list_pro.presentation.EditItemActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shop_list_pro.R
import com.example.shop_list_pro.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var title_name: TextInputLayout
    private lateinit var title_count: TextInputLayout
    private lateinit var et_name: EditText
    private lateinit var et_count: EditText
    private lateinit var save_button: Button
    private lateinit var add_button: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        } else {
            throw java.lang.RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmen_shop_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        title_name = view.findViewById(R.id.title_name)
        title_count = view.findViewById(R.id.title_count)
        et_name = view.findViewById(R.id.et_name)
        et_count = view.findViewById(R.id.et_count)
        save_button = view.findViewById(R.id.save_button)
        add_button = view.findViewById(R.id.add_button)
        addTextChangeListeners()
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }

            title_count.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            title_name.error = message
        }

        viewModel.finishScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun addTextChangeListeners() {
        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        et_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchAddMode() {
        save_button.setOnClickListener() {
            viewModel.addShopItem(et_name.text?.toString(), et_count.text?.toString())
        }
        add_button.setOnClickListener() {
            viewModel.addShopItemElement(et_name.text?.toString(), et_count.text?.toString())
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            et_name.text = null
            et_count.text = null
        }

    }

    private fun launchEditMode() {
        val saveButton = view?.findViewById<Button>(R.id.save_button)
        val saveButtonLayoutParams = saveButton?.layoutParams as ConstraintLayout.LayoutParams
        saveButtonLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        saveButton.layoutParams = saveButtonLayoutParams

        val addButton = view?.findViewById<Button>(R.id.add_button)
        addButton?.visibility = View.GONE

        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            et_name.setText(it.name.toString())
            et_count.setText(it.count.toString())
        }
        save_button.setOnClickListener() {
            viewModel.editShopItem(et_name.text?.toString(), et_count.text?.toString())

        }

    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Parametr screen mode is absent: $screenMode")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Parametr shop item idis absent")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newInstansceAddItem(): ShopItemFragment {
            val args = Bundle().apply {
                putString(EXTRA_SCREEN_MODE, MODE_ADD) }

            return ShopItemFragment().apply {
                arguments = args }
        }

        fun newInstansceEditItem(shopItemId: Int): ShopItemFragment {
            val args = Bundle().apply {
                putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
            }

            return ShopItemFragment().apply { arguments = args }
        }
    }
}