package com.gmail.appverstas.privatetaskmanagerapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.appverstas.privatetaskmanagerapp.R
import com.gmail.appverstas.privatetaskmanagerapp.data.entities.Task
import com.gmail.appverstas.privatetaskmanagerapp.viewmodels.TaskDetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private val args: TaskDetailFragmentArgs by navArgs()

    private val taskDetailViewModel: TaskDetailViewModel by viewModels()

    private var currentTask: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)

        val tvTaskTitle = view.findViewById<TextView>(R.id.tvTaskTitle)
        val tvTaskContent = view.findViewById<TextView>(R.id.tvTaskContent)
        val tvDateTime = view.findViewById<TextView>(R.id.tvDateTime)

        val dateFormat = SimpleDateFormat("dd.MM.yy, HH:mm", Locale.getDefault())

        setHasOptionsMenu(true)

        taskDetailViewModel.observeTaskByID(args.id).observe(viewLifecycleOwner, Observer { task ->
            currentTask = task
            tvTaskTitle.text = task.title
            tvTaskContent.text = task.content
            tvDateTime.text = dateFormat.format(task.date)
        })

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_task_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_task_details_edit -> findNavController().navigate(TaskDetailFragmentDirections.actionTaskDetailFragmentToAddEditTaskFragment(args.id))
            R.id.menu_task_details_delete -> deleteCurrentTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteCurrentTask(){
        currentTask?.let { task ->
            taskDetailViewModel.deleteTask(task)
            Snackbar.make(requireView(), getString(R.string.snackbar_task_deleted), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.snackbar_undo)){
                    taskDetailViewModel.insertTask(task)
                }
                .show()
        }
        findNavController().navigate(R.id.action_taskDetailFragment_to_tasksFragment)
    }
}