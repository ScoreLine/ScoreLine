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
import com.swayy.core_network.model.lineup.Coach
import com.swayy.core_network.model.lineup.StartXI
import com.swayy.core_network.model.lineup.Substitute
import com.swayy.core_network.model.lineup.Team

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
    fun fromScore(str: Score): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toScore(str: String): Score {
        return Gson().fromJson(str, object : TypeToken<Score>() {}.type)
    }

    @TypeConverter
    fun fromTeams(str: Teams): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toTeams(str: String): Teams {
        return Gson().fromJson(str, object : TypeToken<Teams>() {}.type)
    }

    @TypeConverter
    fun fromTeam(str: Team): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toTeam(str: String): Team {
        return Gson().fromJson(str, object : TypeToken<Team>() {}.type)
    }

    @TypeConverter
    fun fromCoach(str: Coach): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toCoach(str: String): Coach {
        return Gson().fromJson(str, object : TypeToken<Coach>() {}.type)
    }

    @TypeConverter
    fun fromLine(str: List<StartXI>): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toLine(str: String): List<StartXI> {
        return Gson().fromJson(str, object : TypeToken<List<StartXI>>() {}.type)
    }

    @TypeConverter
    fun fromSubstitute(str: List<Substitute>): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toSubstitute(str: String): List<Substitute> {
        return Gson().fromJson(str, object : TypeToken<List<Substitute>>() {}.type)
    }

}