package fi.lasicaine.nutrilicious.model

import fi.lasicaine.nutrilicious.data.network.dto.FoodDto

data class Food(
    val id: String,
    val name: String,
    val type: String,
    var isFavorite: Boolean = false
) {
    constructor(dto: FoodDto) : this(dto.ndbno, dto.name, dto.group)
}