package com.ngedev.quotime.ui.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ngedev.quotime.Quote
import com.ngedev.quotime.databinding.RowListQuotesBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 */
class ListAdapterQuotes(
    private val clickListener: (Quote)->Unit
):ListAdapter<Quote, ListAdapterQuotes.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(RowListQuotesBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quote = getItem(position)

        holder.bind(quote)

        holder.itemView.setOnClickListener {
            clickListener(quote)
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Quote>(){
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean
        = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean
        = oldItem == newItem

    }

    class ViewHolder(private var binding:RowListQuotesBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(quote: Quote){
           binding.quote = quote
           binding.executePendingBindings()
       }
    }

}