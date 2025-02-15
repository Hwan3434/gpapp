package jeonghwan.app.modules.di.common

import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.data.model.TombModel
import jeonghwan.app.entity.TombEntity

fun PersonModel.toEntity() = PersonEntity(
    personKey = personKey,
    spouse = spouse,
    alive = alive,
    etc = etc,
    clan = clan,
    family = family,
    name = name,
    generator = generator,
    gender = gender,
)

fun TombModel.toEntity() = TombEntity(
    key = tombKey,
    name = name,
)