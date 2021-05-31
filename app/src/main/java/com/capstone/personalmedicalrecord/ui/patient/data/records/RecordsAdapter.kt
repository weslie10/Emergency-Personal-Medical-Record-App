package com.capstone.personalmedicalrecord.ui.patient.data.records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.personalmedicalrecord.R
import com.capstone.personalmedicalrecord.core.domain.model.Record
import com.capstone.personalmedicalrecord.databinding.ItemRecordBinding

class RecordsAdapter(private val callback: RecordsCallback) :
    RecyclerView.Adapter<RecordsAdapter.ListViewHolder>() {

    private var listData = ArrayList<Record>()

    fun setData(newListData: List<Record>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    inner class ListViewHolder(private val binding: ItemRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(record: Record) {
            with(binding) {
                tvItemDate.text = record.date
                tvItemDescription.text = "Blood Check Result"
                tvItemPlace.visibility = View.GONE

                itemView.setOnClickListener {
                    callback.onItemClick(record)
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