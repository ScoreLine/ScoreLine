package com.swayy.core.domain.repository

import com.swayy.core.domain.model.WebMatch
import com.swayy.core.util.Resource

interface WebMatchRepository {

    suspend fun getWebMatches(url: String): Resource<List<WebMatch>>

}