package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.BasicOfSearchFilterDB
import com.example.foodinfo.local.dto.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.local.dto.BasicRecipeAttrDB
import com.example.foodinfo.remote.dto.BasicRecipeAttrNetwork
import com.example.foodinfo.repository.model.BasicOfFilterPresetModel
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel
import java.lang.Float.min
import kotlin.math.max


fun BasicOfSearchFilterExtendedDB.toDB(): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun BasicOfSearchFilterExtendedDB.toDBLatest(): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = if (this.minValue != null) max(
            this.minValue!!,
            this.attrInfo!!.rangeMin
        ) else null,
        maxValue = if (this.maxValue != null) min(
            this.maxValue!!,
            this.attrInfo!!.rangeMax
        ) else null
    )
}

fun List<BasicOfSearchFilterExtendedDB>.toModelEdit(): List<BasicOfSearchFilterEditModel> {
    return this.map { basic ->
        BasicOfSearchFilterEditModel(
            ID = basic.ID,
            infoID = basic.attrInfo!!.ID,
            name = basic.attrInfo!!.name,
            measure = basic.attrInfo!!.measure,
            stepSize = basic.attrInfo!!.stepSize,
            rangeMin = basic.attrInfo!!.rangeMin,
            rangeMax = basic.attrInfo!!.rangeMax,
            minValue = basic.minValue,
            maxValue = basic.maxValue
        )
    }
}

fun List<BasicOfSearchFilterExtendedDB>.toModelPreset(): List<BasicOfFilterPresetModel> {
    return this
        .filterNot { (it.minValue == null && it.maxValue == null) || it.attrInfo!!.tag == null }
        .map { nutrient ->
            BasicOfFilterPresetModel(
                tag = nutrient.attrInfo!!.tag!!,
                columnName = nutrient.attrInfo!!.columnName,
                minValue = if (nutrient.minValue != null) max(
                    nutrient.minValue!!,
                    nutrient.attrInfo!!.rangeMin
                ) else null,
                maxValue = if (nutrient.maxValue != null) min(
                    nutrient.maxValue!!,
                    nutrient.attrInfo!!.rangeMax
                ) else null
            )
        }
}

fun BasicOfSearchFilterEditModel.toDB(filterName: String): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        infoID = this.infoID,
        filterName = filterName,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun BasicOfSearchFilterExtendedDB.toDefault(): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = null,
        maxValue = null
    )
}

fun BasicRecipeAttrDB.toFilter(filterName: String): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        minValue = null,
        maxValue = null
    )
}

fun BasicRecipeAttrNetwork.toDB(): BasicRecipeAttrDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}