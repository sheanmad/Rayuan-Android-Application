package com.rayuan.view.rating

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rayuan.databinding.ActivityRatingBinding
import com.rayuan.response.ResponseRatingItem

class RatingAdapter(private val listItem: List<ResponseRatingItem>) {
    class ListViewHolder(var binding: ActivityRatingBinding)

//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
//        val binding = ActivityRatingBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
//        return ListViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//
//        holder.binding.apply {
//            viewItemName.text = listItem[position].label
//            //viewItemUrl.text = listItem[position].certainty
//        }
//    }
//
//    override fun getItemCount(): Int = listItem.size

}