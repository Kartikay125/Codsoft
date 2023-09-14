package com.hyouteki.todoapp.activities

import android.os.Bundle
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.hyouteki.todoapp.R
import com.hyouteki.todoapp.bottomsheets.AddTodoBottomSheet
import com.hyouteki.todoapp.bottomsheets.ModalBottomSheet
import com.hyouteki.todoapp.comms.Comms
import com.hyouteki.todoapp.databinding.ActivityMainBinding
import com.hyouteki.todoapp.databinding.TodoListItemBinding
import com.hyouteki.todoapp.models.Todo
import com.hyouteki.todoapp.vms.ViewModel

class MainActivity : AppCompatActivity(), Comms {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComps()
        handleTouchActions()
    }

    private fun initComps() {
        adapter = Adapter()
        viewModel.allTodos.observeForever(Observer {
            it?.let { adapter.update(it) }
        })
        binding.recyclerView.adapter = adapter
    }

    private fun handleTouchActions() {
        binding.add.setOnClickListener {
            AddTodoBottomSheet().show(supportFragmentManager, "launching add todo bottom sheet")
        }
    }

    inner class Adapter() : RecyclerView.Adapter<Adapter.ViewHolder>() {
        private val dataSet = arrayListOf<Todo>()

        inner class ViewHolder(binding: TodoListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val title: TextView = binding.title
            val done: CheckBox = binding.done
            val card: ConstraintLayout = binding.card
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(TodoListItemBinding.inflate(layoutInflater))


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = dataSet[position]
            holder.title.text = item.title
            holder.done.isChecked = item.done
            holder.done.setOnCheckedChangeListener { _, _ ->
                viewModel.toggleDoneTodo(item.id, holder.done.isChecked)
            }
            holder.card.setOnLongClickListener {
                onLongPressTodo(item)
                true
            }
        }

        override fun getItemCount() = dataSet.size

        fun getItem(position: Int) = dataSet[position]

        fun update(newDataSet: List<Todo>) {
            dataSet.clear()
            dataSet.addAll(newDataSet)
            notifyDataSetChanged()
        }
    }

    fun onLongPressTodo(model: Todo) {
        class TodoBottomSheet : ModalBottomSheet(
            arrayListOf(
                "Edit", "Toggle done", "Delete", "Close"
            ), arrayListOf(
                R.drawable.edit,
                R.drawable.check,
                R.drawable.delete,
                R.drawable.close,
            )
        ) {
            override fun handleAction(position: Int) {
                when (position) {
                    0 -> AddTodoBottomSheet(model).show(supportFragmentManager, "launching edit todo bottom sheet")
                    1 -> viewModel.toggleDoneTodo(model.id, model.done)
                    2 -> viewModel.deleteTodo(model)
                    3 -> dismiss()
                }
            }
        }
        TodoBottomSheet().show(
            supportFragmentManager, "launching todo bottom sheet"
        )
    }

    override fun insertTodoAPI(todo: Todo) {
        viewModel.insertTodo(todo)
    }

    override fun makeToast(message: String) {
        Toast.makeText(
            this@MainActivity, message, Toast.LENGTH_SHORT
        ).show()
    }
}