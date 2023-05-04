package com.abahafart.kotlin.template.domain.repository

import com.abahafart.kotlin.template.domain.service.PageRequest
import com.abahafart.kotlin.template.domain.service.RequestFilters
import com.abahafart.kotlin.template.domain.service.Results

interface GenericRepository<MODEL, REQUEST_ENTITY, FILTERS: RequestFilters> {

    suspend fun findBYId(id: Long): MODEL?
    suspend fun findAllWithFilters(pageRequest: PageRequest): Results<MODEL>
    suspend fun save(requestEntity: REQUEST_ENTITY): MODEL
    suspend fun update(model: MODEL): MODEL
    suspend fun delete(id: Long)

}