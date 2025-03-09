package com.example.tatatechtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tatatechtest.R
import com.example.tatatechtest.data.models.RandomText

class StringAdapter(private val onDelete: (Int) -> Unit) : RecyclerView.Adapter<StringAdapter.ViewHolder>() {
    private var strings: List<RandomText> = emptyList()

    // ViewHolder using View Binding (optional) or findViewById
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stringDetails: TextView = itemView.findViewById(R.id.stringDetails)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lyt_string, parent, false) // Use custom layout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = strings[position]
        holder.stringDetails.text = "String: ${item.value}\nLength: ${item.length}\nCreated: ${item.created}"
        holder.deleteButton.setOnClickListener { onDelete(position) }
    }

    override fun getItemCount() = strings.size

    fun updateStrings(newStrings: List<RandomText>) {
        strings = newStrings
        notifyDataSetChanged() // Consider DiffUtil for production
    }
}