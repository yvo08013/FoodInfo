package com.example.foodinfo.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodinfo.data.local.model.SearchInputDB
import com.example.foodinfo.data.local.room.model.entity.SearchInputEntity


@Dao
interface SearchHistoryDAO {
    @Query(
        "SELECT * FROM ${SearchInputDB.TABLE_NAME} WHERE " +
        "${SearchInputDB.Columns.INPUT_TEXT} LIKE '%' || :inputText || '%' " +
        "ORDER BY ${SearchInputDB.Columns.DATE} DESC " +
        "LIMIT ${SearchInputDB.LIMIT}"
    )
    suspend fun getHistory(inputText: String): List<SearchInputEntity>

    @Query(
        "SELECT * FROM ${SearchInputDB.TABLE_NAME} " +
        "ORDER BY ${SearchInputDB.Columns.DATE}"
    )
    suspend fun getHistoryAll(): List<SearchInputEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(searchInput: List<SearchInputEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInput(searchInput: SearchInputEntity)
}