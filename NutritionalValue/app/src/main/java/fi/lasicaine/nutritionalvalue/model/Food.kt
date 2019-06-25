package fi.lasicaine.nutritionalvalue.model

import androidx.room.*
import fi.lasicaine.nutritionalvalue.data.network.dto.FoodDto

@Entity(tableName = "favorites")
data class Food(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    var isFavorite: Boolean = false
) {
    constructor(dto: FoodDto) : this(dto.ndbno, dto.name, dto.group)
}