package com.example.downloadingprogress

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.downloadingprogress.databinding.ActivityMainBinding
import com.example.downloadingprogress.entity.UploadItem
import com.example.downloadingprogress.enum.UploadState
import com.example.downloadingprogress.viewModel.UploadItemViewModel

class MainActivity : AppCompatActivity(),UploadAdapter.UploadItemListeners {

    private lateinit var binding:ActivityMainBinding
    private lateinit var uploadItemViewModel: UploadItemViewModel
    private lateinit var uploadAdapter: UploadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        uploadItemViewModel = ViewModelProvider(this)[UploadItemViewModel::class.java]
        getUploadItemData()
        setUploadItemData()

        binding.btnUploadAll.setOnClickListener {
            bulkUpload()
        }
    }

    /**
     * get data from local list
     */
    private fun getUploadItemData(){
        uploadItemViewModel.getUploadList()
    }

    /**
     * set the recycler view
     */
    private fun setUploadItemData(){
        binding.rvUpload.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            uploadAdapter = UploadAdapter(uploadItemViewModel.uploadItemArrayList,this@MainActivity)
            adapter = uploadAdapter
        }
    }

    /**
     * handle click event of item
     */
    override fun onUpdateStatusClick(position: Int) {
        val item = uploadItemViewModel.uploadItemArrayList[position]
        if (item.state == UploadState.UPLOAD) {
            item.state = UploadState.UPLOADING
            uploadAdapter.notifyItemChanged(position)
            simulateProgress(item, position)
        }
    }

    /**
     * for update the progress bar according click on the upload button
     */
    private fun simulateProgress(item: UploadItem, position: Int) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (item.progress < 100) {
                    item.progress += (5..10).random()
                    if (item.progress >= 100) {
                        item.progress = 100
                        item.state = UploadState.SUCCESS
                    }
                    uploadAdapter.notifyItemChanged(position)
                    handler.postDelayed(this, 1000)
                }
            }
        }, 1000)
    }

    /**
     * bulk upload functionality
     */
    private fun bulkUpload() {
        uploadItemViewModel.uploadItemArrayList.forEachIndexed { index, item ->
            if (item.state == UploadState.UPLOAD) {
                item.state = UploadState.UPLOADING
                simulateProgress(item, index)
            }
        }
        uploadAdapter.notifyDataSetChanged()
    }

}