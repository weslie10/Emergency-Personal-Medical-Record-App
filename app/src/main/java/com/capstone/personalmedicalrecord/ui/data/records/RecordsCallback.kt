package com.capstone.personalmedicalrecord.ui.data.records

import com.capstone.personalmedicalrecord.core.domain.model.Record

interface RecordsCallback {
    fun onItemClick(record: Record)
}