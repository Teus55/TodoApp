package com.example.todoapp.view

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCreateTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.util.NotificationHelper
import com.example.todoapp.util.TodoWorker
import com.example.todoapp.viewmodel.DetailTodoViewModel
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment() {
    private lateinit var binding: FragmentCreateTodoBinding
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding:FragmentCreateTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
//        return binding.root
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_todo, container, false )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        binding.todo = Todo("","",3, 0)

        binding.listener = this
        binding.radioListener = this


        binding.btnAdd.setOnClickListener {
//            TodoWorker.activity = requireActivity()
//            val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//                .setInitialDelay(30, TimeUnit.SECONDS)
//                .setInputData(
//                    workDataOf(
//                        "title" to "Todo created",
//                        "message" to "Stay focus"
//                    ))
//                .build()
//            WorkManager.getInstance(requireContext()).enqueue(workRequest)

//            var radio =
//                view.findViewById<RadioButton>(binding.radioGroupPriority.checkedRadioButtonId)
//
//            var todo = Todo(binding.txtTitle.text.toString(),
//                binding.txtNotes.text.toString(), radio.tag.toString().toInt(), 0)
//
//            val list = listOf(todo)
//            viewModel.addTodo(list)
//            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
//            Navigation.findNavController(it).popBackStack()

//            val notif = NotificationHelper(view.context, requireActivity())
//            notif.createNotification("Todo Created",
//                "A new todo has been created! Stay focus!")

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,
                                            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==NotificationHelper.REQUEST_NOTIF) {
            if(grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                NotificationHelper(requireContext(), requireActivity())
                    .createNotification("Todo Created",
                        "A new todo has been created! Stay focus!")
            }
        }
    }

}