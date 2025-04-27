package com.example.mangaproj.Data.Utils

import android.content.Context

// Класс-помощник для работы с SharedPreferences
class PreferenceHelper(private val context: Context) {

    // Companion object содержит константы, общие для всех экземпляров класса
    companion object {
        // Ключ для доступа к SharedPreferences файлу
        private const val PREF_KEY = "MY_PREF"
    }

    // Функция для сохранения строковых данных
    fun saveStringData(key: String, data: String) {
        // Получаем экземпляр SharedPreferences с указанным именем файла
        val sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        // Редактируем и сохраняем данные асинхронно (apply() вместо commit())
        sharedPreferences.edit()
            .putString(key, data)  // Сохраняем строку по ключу
            .apply()              // Асинхронное сохранение
    }

    // Функция для получения сохраненных строковых данных
    fun getStringData(key: String): String? {
        // Получаем экземпляр SharedPreferences
        val sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        // Получаем данные по ключу, или null если данных нет
        return sharedPreferences.getString(key, null)
    }

    fun removeKey(key: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(key).apply()
    }
}