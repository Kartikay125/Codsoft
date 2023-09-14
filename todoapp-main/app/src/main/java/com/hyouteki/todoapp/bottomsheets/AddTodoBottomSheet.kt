package com.hyouteki.todoapp.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyouteki.todoapp.comms.Comms
import com.hyouteki.todoapp.databinding.BottomSheetAddTodoBinding
import com.hyouteki.todoapp.models.Todo

class AddTodoBottomSheet(val todo: Todo? = null) : BottomSheetDialogFragment() {
    private lateinit var comms: Comms
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = BottomSheetAddTodoBinding.inflate(inflater, container, false)
        comms = activity as Comms
        if (todo != null) {
            binding.todo.setText(todo.title)
        }
        binding.add.setOnClickListener {
            if (binding.todo.text.toString().isNotEmpty()) {
                comms.insertTodoAPI(
                    when (todo) {
                        null -> Todo(
                            id = 0,
                            title = binding.todo.text.toString(),
                            done = false
                        )

                        else -> Todo(
                            id = todo.id,
                            title = binding.todo.text.toString(),
                            done = todo.done
                        )
                    }
                )
                dismiss()
            } else {
                comms.makeToast("Type in todo")
            }
        }
        return binding.root
    }
}