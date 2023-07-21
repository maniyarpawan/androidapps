package com.pawan.propertyapplication.network.model

data class PropertyDTO(
    val exclusions: List<List<Exclusion>>,
    val facilities: List<Facility>
)