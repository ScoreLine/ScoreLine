package com.swayy.core_database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.swayy.core_network.model.Fixtures.Fixture
import com.swayy.core_network.model.Fixtures.Goals
import com.swayy.core_network.model.Fixtures.League
import com.swayy.core_network.model.Fixtures.Score
import com.swayy.core_network.model.Fixtures.Teams
import com.swayy.core_network.model.events.Assist
import com.swayy.core_network.model.events.Player
import com.swayy.core_network.model.events.Time
import com.swayy.core_network.model.leagues.Country
import com.swayy.core_network.model.lineup.Coach
import com.swayy.core_network.model.lineup.StartXI
import com.swayy.core_network.model.lineup.Substitute
import com.swayy.core_network.model.lineup.Team
import com.swayy.core_network.model.standings.Standing
import com.swayy.core_network.model.topscorers.Statistic

@ProvidedTypeConverter
class Converters(private val gson : Gson) {

    @TypeConverter
    fun fromFixture(str: Fixture): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toFixture(str: String): Fixture {
        return Gson().fromJson(str, object : TypeToken<Fixture>() {}.type)
    }

    @TypeConverter
    fun fromGoals(str: Goals): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toGoals(str: String): Goals {
        return Gson().fromJson(str, object : TypeToken<Goals>() {}.type)
    }

    @TypeConverter
    fun fromLeague(str: League): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toLeague(str: String): League {
        return Gson().fromJson(str, object : TypeToken<League>() {}.type)
    }

    @TypeConverter
    fun fromScore(df: Score): String {
        return Gson().toJson(df)
    }

    @TypeConverter
    fun toScore(vo: String): Score {
        return Gson().fromJson(vo, object : TypeToken<Score>() {}.type)
    }

    @TypeConverter
    fun fromTeams(ke: Teams): String {
        return Gson().toJson(ke)
    }

    @TypeConverter
    fun toTeams(you: String): Teams {
        return Gson().fromJson(you, object : TypeToken<Teams>() {}.type)
    }

    @TypeConverter
    fun fromTeam(me: Team?): String? {
        return Gson().toJson(me)
    }

    @TypeConverter
    fun toTeam(no: String?): Team? {
        return Gson().fromJson(no, object : TypeToken<Team?>() {}.type)
    }

    @TypeConverter
    fun fromCoach(aa: Coach?): String? {
        return Gson().toJson(aa)
    }

    @TypeConverter
    fun toCoach(gg: String?): Coach? {
        return Gson().fromJson(gg, object : TypeToken<Coach?>() {}.type)
    }

    @TypeConverter
    fun fromLine(sa: List<StartXI?>): String? {
        return Gson().toJson(sa)
    }

    @TypeConverter
    fun toLine(da: String?): List<StartXI?> {
        return Gson().fromJson(da, object : TypeToken<List<StartXI?>>() {}.type)
    }

    @TypeConverter
    fun fromSubstitute(subone: List<Substitute?>): String? {
        return Gson().toJson(subone)
    }

    @TypeConverter
    fun toSubstitute(sub: String?): List<Substitute?> {
        return Gson().fromJson(sub, object : TypeToken<List<Substitute?>>() {}.type)
    }

    @TypeConverter
    fun fromAssist(str: Assist): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toAssist(str: String): Assist {
        return Gson().fromJson(str, object : TypeToken<Assist>() {}.type)
    }

    @TypeConverter
    fun fromPlaye(str: Player): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toPlaye(str: String): Player {
        return Gson().fromJson(str, object : TypeToken<Player>() {}.type)
    }

    @TypeConverter
    fun fromT(str: com.swayy.core_network.model.events.Team): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toT(str: String): com.swayy.core_network.model.events.Team {
        return Gson().fromJson(str, object : TypeToken<com.swayy.core_network.model.events.Team>() {}.type)
    }

    @TypeConverter
    fun fromTime(str: Time): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toTime(str: String): Time {
        return Gson().fromJson(str, object : TypeToken<Time>() {}.type)
    }

    @TypeConverter
    fun fromStandingsJson(json: String): List<List<Standing>> {
        val listType = object : TypeToken<List<List<Standing>>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toStandingsJson(standings: List<List<Standing>>): String {
        return gson.toJson(standings)
    }


    @TypeConverter
    fun fromCountry(country: Country): String {
        return gson.toJson(country)
    }

    @TypeConverter
    fun toCountry(countryString: String): Country {
        return gson.fromJson(countryString, Country::class.java)
    }

    @TypeConverter
    fun fromLeagueJson(leagues: com.swayy.core_network.model.leagues.League): String {
        return gson.toJson(leagues)
    }

    @TypeConverter
    fun toLeagueJson(leagueString: String): com.swayy.core_network.model.leagues.League {
        return gson.fromJson(leagueString, com.swayy.core_network.model.leagues.League::class.java)
    }

    @TypeConverter
    fun fromPlayerTopScorer(scorerPlayer: com.swayy.core_network.model.topscorers.Player): String {
        return gson.toJson(scorerPlayer)
    }

    @TypeConverter
    fun toPlayerTopScorer(playerJson: String): com.swayy.core_network.model.topscorers.Player {
        return gson.fromJson(playerJson, com.swayy.core_network.model.topscorers.Player::class.java)
    }

    @TypeConverter
    fun fromPlayerAssist(assistPlayer: com.swayy.core_network.model.assists.Player): String {
        return gson.toJson(assistPlayer)
    }

    @TypeConverter
    fun toPlayerAssist(playerJsonAssit: String): com.swayy.core_network.model.assists.Player {
        return gson.fromJson(playerJsonAssit, com.swayy.core_network.model.assists.Player::class.java)
    }

    @TypeConverter
    fun fromStatisticsScorer(statisticsScorer: List<Statistic>): String {
        return gson.toJson(statisticsScorer)
    }

    @TypeConverter
    fun toStatisticsScorer(statisticsJsonScorer: String): List<Statistic> {
        val statisticType = object : TypeToken<List<Statistic>>() {}.type
        return gson.fromJson(statisticsJsonScorer, statisticType)
    }

    @TypeConverter
    fun fromStatisticsAssist(statisticsAssist: List<com.swayy.core_network.model.assists.Statistic>): String {
        return gson.toJson(statisticsAssist)
    }

    @TypeConverter
    fun toStatisticsAssist(statisticsJsonAssist: String): List<com.swayy.core_network.model.assists.Statistic> {
        val statisticType = object : TypeToken<List<com.swayy.core_network.model.assists.Statistic>>() {}.type
        return gson.fromJson(statisticsJsonAssist, statisticType)
    }

    @TypeConverter
    fun fromStatisticsInStats(statisticsMatchJson: List<com.swayy.core_network.model.stats.Statistic>): String {
        return gson.toJson(statisticsMatchJson)
    }

    @TypeConverter
    fun toStatisticsInStats(statisticsJsonFromStats: String): List<com.swayy.core_network.model.stats.Statistic> {
        val statisticType = object : TypeToken<List<com.swayy.core_network.model.stats.Statistic>>() {}.type
        return gson.fromJson(statisticsJsonFromStats, statisticType)
    }

    @TypeConverter
    fun fromStatsTeam(statsTeam: com.swayy.core_network.model.stats.Team): String {
        return gson.toJson(statsTeam)
    }

    @TypeConverter
    fun toStatsTeam(statsTeamString: String): com.swayy.core_network.model.stats.Team {
        return gson.fromJson(statsTeamString, com.swayy.core_network.model.stats.Team::class.java)
    }
}