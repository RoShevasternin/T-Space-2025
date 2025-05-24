package com.smarteca.foundsender.game.manager

import org.robovm.apple.foundation.*

object JsonStorageManager {

    private fun getFilePath(fileName: String): String {
        val directory = NSPathUtilities.getDocumentsDirectory()
        return "$directory/$fileName.json"
    }

    // 🔹 Збереження JSON у файл
    fun saveStringToFile(fileName: String, json: String) {
        val filePath = getFilePath(fileName)
        val nsString = NSString(json)
        try {
            nsString.writeFile(filePath, true, NSStringEncoding.UTF8)
        } catch (e: NSErrorException) {
            e.printStackTrace()
        }
    }

    // 🔹 Завантаження JSON із файлу
    fun loadStringFromFile(fileName: String): String? {
        val filePath = getFilePath(fileName)
        return try {
            NSString.readFile(filePath, NSStringEncoding.UTF8)
        } catch (e: NSErrorException) {
            null // Якщо файл не знайдено або сталася помилка
        }
    }

    // 🔹 Отримання списку всіх файлів у Documents
    fun listFilesInDocuments(): List<String> {
        val fileManager = NSFileManager.getDefaultManager()
        val documentsPath = NSPathUtilities.getDocumentsDirectory()

        val errorPtr = NSError.NSErrorPtr()
        val files = fileManager.getSubpathsAtPath(documentsPath)

        if (errorPtr.get() != null) {
            println("Error reading directory: ${errorPtr.get()?.localizedDescription}")
            return emptyList()
        }

        return files
    }

    // 🔹 Видалення JSON-файлу
    fun deleteFile(fileName: String) {
        val fileManager = NSFileManager.getDefaultManager()
        val filePath = getFilePath(fileName)

        try {
            fileManager.removeItemAtPath(filePath)
        } catch (e: NSErrorException) {
            println("Error deleting file: ${e.message}")
        }
    }

}
