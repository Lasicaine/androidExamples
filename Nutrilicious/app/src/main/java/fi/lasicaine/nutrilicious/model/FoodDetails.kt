package fi.lasicaine.nutrilicious.model

import fi.lasicaine.nutrilicious.data.network.dto.DetailsDto
import fi.lasicaine.nutrilicious.data.network.dto.NutrientDto

data class FoodDetails(val id: String, val name: String, val nutrients: List<Nutrient>) {
    constructor(dto: DetailsDto) : this(
        dto.desc.ndbno,
        dto.desc.name,
        dto.nutrients.map(::Nutrient)
    )
}

data class Nutrient(
    val id: Int,
    val detailsId: String,
    val name: String,
    val amountPer100g: Float,
    val unit: String,
    val type: NutrientType
) {
    constructor(dto: NutrientDto) : this(
        dto.nutrient_id!!,
        dto.detailsId!!,
        dto.name,
        dto.value,
        dto.unit,
        NutrientType.valueOf(dto.group.toUpperCase())
    )
}

enum class NutrientType {
    PROXIMATES, MINERALS, VITAMINS, LIPIDS, OTHER
}