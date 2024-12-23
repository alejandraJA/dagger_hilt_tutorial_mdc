package com.example.gob_fact.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.FactEntity

class FactAdapter(
    private val factInterface: (FactEntity) -> Unit
) : PagingDataAdapter<FactEntity, FactViewHolder>(FACT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fact, parent, false)
        return FactViewHolder(view, factInterface)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val fact = getItem(position)
        fact?.let { holder.bind(it) }
    }

    companion object {
        private val FACT_COMPARATOR = object : DiffUtil.ItemCallback<FactEntity>() {
            override fun areItemsTheSame(oldItem: FactEntity, newItem: FactEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FactEntity, newItem: FactEntity): Boolean =
                oldItem == newItem
        }
    }
}