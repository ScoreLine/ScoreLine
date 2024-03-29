package com.swayy.core.util

class Route {
    companion object {
        const val HOME = "news"
        const val MORE = "more"
        const val STANDINGS = "standings"
        const val FAVOURITES = "favourites"
        const val STATISTICS = "statistics"
        const val SETTINGS = "settings/?fromGame={fromGame}"
//        const val MATCH_DETAILS = "match/{id}/{date}"
        const val MATCH_INFO = "match/{matchLink}"
        const val NEWS_DETAIL = "news/{news}"
        const val LEAGUE_DETAIL = "league/{league}"
        const val SOCCER_DETAIL = "soccer/{league}/{games}/{teams}/{logo}/{flag}/{link}"
    }
}