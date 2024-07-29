package com.example.downloadingprogress.repository

import android.content.Context
import com.example.downloadingprogress.entity.UploadItem
import com.example.downloadingprogress.enum.UploadState

class UploadRepository(private val context:Context) {

    fun getUploadList(listener: (ArrayList<UploadItem>) -> Unit) {
        val uploadList = arrayListOf(
            UploadItem("File1", UploadState.UPLOAD, 0),
            UploadItem("File2", UploadState.UPLOAD, 0),
            UploadItem("File3", UploadState.UPLOAD, 0),
            UploadItem("File4", UploadState.UPLOAD, 0),
            UploadItem("File5", UploadState.UPLOAD, 0)
        )
        listener(uploadList)
    }
}