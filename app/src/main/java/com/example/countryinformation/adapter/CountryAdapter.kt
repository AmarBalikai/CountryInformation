package com.example.countryinformation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countryinformation.BR
import com.example.countryinformation.R
import com.example.countryinformation.databinding.ItemLayoutBinding
import com.example.countryinformation.model.InfoModelData
import com.example.countryinformation.roomdatabase.CountryEntity

/*
class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder>
{

    */
class CountryAdapter(var countryList: ArrayList<InfoModelData>, var context:Context) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapter.ViewHolder {
        val binding: ItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_layout, parent, false)
        return CountryAdapter.ViewHolder(binding)
    }
    fun setList(countryList: ArrayList<InfoModelData>)
    {
        this.countryList=countryList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: CountryAdapter.ViewHolder, position: Int) {
        holder.bind(countryList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return countryList.size
    }

    class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: InfoModelData) {

            Glide.with(itemView)
                .load(data.imageHref)
                .placeholder(R.drawable.no_image)
                .into(binding.imgImage)
            binding.setVariable(BR.data, data) //BR - generated class; BR.user -- 'user' is variable name declared in layout
            binding.data = data
            binding.executePendingBindings()
        }
    }
}