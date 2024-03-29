package com.example.foodinfo.data.local.room.data_source

import com.example.foodinfo.data.local.data_source.APICredentialsLocalSource
import com.example.foodinfo.data.local.model.EdamamCredentialsDB
import com.example.foodinfo.data.local.model.GitHubCredentialsDB
import com.example.foodinfo.data.local.room.dao.APICredentialsDAO
import com.example.foodinfo.data.local.room.model.entity.EdamamCredentialsEntity
import com.example.foodinfo.data.local.room.model.entity.GitHubCredentialsEntity
import javax.inject.Inject


class APICredentialsRoomSource @Inject constructor(
    private val apiCredentialsDAO: APICredentialsDAO
) : APICredentialsLocalSource {
    override suspend fun getEdamam(name: String): EdamamCredentialsDB {
        return apiCredentialsDAO.getEdamam(name)
    }

    override suspend fun getGitHub(name: String): GitHubCredentialsDB {
        return apiCredentialsDAO.getGitHub(name)
    }


    override suspend fun addEdamam(credentials: EdamamCredentialsDB) {
        apiCredentialsDAO.addEdamam(EdamamCredentialsEntity(credentials))
    }

    override suspend fun addGitHub(credentials: GitHubCredentialsDB) {
        apiCredentialsDAO.addGitHub(GitHubCredentialsEntity(credentials))
    }
}