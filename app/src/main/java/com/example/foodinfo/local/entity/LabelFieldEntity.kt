package com.example.foodinfo.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = LabelFieldEntity.TABLE_NAME,
    indices = [Index(value = arrayOf(LabelFieldEntity.Columns.NAME), unique = true)]
)
data class LabelFieldEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    val id: Long = 0,

    @ColumnInfo(name = Columns.CATEGORY)
    val category: String,

    @ColumnInfo(name = Columns.NAME)
    val name: String,

    @ColumnInfo(name = Columns.DESCRIPTION)
    val description: String,

    @ColumnInfo(name = Columns.PREVIEW_URL)
    val previewURL: String,
) {

    object Columns {
        const val ID = "id"
        const val CATEGORY = "category"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PREVIEW_URL = "preview_url"
    }

    companion object {
        const val TABLE_NAME = "labels"
    }
}