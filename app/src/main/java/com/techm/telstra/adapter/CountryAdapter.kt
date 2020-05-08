package com.techm.telstra.adapter

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techm.telstra.BR
import com.techm.telstra.R
import com.techm.telstra.databinding.ItemLayoutBinding
import com.techm.telstra.model.InfoModelData



class CountryAdapter(private var countryList: ArrayList<InfoModelData>,private var context:Context) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    /**
     * this method is returning the view for each item in the list
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_layout, parent, false)
        return ViewHolder(binding)
    }
    /**
     * This method for setting list to current list from another class
     * @param countryList for to get updated list
     */
    fun setList(countryList: ArrayList<InfoModelData>)
    {
        this.countryList=countryList
        notifyDataSetChanged()
    }
    /**
     * This method for set data to item.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countryList[position])
    }

    /**
     * @return the size of the list.
     */
    override fun getItemCount(): Int {
        return countryList.size
    }
    /**
     * This class for creating items
     */
    class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: InfoModelData) {
            //Loading image
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