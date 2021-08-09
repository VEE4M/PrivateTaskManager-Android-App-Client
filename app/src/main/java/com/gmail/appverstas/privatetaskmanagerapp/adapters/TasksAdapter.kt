package com.gmail.appverstas.privatetaskmanagerapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.privatetaskmanagerapp.R
import com.gmail.appverstas.privatetaskmanagerapp.data.entities.Task
import java.text.SimpleDateFormat
import java.util.*

/**
 *Veli-Matti Tikkanen, 27.7.2021
 */
class TasksAdapter (): RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var taskList = listOf<Task>()
    private val c = Calendar.getInstance()
    private var onItemClickListener: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tasks_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateFormat = SimpleDateFormat("dd.MM.yy, HH:mm", Locale.getDefault())
        val dateString = dateFormat.format(taskList[position].date)
        holder.itemView.findViewById<TextView>(R.id.tvTitle).text = taskList[position].title
        holder.itemView.findViewById<TextView>(R.id.tvDate).text = dateString

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click ->
                click(taskList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun updateTaskList(updatedList: List<Task>){
        taskList = updatedList
        notifyDataSetChanged()
    }

    fun getCurrentList(): List<Task>{
        return taskList
    }

    fun setOnItemClickListener(onItemClick: (Task) -> Unit){
        this.onItemClickListener = onItemClick
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}