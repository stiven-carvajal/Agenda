package com.example.agenda

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.MainActivity.Companion.TaskOne
import com.example.agenda.MainActivity.Companion.flag
import com.example.agenda.databinding.ItemBinding
import com.example.agenda.room.agendaData

class adapterAgenda(var taskList: List<agendaData>) : RecyclerView.Adapter<adapterAgenda.UserHolder>() {

    inner class UserHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        var tarea = taskList.get(position)
        holder.binding.tarea.text = tarea.tarea
        holder.binding.fecha.text = tarea.fecha

        holder.binding.wrapper.setOnClickListener{
            flag = true
            TaskOne = tarea
            val intent = Intent(holder.itemView.context, setTask::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  taskList.size
    }

}