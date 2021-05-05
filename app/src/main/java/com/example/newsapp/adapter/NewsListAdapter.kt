package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.network.response.Article
import com.example.newsapp.databinding.NewsBinding
import com.example.newsapp.viewmodel.NewsAdapterItemModel
import com.example.newsapp.viewmodel.NewsListViewModel

class NewsListAdapter(
    private var mList: ArrayList<Article>,
    val mViewModel: NewsListViewModel
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var binding: NewsBinding? = null

        init {
            bind()
        }

        fun bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView)
                binding?.executePendingBindings()
            }
        }

        fun unbind() {
            if (binding != null) {
                binding?.unbind()
            }
        }

        fun setViewModel(
            itemModelActive: NewsAdapterItemModel
        ) {
            if (binding != null) {
                binding?.itemModel = itemModelActive
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_news_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        NewsAdapterItemModel(
            mList[position]
        ).let {
            holder.setViewModel(it)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onViewAttachedToWindow(holder: NewsListAdapter.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: NewsListAdapter.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind()
    }

    /**
     * update the new list to UI
     * @param list ArrayList<Article> : news item list
     */
    fun updateList(list: ArrayList<Article>) {
        mList = list
        notifyDataSetChanged()
    }
}