package de.neugelb.domain.entity.response

data class Movies(
    var page: Int? = null,
    var results: List<Result>? = null,
    var total_pages: Int? = null,
    var total_results: Int? = null
)