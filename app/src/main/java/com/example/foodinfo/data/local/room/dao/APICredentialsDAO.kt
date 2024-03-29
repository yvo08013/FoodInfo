package com.example.foodinfo.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodinfo.data.local.model.EdamamCredentialsDB
import com.example.foodinfo.data.local.model.GitHubCredentialsDB
import com.example.foodinfo.data.local.room.model.entity.EdamamCredentialsEntity
import com.example.foodinfo.data.local.room.model.entity.GitHubCredentialsEntity


@Dao
interface APICredentialsDAO {
    @Query(
        "SELECT * FROM ${EdamamCredentialsDB.TABLE_NAME} WHERE " +
        "${EdamamCredentialsDB.Columns.NAME} LIKE '%' || :name || '%' "
    )
    suspend fun getEdamam(name: String): EdamamCredentialsEntity

    @Query(
        "SELECT * FROM ${GitHubCredentialsDB.TABLE_NAME} WHERE " +
        "${GitHubCredentialsDB.Columns.NAME} LIKE '%' || :name || '%' "
    )
    suspend fun getGitHub(name: String): GitHubCredentialsEntity


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEdamam(credentials: EdamamCredentialsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGitHub(credentials: GitHubCredentialsEntity)
}