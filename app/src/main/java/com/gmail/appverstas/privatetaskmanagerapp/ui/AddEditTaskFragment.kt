package com.gmail.appverstas.privatetaskmanagerapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.appverstas.privatetaskmanagerapp.R
import com.gmail.appverstas.privatetaskmanagerapp.data.entities.Task
import com.gmail.appverstas.privatetaskmanagerapp.viewmodels.AddEditTaskViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(), DatePicker.OnDateChangedListener,
TimePicker.OnTimeChangedListener {

    private val viewModel: AddEditTaskViewModel by viewModels()
    private val args: AddEditTaskFragmentArgs by navArgs()

    lateinit var etTitle: EditText
    lateinit var etContent: EditText
    lateinit var tvDateText: TextView
    lateinit var tvTimeText: TextView
    private var currentTask: Task? = null
    private val c = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit_task, container, false)

        etTitle = view.findViewById<EditText>(R.id.etTaskTitle)
        etContent = view.findViewById<EditText>(R.id.etTaskContent)
        tvDateText = view.findViewById<TextView>(R.id.tvDateText)
        tvTimeText = view.findViewById<TextView>(R.id.tvTimeText)
        val linearLayoutDate = view.findViewById<LinearLayout>(R.id.linearLayoutDate)
        val linearLayoutTime = view.findViewById<LinearLayout>(R.id.linearLayoutTime)
        setHasOptionsMenu(true)
        setCurrentDateTime(c)

        if(args.id.isNotEmpty()){
            viewModel.observeTaskByID(args.id).observe(viewLifecycleOwner, androidx.lifecycle.Observer { task ->
                currentTask = task
                etTitle.setText(task.title)
                etContent.setText(task.content)
                c.timeInMillis = task.date
                setCurrentDateTime(c)
            })
        }

        linearLayoutDate.setOnClickListener {
            setDateFromDatePickerDialog(c)
        }

        linearLayoutTime.setOnClickListener {
            setTimeFromTimePickerDialog(c)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add_edit_task, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_add_edit_task_delete -> discardTask()
            R.id.menu_add_edit_task_save -> {
                if(validateInput()){
                    saveTask()
                }else{
                    Toast.makeText(requireContext(), getString(R.string.please_insert_title), Toast.LENGTH_SHORT).show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }



    override fun onDateChanged(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {

    }

    private fun saveTask() {
        val newTask = Task(
            etTitle.text.toString(),
            etContent.text?.toString() ?: "",
            c.timeInMillis,
            currentTask?.id ?: UUID.randomUUID().toString())
        viewModel.insertTask(newTask)
        Snackbar.make(requireView(), getString(R.string.snackbar_task_saved), Snackbar.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addEditTaskFragment_to_tasksFragment)
    }

    private fun discardTask() {
        currentTask?.let{ task ->
            viewModel.deleteTask(task)
            Snackbar.make(requireView(), getString(R.string.snackbar_task_deleted), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.snackbar_undo)){
                    viewModel.insertTask(task)
                }
                .show()
        } ?: Snackbar.make(requireView(), getString(R.string.snackbar_task_not_saved), Snackbar.LENGTH_SHORT)
            .show()
        findNavController().navigate(R.id.action_addEditTaskFragment_to_tasksFragment)
    }

    private fun setCurrentDateTime(c: Calendar){
        tvDateText.text = "${c.get(Calendar.DAY_OF_MONTH)}.${c.get(Calendar.MONTH)+1}.${c.get(Calendar.YEAR)}"
        tvTimeText.text = "${c.get(Calendar.HOUR_OF_DAY)}:${c.get(Calendar.MINUTE)}"
    }

    private fun validateInput(): Boolean {
        return (etTitle.text.isNotEmpty())
    }

    private fun setTimeFromTimePickerDialog(c: Calendar){
        TimePickerDialog(requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay , minute ->
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute)
                setCurrentDateTime(c)
            },
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setDateFromDatePickerDialog(c: Calendar) {
        val datePicker = DatePickerDialog(requireContext())
        datePicker.setOnDateSetListener { _, year, month, day ->
            c.set(year, month, day)
            setCurrentDateTime(c)
        }
        datePicker.show()
    }

}