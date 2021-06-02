package com.capstone.personalmedicalrecord.ui.patient.data.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Note
import com.capstone.personalmedicalrecord.databinding.ItemNoteBinding

class NotesAdapter(private val callback: NotesCallback) :
    RecyclerView.Adapter<NotesAdapter.ListViewHolder>() {

    private var listData = ArrayList<Note>()

    fun setData(newListData: List<Note>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    inner class ListViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemDate.text = note.datetime
                tvItemDescription.text = note.description
                if (note.from != "me") {
                    itemContainer.setBackgroundColor(ContextCompat.getColor(itemContainer.context,
                        R.color.blue_dark))
                }
                itemView.setOnClickListener {
                    callback.onItemClick(note)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val record = listData[position]
        holder.bind(record)
    }

    override fun getItemCount(): Int = listData.size
}