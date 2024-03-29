package com.example.foodinfo.data.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.data.local.model.BasicOfRecipeMetadataDB


@Entity(
    tableName = BasicOfRecipeMetadataDB.TABLE_NAME,
    indices = [Index(value = arrayOf(BasicOfRecipeMetadataDB.Columns.ID), unique = true)]
)
data class BasicOfRecipeMetadataEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = Columns.TAG)
    override val tag: String?,

    @ColumnInfo(name = Columns.NAME)
    override val name: String,

    @ColumnInfo(name = Columns.COLUMN_NAME)
    override val columnName: String,

    @ColumnInfo(name = Columns.MEASURE)
    override val measure: String,

    @ColumnInfo(name = Columns.PRECISION)
    override val precision: Int,

    @ColumnInfo(name = Columns.RANGE_MIN)
    override val rangeMin: Float,

    @ColumnInfo(name = Columns.RANGE_MAX)
    override val rangeMax: Float,

    @ColumnInfo(name = Columns.STEP_SIZE)
    override val stepSize: Float

) : BasicOfRecipeMetadataDB(
    ID = ID,
    tag = tag,
    name = name,
    columnName = columnName,
    measure = measure,
    precision = precision,
    rangeMin = rangeMin,
    rangeMax = rangeMax,
    stepSize = stepSize
) {
    companion object {
        operator fun invoke(item: BasicOfRecipeMetadataDB): BasicOfRecipeMetadataEntity {
            return BasicOfRecipeMetadataEntity(
                ID = item.ID,
                tag = item.tag,
                name = item.name,
                columnName = item.columnName,
                measure = item.measure,
                precision = item.precision,
                rangeMin = item.rangeMin,
                rangeMax = item.rangeMax,
                stepSize = item.stepSize
            )
        }
    }
}