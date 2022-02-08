package com.infinity.omos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.infinity.omos.data.MyRecord
import com.infinity.omos.databinding.ListMyrecordItemBinding

class MyRecordListAdapter internal constructor(context: Context):
    ListAdapter<MyRecord, MyRecordListAdapter.ViewHolder>(
        MyRecordDiffUtil
    ){

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(private val binding: ListMyrecordItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(record: MyRecord) {
            binding.record = record
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMyrecordItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = getItem(position)
        holder.bind(record)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    companion object MyRecordDiffUtil: DiffUtil.ItemCallback<MyRecord>(){
        override fun areItemsTheSame(oldItem: MyRecord, newItem: MyRecord): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: MyRecord, newItem: MyRecord): Boolean {
            return oldItem==newItem
        }
    }
}