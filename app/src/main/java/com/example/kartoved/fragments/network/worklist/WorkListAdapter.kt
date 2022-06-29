package com.example.kartoved.fragments.network.worklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kartoved.R
import com.example.kartoved.databinding.WorkRowBinding
import com.example.kartoved.model.Work

class WorkListAdapter: RecyclerView.Adapter<WorkListAdapter.MyViewHolder>() {

    private var workList = ArrayList<Work>()

    private var previousExpandedPosition = -1
    private var mExpandedPosition = -1

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = WorkRowBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.work_row,parent,false))
    }

    override fun getItemCount(): Int {
        return workList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = workList[position]
        holder.binding.workNameView.text = currentItem.name
        holder.binding.workDescriptionTextView.text = currentItem.task

        if (currentItem.type_work == "polyline"){
            holder.binding.workIconView.setImageResource(R.drawable.ic_polyline)
        }else if (currentItem.type_work == "polygon"){
            holder.binding.workIconView.setImageResource(R.drawable.ic_area)
        }

        //ExpandMenu
        val isExpanded = (position==mExpandedPosition)

        holder.binding.popOutMenuGroup.visibility = if(isExpanded){View.VISIBLE}else{View.GONE}
        holder.binding.expandButton.setImageResource(if (isExpanded){ R.drawable.ic_expand_open }else{ R.drawable.ic_expand_closed })
        if (isExpanded) previousExpandedPosition = position


        holder.binding.expandButton.setOnClickListener{
            if (isExpanded){ mExpandedPosition = -1 }else{ mExpandedPosition = position }
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }

        holder.binding.startWorkButton.setOnClickListener{
            val action = WorkListFragmentDirections.actionFragmentWorkListToNetworkFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(workList: ArrayList<Work>?){
        this.workList = workList!!
        notifyDataSetChanged()
    }
}