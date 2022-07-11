package com.task.shortlyapp.ui.shorteninglink.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.shortlyapp.R
import com.task.shortlyapp.databinding.LayoutListItemLinkHistoryBinding
import com.task.shortlyapp.repository.locale.entity.ShortlyLink
import com.task.shortlyapp.utils.getColor

/**
 * Created by Muhammad Maqsood on 11/07/2021.
 */
class ShortlyLinksListAdapter(
    private val onDeleteCallBack: ((shortlyLink: ShortlyLink) -> Unit)? = null,
    private val onCopyCallBack: ((shortlyLink: ShortlyLink) -> Unit)? = null
) :
    RecyclerView.Adapter<ShortlyLinksListAdapter.ViewHolder>() {

    var shortlyLinks: MutableList<ShortlyLink> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val mostViewedListItemBinding: LayoutListItemLinkHistoryBinding =
            LayoutListItemLinkHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

        return ViewHolder(mostViewedListItemBinding)
    }

    override fun getItemCount(): Int {
        return shortlyLinks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val shortlyLink = shortlyLinks[position].let {
            holder.bind(it)
            return@let it
        }

        holder.layoutListItemLinkHistoryBinding.buttonCopy.setOnClickListener {
            onCopied(shortlyLink, position)
        }

        holder.layoutListItemLinkHistoryBinding.buttonDelete.setOnClickListener {
            onDeleteCallBack?.invoke(shortlyLink)
        }
    }

    private fun onCopied(shortlyLink: ShortlyLink, position: Int) {
        shortlyLinks.find { it.copied && (shortlyLink.copied == it.copied).not() }?.copied = false
        shortlyLink.copied = true
        notifyItemChanged(position)
        onCopyCallBack?.invoke(shortlyLink)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun add(shortlyLink: ShortlyLink) {
        synchronized(this.shortlyLinks) {
            this.shortlyLinks.add(0, shortlyLink)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(shortlyLink: ShortlyLink) {
        synchronized(this.shortlyLinks) {
            shortlyLinks.remove(shortlyLink)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(val layoutListItemLinkHistoryBinding: LayoutListItemLinkHistoryBinding) :
        RecyclerView.ViewHolder(layoutListItemLinkHistoryBinding.root) {

        fun bind(shortlyLink: ShortlyLink) {
            layoutListItemLinkHistoryBinding.apply {
                textViewLink.text = shortlyLink.original_link
                textViewShortlyLink.text = shortlyLink.short_link
                root.context?.apply {
                    buttonCopy.text =
                        if (shortlyLink.copied) getString(R.string.copied) else getString(R.string.copy)
                    buttonCopy.setBackgroundColor(
                        if (shortlyLink.copied) getColor(this, R.color.purple) else getColor(
                            this,
                            R.color.green
                        )
                    )
                }
            }
        }
    }
}
