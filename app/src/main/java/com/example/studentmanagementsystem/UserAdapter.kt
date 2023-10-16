package com.example.studentmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementsystem.StudentData.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var actionDelete: ((User) -> Unit)? = null
    private var list = mutableListOf<User>()
    private var actionEdit: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.database_display, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]
        holder.tvId.text = user.roll_no.toString()
        holder.tvFullName.text = user.name
        holder.tvAge.text = user.age
        holder.tvPhone.text = user.phone
        holder.tvEmail.text = user.email
        holder.tvGrade.text = user.grade
        holder.actionEdit.setOnClickListener { actionEdit?.invoke(user) }
        holder.actionDelete.setOnClickListener { actionDelete?.invoke(user) }
    }

    fun setData(data: List<User>) {
        list.apply {
            clear()
            addAll(data)
        }
    }

    fun setOnActionEditListener(callback: (User) -> Unit) {
        this.actionEdit = callback
    }

    fun setOnActionDeleteListener(callback: (User) -> Unit) {
        this.actionDelete = callback
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView = itemView.findViewById(R.id.tv_uid)
        val tvFullName: TextView = itemView.findViewById(R.id.tv_name)
        val tvAge: TextView = itemView.findViewById(R.id.tv_age)
        val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val tvGrade: TextView = itemView.findViewById(R.id.tv_grades)
        val actionEdit: ImageView = itemView.findViewById(R.id.btnUpdate)
        val actionDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }
}