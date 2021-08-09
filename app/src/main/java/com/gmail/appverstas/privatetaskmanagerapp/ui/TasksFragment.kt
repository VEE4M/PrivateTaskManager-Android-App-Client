package com.gmail.appverstas.privatetaskmanagerapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.privatetaskmanagerapp.R
import com.gmail.appverstas.privatetaskmanagerapp.adapters.TasksAdapter
import com.gmail.appverstas.privatetaskmanagerapp.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModels()

    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.notesRecyclerView)
        val fabAddEdit = view.findViewById<FloatingActionButton>(R.id.fabAddEdit)

        adapter = TasksAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setHasOptionsMenu(true)

        taskViewModel.getAllTasks.observe(viewLifecycleOwner, Observer { updatedList ->
            adapter.updateTaskList(updatedList)
        })

        adapter.setOnItemClickListener {
            findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(it.id))
        }

        fabAddEdit.setOnClickListener {
            findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(""))
        }

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = adapter.getCurrentList()[position]
                taskViewModel.deleteTask(task)
                Snackbar.make(view, getString(R.string.snackbar_task_deleted), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.snackbar_undo)) {
                        taskViewModel.insertTask(task)
                    }.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_tasks, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_tasks_add_task -> findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(""))
        }
        return super.onOptionsItemSelected(item)
    }
}