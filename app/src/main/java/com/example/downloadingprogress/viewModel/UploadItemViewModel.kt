package com.example.downloadingprogress.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.downloadingprogress.entity.UploadItem
import com.example.downloadingprogress.repository.UploadRepository

class UploadItemViewModel(application: Application):AndroidViewModel(application) {
    private var uploadRepository = UploadRepository(application)
    var uploadItemArrayList:ArrayList<UploadItem> = arrayListOf()

     fun getUploadList(){
        uploadRepository.getUploadList {
            uploadItemArrayList = it
        }
    }
}