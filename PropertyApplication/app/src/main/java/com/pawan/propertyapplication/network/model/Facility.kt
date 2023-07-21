package com.pawan.propertyapplication.network.model

data class Facility(
    val facility_id: String,
    val name: String,
    val options: List<Option>
)