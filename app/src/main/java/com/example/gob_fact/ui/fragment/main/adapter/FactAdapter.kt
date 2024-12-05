package com.example.gob_fact.ui.fragment.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.FactEntity

class FactAdapter(
    private val facts: MutableList<FactEntity>,
    val factInterface: (Int) -> Unit,
    private val loadMoreFacts: () -> Unit
) : RecyclerView.Adapter<FactViewHolder>() {

    private var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fact, parent, false)
        return FactViewHolder(view, factInterface)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(facts[position])
        if (shouldLoadMoreFacts(position)) {
            loadMoreFacts()
            isLoading = true
        }
    }

    override fun getItemCount(): Int = facts.size

    fun addFacts(newFacts: List<FactEntity>) {
        val startPosition = facts.size
        facts.addAll(newFacts)
        notifyItemRangeInserted(startPosition, newFacts.size)
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
    }

    fun clear() {
        val size = facts.size
        facts.clear()
        notifyItemRangeRemoved(0, size)
    }

    private fun shouldLoadMoreFacts(position: Int): Boolean {
        return position == facts.size - 1 && !isLoading
    }
}