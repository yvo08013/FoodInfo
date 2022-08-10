package com.example.foodinfo.local.model

import com.example.foodinfo.local.model.filter_field.CategoryField
import com.example.foodinfo.local.model.filter_field.NutrientField
import com.example.foodinfo.local.model.filter_field.RangeField
import com.example.foodinfo.local.entity.RecipeLabelEntity
import com.example.foodinfo.local.entity.RecipeNutrientEntity
import com.example.foodinfo.local.entity.SearchFilterEntity
import com.example.foodinfo.local.entity.recipe.RecipeEntity
import com.example.foodinfo.local.entity.recipe.RecipeExtendedEntity
import com.example.foodinfo.local.entity.recipe.RecipeShortEntity
import com.example.foodinfo.utils.queryExample
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Class that generate query for [RecipeEntity]
 *
 * Fields order:
 * * Range fields - fastest one, search goes through the fields of [RecipeEntity] itself
 * * Nutrient fields - slowly than Range fields but only one query to another table
 * for multiple nutrients
 * * Category fields - the slowest one, each category requires
 * subquery to another tables
 *
 * Because of AND separator between subqueries if any of subquery don't
 * match the condition, row will be denied, so it makes sense to check the fastest
 * conditions first
 *
 * @sample queryExample
 */
data class SearchFilter(
    val id: Long = 0,
    val name: String = "target",
    val rangeFields: HashSet<RangeField> = hashSetOf(),
    val categoryFields: HashSet<CategoryField> = hashSetOf(),
    val nutrientFields: HashSet<NutrientField> = hashSetOf(),
    var inputText: String = "",
    private val separator: String = " AND ",
    private var selector: String = RecipeShortEntity.SELECTOR,
    private var _query: String = "",
) {
    val query: String
        get() = _query

    private fun nutrientFieldsToQuery(): String {
        if (nutrientFields.isEmpty()) return ""
        val nutrientQueryList = nutrientFields.map { field ->
            nutrientFieldToQuery(
                field.value,
                field.minValue,
                field.maxValue
            )
        }
        var query = "${RecipeEntity.Columns.ID} IN "
        query += "("
        query += "SELECT ${RecipeNutrientEntity.Columns.RECIPE_ID} "
        query += "FROM ${RecipeNutrientEntity.TABLE_NAME} WHERE CASE "
        query += nutrientQueryList.joinToString(" ")
        query += " ELSE NULL END "
        query += "GROUP BY ${RecipeNutrientEntity.Columns.RECIPE_ID} "
        query += "HAVING  count(${RecipeNutrientEntity.Columns.RECIPE_ID}) = ${nutrientQueryList.size}"
        query += ")"
        return query
    }

    private fun nutrientFieldToQuery(
        name: String, minValue: Float?, maxValue: Float?
    ): String {
        var query = ""
        query += "WHEN ${RecipeNutrientEntity.Columns.LABEL} = '$name' THEN "
        query += rangeFieldToQuery(
            RecipeNutrientEntity.Columns.TOTAL_VALUE,
            minValue,
            maxValue
        )
        return query
    }

    private fun rangeFieldToQuery(
        column: String, minValue: Any?, maxValue: Any?
    ): String {
        if (minValue == null && maxValue == null) return ""
        return if (minValue == null) {
            "$column <= $maxValue"
        } else if (maxValue == null) {
            "$column >= $minValue"
        } else {
            when (minValue == maxValue) {
                true  -> "$column = $maxValue"
                false -> "$column BETWEEN $minValue AND $maxValue"
            }
        }
    }

    private fun categoryFieldsToQuery(): String {
        if (categoryFields.isEmpty()) return ""
        var query = "${RecipeEntity.Columns.ID} IN "
        val categoryQueryList = categoryFields.map { field ->
            categoryFieldToQuery(
                field.value,
                field.labels
            )
        }
        query += "("
        query += "SELECT ${RecipeLabelEntity.Columns.RECIPE_ID} "
        query += "FROM ${RecipeLabelEntity.TABLE_NAME} WHERE CASE "
        query += categoryQueryList.joinToString(" ")
        query += " ELSE NULL END "
        query += "GROUP BY ${RecipeLabelEntity.Columns.RECIPE_ID} "
        query += "HAVING  count(${RecipeLabelEntity.Columns.RECIPE_ID}) = "
        query += "${categoryFields.sumOf { it.labels.size }}"
        query += ")"
        return query
    }

    private fun categoryFieldToQuery(
        column: String, labels: List<String>
    ): String {
        var query = ""
        query += "WHEN ${RecipeLabelEntity.Columns.CATEGORY} = '$column' THEN "
        query += "${RecipeLabelEntity.Columns.LABEL} IN ('${labels.joinToString("', '")}')"
        return query
    }

    private fun inputTextToQuery(): String {
        if (inputText == "") return ""
        return "${RecipeEntity.Columns.NAME} LIKE '$inputText'"
    }


    fun setSelector(id: Int) {
        when (id) {
            RECIPE_SELECTOR_SHORT    -> selector = RecipeShortEntity.SELECTOR
            RECIPE_SELECTOR_EXTENDED -> selector = RecipeExtendedEntity.SELECTOR
        }
    }

    fun buildQuery() {
        _query = ""
        val subQueryList = arrayListOf<String>()
        subQueryList.addAll(rangeFields.map { field ->
            rangeFieldToQuery(
                field.value.column,
                field.minValue,
                field.maxValue
            )
        })
        subQueryList.add(nutrientFieldsToQuery())
        subQueryList.add(categoryFieldsToQuery())
        subQueryList.add(inputTextToQuery())
        subQueryList.removeAll(setOf(""))
        _query += selector
        if (subQueryList.size > 0) {
            _query += " WHERE " + subQueryList.joinToString(separator)
        }
    }

    companion object {
        const val RECIPE_SELECTOR_SHORT = 0
        const val RECIPE_SELECTOR_EXTENDED = 1

        fun fromEntity(entity: SearchFilterEntity): SearchFilter {
            return SearchFilter(
                id = entity.id,
                name = entity.name,
                inputText = entity.inputText,
                rangeFields = Gson().fromJson(
                    entity.rangeFields,
                    object : TypeToken<List<RangeField>>() {}.type
                ),
                nutrientFields = Gson().fromJson(
                    entity.nutrientFields,
                    object : TypeToken<List<NutrientField>>() {}.type
                ),
                categoryFields = Gson().fromJson(
                    entity.categoryFields,
                    object : TypeToken<List<CategoryField>>() {}.type
                )
            )
        }

        fun toEntity(filter: SearchFilter): SearchFilterEntity {
            return SearchFilterEntity(
                name = filter.name,
                inputText = filter.inputText,
                rangeFields = Gson().toJson(filter.rangeFields),
                nutrientFields = Gson().toJson(filter.nutrientFields),
                categoryFields = Gson().toJson(filter.categoryFields)
            )
        }
    }
}