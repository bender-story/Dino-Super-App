package com.rahul.pod.dashboard.data

data class CategoryDataResponse(
    val categories: List<CategoryData>
)
data class CategoryData(
    val title: String,
    val values: List<SubCategoriesData>
)

data class SubCategoriesData(
    val imageURL: String,
    val value: String
)