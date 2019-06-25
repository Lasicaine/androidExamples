package fi.lasicaine.nutritionalvalue.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fi.lasicaine.nutritionalvalue.data.db.NutrientListConverter
import fi.lasicaine.nutritionalvalue.data.network.dto.DetailsDto
import fi.lasicaine.nutritionalvalue.data.network.dto.NutrientDto


@Entity(tableName = "details")
@TypeConverters(NutrientListConverter::class)
data class FoodDetails(
    @PrimaryKey val id: String,
    val name: String,
    val nutrients: List<Nutrient>
) {
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
    val amountPer100g: Amount,
    val type: NutrientType
) {
    constructor(dto: NutrientDto) : this(
        dto.nutrient_id!!,
        dto.detailsId!!,
        dto.name,
        Amount(dto.value.toDouble(), WeightUnit.fromString(dto.unit)),
        NutrientType.valueOf(dto.group.toUpperCase())
    )
}

enum class NutrientType {
    PROXIMATES, MINERALS, VITAMINS, LIPIDS, OTHER
}