package com.infinity.omos.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infinity.omos.data.AllRecords
import com.infinity.omos.databinding.ListAllrecordsItemBinding

class AllRecordsListAdapter internal constructor(context: Context)
    : RecyclerView.Adapter<AllRecordsListAdapter.AllRecordsViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val context = context
    private var category = emptyList<AllRecords>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRecordsViewHolder {
        val binding = ListAllrecordsItemBinding.inflate(inflater, parent, false)
        return AllRecordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllRecordsViewHolder, position: Int) {
        holder.bind(category[position])
    }

    inner class AllRecordsViewHolder(val binding: ListAllrecordsItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(content: AllRecords){
            binding.category = content

            val mAdapter = CategoryListAdapter(context!!, content.myRecord)
            mAdapter.setRecords(content.myRecord)
            binding.rvCategory.apply{
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    internal fun setCategory(category: List<AllRecords>) {
        this.category = category
        notifyDataSetChanged()
    }

    override fun getItemCount() = category.size
}