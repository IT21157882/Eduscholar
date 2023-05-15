package com.example.mycrud.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycrud.R
import com.example.mycrud.models.EmployeeModel

class EmpAdapter( private var empList: ArrayList<EmployeeModel>) :
    RecyclerView.Adapter<EmpAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.emp_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = empList[position]
        holder.tvEmpCourseName.text = currentEmp.empCourseName
        holder.tvEmpDuration.text = currentEmp.empDuration
        holder.tvEmpDonatePrice.text = currentEmp.empDonatePrice
    }

    override fun getItemCount(): Int {
        return empList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvEmpCourseName : TextView = itemView.findViewById(R.id.tvEmpCourseName)
        val tvEmpDuration : TextView = itemView.findViewById(R.id.tvEmpDuration)
        val tvEmpDonatePrice : TextView = itemView.findViewById(R.id.tvEmpDonatePrice)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}