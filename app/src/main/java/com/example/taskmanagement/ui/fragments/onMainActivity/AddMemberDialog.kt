package com.example.taskmanagement.ui.fragments.onMainActivity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.taskmanagement.R

class AddMemberDialog(
    context: Context,
    var addMemberDialogListener: AddMemberToTaskDialogListener) :
    AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_member)
        window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
//        window?.decorView?.background?.colorFilter =
//            LightingColorFilter(-0x1000000, Color.TRANSPARENT)
//
//        tvSaveTodo.setOnClickListener {
//            val productName = add_product_name.text.toString()
//            val productPrice = add_product_price.text.toString().toFloat()
//            val productAmount = add_product_amount.text.toString()
////            val productAvailable = add_product_.text.toString()
//            val productUnit = add_product_unit.text.toString()
//
//
////            tvSaveTodo.setOnClickListener {
////                val todo = etInputTodo.text.toString()
//
////                if (todo.isEmpty()) {
////                    Toast.makeText(context, "Please Enter Your Todo!", Toast.LENGTH_SHORT).show()
////                    return@setOnClickListener
////                }
//
//            val item = Product(
//                0,
//                productName,
//                productPrice,
//                "fhg",
//                234f
//            )
//                addDialogListener.onAddButtonClicked(item)
//                dismiss()
//            }
//
//            tvCancel.setOnClickListener {
//                cancel()
//            }
//        tvCancel.setOnClickListener {
//            cancel()
//        }
    }
}