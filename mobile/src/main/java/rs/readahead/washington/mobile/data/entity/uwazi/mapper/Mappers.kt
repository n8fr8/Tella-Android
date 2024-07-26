package rs.readahead.washington.mobile.data.entity.uwazi.mapper

import rs.readahead.washington.mobile.data.entity.uwazi.CommonPropertyEntity
import rs.readahead.washington.mobile.data.entity.uwazi.DictionaryResponse
import rs.readahead.washington.mobile.data.entity.uwazi.NestedValueEntity
import rs.readahead.washington.mobile.data.entity.uwazi.PropertyEntity
import rs.readahead.washington.mobile.data.entity.uwazi.RelationShipEntitiesResponse
import rs.readahead.washington.mobile.data.entity.uwazi.RowDictionaryEntity
import rs.readahead.washington.mobile.data.entity.uwazi.TemplateResponse
import rs.readahead.washington.mobile.data.entity.uwazi.UwaziEntityRow
import rs.readahead.washington.mobile.data.entity.uwazi.UwaziRelationShipRow
import rs.readahead.washington.mobile.data.entity.uwazi.Value
import rs.readahead.washington.mobile.data.entity.uwazi.ValueEntity
import rs.readahead.washington.mobile.domain.entity.uwazi.CommonProperty
import rs.readahead.washington.mobile.domain.entity.uwazi.NestedSelectValue
import rs.readahead.washington.mobile.domain.entity.uwazi.Property
import rs.readahead.washington.mobile.domain.entity.uwazi.RelationShipRow
import rs.readahead.washington.mobile.domain.entity.uwazi.RowDictionary
import rs.readahead.washington.mobile.domain.entity.uwazi.SelectValue
import rs.readahead.washington.mobile.domain.entity.uwazi.UwaziRow

/**
 * Mapper for template response
 */
fun RelationShipEntitiesResponse.mapToDomainModel() = rows?.map {
    it.mapToDomainModel()
} ?: emptyList()

fun TemplateResponse.mapToDomainModel() = rows?.map {
    it.mapToDomainModel()
} ?: emptyList()

fun UwaziRelationShipRow.mapToDomainModel() = RelationShipRow(
    version = __v,
    id = _id,
    values = values?.map { it.mapToDomainModel() } ?: emptyList(),
    type = type,
    name = name,
    entityViewPage = entityViewPage
)

fun Value.mapToDomainModel() = rs.readahead.washington.mobile.domain.entity.uwazi.Value(
    label = label,
    id = id
)

fun UwaziEntityRow.mapToDomainModel() = UwaziRow(
    version = __v,
    _id = _id,
    commonProperties = commonProperties?.map { it.mapToDomainModel() } ?: emptyList(),
    default = default,
    name = name,
    properties = properties?.map { it.mapToDomainModel() } ?: emptyList())

fun CommonPropertyEntity.mapToDomainModel() = CommonProperty(
    id = _id,
    generatedId = generatedId,
    isCommonProperty = isCommonProperty,
    label = label,
    localID = localID ?: "",
    name = name,
    prioritySorting = prioritySorting,
    type = type
)

fun PropertyEntity.mapToDomainModel() = Property(
    _id = _id ?: "",
    id = id ?: "",
    showInCard = showInCard,
    content = content ?: "",
    label = label ?: "",
    name = name ?: "",
    nestedProperties = nestedProperties ?: emptyList(),
    required = required,
    type = type
)

/**
 * Mapper for dictionary response
 */
fun DictionaryResponse.mapToDomainModel() = rows?.map {
    it.mapToDomainModel()
} ?: emptyList()

fun RowDictionaryEntity.mapToDomainModel() = RowDictionary(
    version = __v ?: 0,
    _id = _id ?: "",
    name = name ?: "",
    values = values?.map { it.mapToDomainModel() } ?: emptyList()
)

fun ValueEntity.mapToDomainModel() = SelectValue(
    _id = _id ?: "",
    id = id ?: "",
    label = label,
    values = values?.map { it.mapToDomainModel() } ?: emptyList()
)

fun NestedValueEntity.mapToDomainModel() = NestedSelectValue(
    id = id ?: "",
    label = label ?: ""
)

