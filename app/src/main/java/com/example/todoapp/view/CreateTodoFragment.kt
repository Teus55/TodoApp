package com.example.todoapp.view

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
import java.time.Year
import java.util.Calendar
import java.util.concurrent.TimeUnit

class CreateTodoFragment :
    Fragment(),
    RadioClick,
    TodoEditClick,
    DateClickListener,
    TimeClickListener,
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: FragmentCreateTodoBinding
    private lateinit var viewModel: DetailTodoViewModel
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
//        return binding.root
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_todo, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS), NotificationHelper.REQUEST_NOTIF
            )
        }

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        binding.todo = Todo("", "", 3, 0, 0)
        binding.addlistener = this
        binding.radioListener = this
        binding.listenerDate = this
        binding.listenerTime = this

        binding.btnAdd.setOnClickListener {
//            val notif = NotificationHelper(view.context)
//            notif.createNotification("Todo Created",
//                "A new todo has been created! Stay focus!")

            val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(30, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "title" to "Todo created",
                        "message" to "Stay focus"
                    )
                )
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
        }
        // yang lama dan tidak bisa
//        binding.btnAdd.setOnClickListener {
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
//            var todo = Todo(binding.txtTitle.text.toString(),
//                binding.txtNotes.text.toString(), radio.tag.toString().toInt(), 0)
//            val list = listOf(todo)
//            viewModel.addTodo(list)
//            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
//            Navigation.findNavController(it).popBackStack()
//            val notif = NotificationHelper(view.context, requireActivity())
//            notif.createNotification("Todo Created",
//                "A new todo has been created! Stay focus!")
//        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NotificationHelper.REQUEST_NOTIF) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                NotificationHelper(requireContext())
                    .createNotification(
                        "Todo Created",
                        "A new todo has been created! Stay focus!"
                    )
            }
        }
    }

    override fun onRadioClick(v: View, priority: Int, obj: Todo) {
        obj.priority = priority
    }


    override fun onTodoEditClick(v: View) {
//        val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//            .setInitialDelay(30, TimeUnit.SECONDS)
//            .setInputData(
//                workDataOf(
//                    "title" to "Todo created",
//                    "message" to "Stay focus"
//                )
//            )
//            .build()
//        WorkManager.getInstance(requireContext()).enqueue(workRequest)
//
//        val list = listOf(binding.todo!!)
//        viewModel.addTodo(list)
//        Toast.makeText(view?.context, "Data Added", Toast.LENGTH_LONG).show()
//        Navigation.findNavController(v).popBackStack()

        val c = Calendar.getInstance()
        c.set(year, month, day, hour, minute, 0)
        val today = Calendar.getInstance()
        val diff = (c.timeInMillis/1000L) - (today.timeInMillis/1000L)

        binding.todo!!.todo_date = (c.timeInMillis/1000L).toInt()

        val list = listOf(binding.todo!!)
        viewModel.addTodo(list)
        Toast.makeText(v.context, "Data added", Toast.LENGTH_LONG).show()
        val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
            .setInitialDelay(diff, TimeUnit.SECONDS)
            .setInputData(workDataOf(
                "title" to "Todo Created",
                "message" to "A new todo has been created! Stay Focus"))
            .build()
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        Navigation.findNavController(v).popBackStack()
    }

    override fun onDateClick(v: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        activity?.let { it1 -> DatePickerDialog(it1, this, year, month, day).show() }
    }

    override fun onTimeClick(v: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year, month, day)
            binding.txtDate.setText(day.toString().padStart(2,'0') + "-"
                    +month.toString().padStart(2,'0')+ "-" + year)
            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(p0: TimePicker?, h: Int, m: Int) {
        binding.txtTime.setText(
            h.toString().padStart(2,'0') + ":" +
                    m.toString().padStart(2,'0')
        )
        this.hour = h
        this.minute = m
    }
}