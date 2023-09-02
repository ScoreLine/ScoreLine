package com.swayy.core.data.WebData

import android.util.Log
import com.swayy.core.domain.model.LeagueStandingResponse
import com.swayy.core.domain.model.WebMatchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

suspend fun fetchMatches(url: String): List<WebMatchResponse> {
    val matchList = mutableListOf<WebMatchResponse>()
    withContext(Dispatchers.IO) {
        try {
            val doc: Document = Jsoup.connect(url).get()
            val matchElements: Elements = doc.select(".match-link")

            for (matchElement in matchElements) {
                val homeTeam = matchElement.select(".team_left .name").text()

                if (homeTeam.isBlank()) {
                    continue
                }

                val awayTeam = matchElement.select(".team_right .name").text()
                val homeTeamImageUrl = matchElement.select("img[alt='${homeTeam}']").attr("src")
                val awayTeamImageUrl = matchElement.select("img[alt='${awayTeam}']").attr("src")
                val scoreElement = matchElement.select("div.marker").text()
                val scoreParts = scoreElement.split("-")
                val homeScore = scoreParts.getOrElse(0) { "0" }
                val awayScore = scoreParts.getOrElse(1) { "0" }
                val matchTime = matchElement.attr("starttime")
                val round = matchElement.select(".middle-info").text()
                val matchLink = matchElement.attr("href")

                val matchStatusElement = matchElement.select(".match-status-label .tag").text()
                val matchProgress = if (matchStatusElement.contains("'")) {
                    matchStatusElement
                } else {
                    matchStatusElement.replace("'", "").toLowerCase()
                }

                var league = ""
                var leagueImage = ""
                var leagueLink = ""

                // Find the correct league for the current match
                val panelHeadElement = matchElement.parents().select(".panel-head").first()
                if (panelHeadElement != null) {
                    league = panelHeadElement.select("a[data-cy=competitionName] span.va-m")
                        .text()
                    leagueImage = panelHeadElement.select("img.comp-img").attr("src")
                    leagueLink =  panelHeadElement.select("a[data-cy=competitionDetail]").attr("href")
                }

                matchList.add(
                    WebMatchResponse(
                        league,
                        homeTeam,
                        awayTeam,
                        homeTeamImageUrl,
                        awayTeamImageUrl,
                        matchTime,
                        round,
                        scoreElement,
                        scoreElement,
                        matchLink,
                        matchProgress = matchProgress,
                        link = matchLink,
                        leagueImage = leagueImage,
                        leagueLink = leagueLink
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return matchList
}


 suspend fun scrapePremierLeagueStandings(league: String): List<LeagueStandingResponse> {
    val standings = mutableListOf<LeagueStandingResponse>()
    val document: Document = withContext(Dispatchers.IO) {
        Jsoup.connect(league+"/table").get()
    }

    val rows = document.select("tr.row-body")

    for (row in rows) {
        val positionText = row.select("td.number-box").text()
        val position = if (positionText.isNotEmpty()) positionText.toInt() else 0

        val logoUrl = row.select("td.td-shield img").attr("src")
        val teamName = row.select("td.name span.team-name").text()

        val pointsText = row.select("td.green.bold").text()
        val points = if (pointsText.isNotEmpty()) pointsText.toInt() else 0

        val winsAndDraws = row.select("td.name span.bg-match-res")
        val wins = winsAndDraws.count { it.hasClass("win") }
        val draws = winsAndDraws.count { it.hasClass("draw") }

        val lossesElement = winsAndDraws.firstOrNull()?.nextElementSibling()
        val lossesText = lossesElement?.text() ?: ""
        val losses = parseNumericValue(lossesText)

        val goalsForElement = lossesElement?.nextElementSibling()
        val goalsForText = goalsForElement?.text() ?: ""
        val goalsFor = parseNumericValue(goalsForText)

        val goalsAgainstElement = goalsForElement?.nextElementSibling()
        val goalsAgainstText = goalsAgainstElement?.text() ?: ""
        val goalsAgainst = parseNumericValue(goalsAgainstText)

        val goalDifferenceElement = goalsAgainstElement?.nextElementSibling()
        val goalDifferenceText = goalDifferenceElement?.text() ?: ""
        val goalDifference = parseNumericValue(goalDifferenceText)

        standings.add(
            LeagueStandingResponse(
                position,
                logoUrl,
                teamName,
                points,
                wins + draws + losses,
                wins,
                draws,
                losses,
                goalsFor,
                goalsAgainst,
                goalDifference
            )
        )
    }

    return standings
}

private fun parseNumericValue(text: String): Int {
    val numericPattern = Regex("\\d+")
    val match = numericPattern.find(text)
    return match?.value?.toInt() ?: 0
}