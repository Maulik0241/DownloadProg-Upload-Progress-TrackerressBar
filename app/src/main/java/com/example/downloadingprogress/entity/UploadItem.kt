package com.example.downloadingprogress.entity

import com.example.downloadingprogress.enum.UploadState

data class UploadItem(
    val title: String,
    var state: UploadState,
    var progress: Int = 0 // Only used for UPLOADING state
){
    constructor():this("",UploadState.UPLOAD,0)
}