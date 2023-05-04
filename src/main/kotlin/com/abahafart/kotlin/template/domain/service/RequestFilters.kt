package com.abahafart.kotlin.template.domain.service

interface RequestFilters {
    fun addFilters(): Map<String, Any>
}

data class PageRequest(
    val offset: Long,
    val limit: Int,
    val sortedBy: String? = null,
    val filters: RequestFilters? = null
)

class Results<MODEL>(
    val items: List<MODEL>, val offset: Long, val limit: Int, val sortedBy: String? = null, val count: Long
)