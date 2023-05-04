package com.abahafart.kotlin.template.infra.repository

import com.abahafart.kotlin.template.domain.repository.GenericRepository
import com.abahafart.kotlin.template.domain.service.PageRequest
import com.abahafart.kotlin.template.domain.service.RequestFilters
import com.abahafart.kotlin.template.domain.service.Results
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.from
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor

interface GeneralCoroutineCrudRepository<ENTITY_PERSISTENCE> : CoroutineCrudRepository<ENTITY_PERSISTENCE, Long>,
        ReactiveQueryByExampleExecutor<ENTITY_PERSISTENCE>

abstract class GeneralRepositoryImpl<ENTITY_PERSISTENCE, MODEL : Any, REQUEST_ENTITY, FILTERS: RequestFilters>(
        private val innerRepository: GeneralCoroutineCrudRepository<ENTITY_PERSISTENCE>,
        private val mapper: GenericMapper<MODEL, REQUEST_ENTITY, ENTITY_PERSISTENCE>,
        private val r2dbcEntityTemplate: R2dbcEntityTemplate
): GenericRepository<MODEL, REQUEST_ENTITY, FILTERS> {

        abstract fun getEntityClass(): Class<ENTITY_PERSISTENCE>
        override suspend fun findBYId(id: Long): MODEL? {
                return innerRepository.findById(id)?.let { mapper.fromEntityPersistenceToModel(it) }
        }

        override suspend fun findAllWithFilters(pageRequest: PageRequest): Results<MODEL> = coroutineScope {
                val filtersFound = pageRequest.filters?.addFilters()?.map { where(it.key).`is`(it.value) } ?: emptyList()
                val query = Query.query(from(filtersFound)).offset(pageRequest.offset).limit(pageRequest.limit)
                val itemsFound =
                        async { r2dbcEntityTemplate.select(query, getEntityClass()).map { mapper.fromEntityPersistenceToModel(it) }.asFlow().toList() }
                val count =  async { r2dbcEntityTemplate.count(query, getEntityClass()).awaitFirstOrNull() ?: 0 }
                Results(itemsFound.await(), pageRequest.offset, pageRequest.limit, pageRequest.sortedBy, count.await())
        }

        override suspend fun save(requestEntity: REQUEST_ENTITY): MODEL {
                val entity = mapper.fromEntityRequestToEntity(requestEntity)
                return innerRepository.save(entity).let { mapper.fromEntityPersistenceToModel(it) }
        }

        override suspend fun update(model: MODEL): MODEL {
                val entity = mapper.fromModelToEntity(model)
                return innerRepository.save(entity).let { mapper.fromEntityPersistenceToModel(it) }
        }

        override suspend fun delete(id: Long) {
                innerRepository.deleteById(id)
        }
}