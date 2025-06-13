package com.example.shared.feature.folders.ui

interface FoldersViewModel {

    fun getFoldersData()

     fun createNewFolder(folderName: String, isSync: Boolean)

}