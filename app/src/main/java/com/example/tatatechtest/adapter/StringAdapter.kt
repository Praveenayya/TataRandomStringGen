package com.example.tatatechtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tatatechtest.R
import com.example.tatatechtest.data.models.RandomText
import com.example.tatatechtest.databinding.LytStringBinding



class StringAdapter(private val onDelete: (Int) -> Unit) : RecyclerView.Adapter<StringAdapter.StringViewHolder>() {

    private var strings: List<RandomText> = emptyList()

    // ViewHolder using View Binding
    class StringViewHolder(private val binding: LytStringBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(randomText: RandomText, onDelete: (Int) -> Unit, position: Int) {
            binding.stringDetails.text = "String: ${randomText.value}\nLength: ${randomText.length}\nCreated: ${randomText.created}"
            binding.deleteButton.setOnClickListener { onDelete(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val binding = LytStringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(strings[position], onDelete, position)
    }

    override fun getItemCount(): Int = strings.size

    fun updateStrings(newStrings: List<RandomText>) {
        strings = newStrings
        notifyDataSetChanged() // Simple update; use DiffUtil for production
    }
}