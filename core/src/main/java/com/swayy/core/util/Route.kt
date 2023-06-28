package com.swayy.core.util

class Route {
    companion object {
        const val HOME = "news"
        const val MORE = "more"
        const val LEAGUE = "league"
        const val TRANSFERS = "transfers"
        const val STATISTICS = "statistics"
        const val SETTINGS = "settings/?fromGame={fromGame}"
        const val MATCH_DETAILS = "match/{id}/{date}"
        const val NEWS_DETAIL = "news/{news}"
        const val TEST = "test"
    }
}