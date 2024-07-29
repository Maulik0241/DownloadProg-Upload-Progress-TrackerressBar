package com.example.downloadingprogress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.downloadingprogress.entity.UploadItem
import com.example.downloadingprogress.enum.UploadState

class UploadAdapter(
    private val items: List<UploadItem>,
    private val listeners:UploadAdapter.UploadItemListeners) :
    RecyclerView.Adapter<UploadAdapter.UploadViewHolder>() {

        interface UploadItemListeners{
             fun onUpdateStatusClick(position: Int)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_upload, parent, false)
        return UploadViewHolder(view)
    }

    override fun onBindViewHolder(holder: UploadViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class UploadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        private val statusImageView: ImageView = itemView.findViewById(R.id.ivUpload)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun bind(item: UploadItem) {
            titleTextView.text = item.title
            when (item.state) {
                UploadState.UPLOAD -> {
                    statusImageView.setImageResource(R.drawable.ic_upload)
                    statusImageView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    statusImageView.setOnClickListener {
                        listeners.onUpdateStatusClick(adapterPosition)
                    }
                }
                UploadState.UPLOADING -> {
                    statusImageView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    progressBar.progress = item.progress
                }
                UploadState.SUCCESS -> {
                    statusImageView.setImageResource(R.drawable.ic_upload_done)
                    statusImageView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
                UploadState.FAILURE -> {
                    statusImageView.setImageResource(R.drawable.ic_upload_done)
                    statusImageView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}
