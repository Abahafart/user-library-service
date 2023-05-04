package com.abahafart.kotlin.template.infra.repository

interface GenericMapper<MODEL : Any, REQUEST_ENTITY, ENTITY_PERSISTENCE> {

    fun fromModelToEntity(model: MODEL): ENTITY_PERSISTENCE
    fun fromEntityRequestToEntity(requestEntity: REQUEST_ENTITY): ENTITY_PERSISTENCE
    fun fromEntityPersistenceToModel(entityPersistence: ENTITY_PERSISTENCE): MODEL

}