package com.swayy.core.data.repository

import android.util.Log
import com.swayy.core.data.WebData.fetchMatches
import com.swayy.core.data.mapper.toWebMatch
import com.swayy.core.domain.model.WebMatch
import com.swayy.core.domain.model.WebMatchResponse
import com.swayy.core.domain.repository.WebMatchRepository
import com.swayy.core.util.Resource
import com.swayy.core.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class WebMatchRepositoryImpl(

) : WebMatchRepository {

    override suspend fun getWebMatches(url: String): Resource<List<WebMatch>> {
        return safeApiCall(Dispatchers.IO) {
            val response = fetchMatches(url = url)
            response.map { it.toWebMatch() }
        }
    }

}



