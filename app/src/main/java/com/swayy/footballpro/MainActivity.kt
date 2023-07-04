package com.swayy.footballpro

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.standings.presentation.leagues.LeagueScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.swayy.more.MoreScreen
import com.swayy.core.core.PreferencesConstants
import com.swayy.core.data.datastore.ThemeSettingsManager
import com.swayy.more.settings.SettingsScreen
import com.swayy.footballpro.ui.components.animatedComposable
import com.swayy.compose_ui.theme.AppTheme
import com.swayy.compose_ui.theme.FootballProTheme
import com.swayy.core.util.Route
import com.swayy.favourites.presentation.FavouritesScreen
import com.swayy.matches.presentation.MatchesScreen
import com.swayy.matches.presentation.match_details.MatchDetailsScreen
import com.swayy.news.NewsScreen
import com.swayy.news.presentation.NewsViewModel
import com.swayy.news.presentation.TrendingNewsScreen
import com.swayy.news.presentation.components.NewsDetail
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val mainViewModel: MainActivityViewModel = hiltViewModel()

            val dynamicColors by mainViewModel.dc.collectAsStateWithLifecycle(isSystemInDarkTheme())
            val darkTheme by mainViewModel.darkTheme.collectAsStateWithLifecycle(PreferencesConstants.DEFAULT_DARK_THEME)
            val amoledBlack by mainViewModel.amoledBlack.collectAsStateWithLifecycle(PreferencesConstants.DEFAULT_AMOLED_BLACK)
            val currentTheme by mainViewModel.currentTheme.collectAsStateWithLifecycle(PreferencesConstants.DEFAULT_SELECTED_THEME)

            FootballProTheme(
                darkTheme = when (darkTheme) {
                    1 -> false
                    2 -> true
                    else -> isSystemInDarkTheme()
                },
                dynamicColor = dynamicColors,
                amoled = amoledBlack,
                appTheme = when (currentTheme) {
                    PreferencesConstants.GREEN_THEME_KEY -> AppTheme.Green
                    PreferencesConstants.BLUE_THEME_KEY -> AppTheme.Blue
                    PreferencesConstants.PEACH_THEME_KEY -> AppTheme.Peach
                    PreferencesConstants.YELLOW_THEME_KEY -> AppTheme.Yellow
                    PreferencesConstants.LAVENDER_THEME_KEY -> AppTheme.Lavender
                    PreferencesConstants.BLACK_AND_WHITE_THEME_KEY -> AppTheme.BlackAndWhite
                    else -> AppTheme.Green
                }
            ) {

                val navController = rememberAnimatedNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                var bottomBarState by rememberSaveable { mutableStateOf(false) }

                LaunchedEffect(navBackStackEntry) {
                    bottomBarState = when (navBackStackEntry?.destination?.route) {
                        Route.STATISTICS, Route.HOME, Route.STANDINGS, Route.FAVOURITES, Route.MORE -> true
                        else -> false
                    }
                }
                CompositionLocalProvider() {
                    Scaffold(
                        bottomBar = {
                            NavigationBar(
                                navController = navController,
                                bottomBarState = bottomBarState
                            )
                        },
                        contentWindowInsets = WindowInsets(0.dp)
                    ) { paddingValues ->
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = Route.STATISTICS,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            animatedComposable(Route.HOME) {
                                NewsScreen(
                                    navigateNewsDetails = { news ->
                                        navController.navigate(
                                            "news/${news}"
                                        )
                                    }
                                )
                            }

                            animatedComposable(Route.MORE) {
                                MoreScreen(
                                    navigateSettings = { navController.navigate("settings/?fromGame=false") }
                                )
                            }

                            animatedComposable(Route.STANDINGS) {
                                LeagueScreen()
                            }

                            animatedComposable(Route.FAVOURITES) {
                                FavouritesScreen()
                            }

                            animatedComposable(Route.STATISTICS) {
                                MatchesScreen(
                                    navigateMatchDetails = { id, date ->
                                        navController.navigate(
                                            "match/${id}/$date"
                                        )
                                    }
                                )
                            }

                            animatedComposable(
                                route = Route.SETTINGS,
                                arguments = listOf(navArgument("fromGame") {
                                    defaultValue = false
                                    type = NavType.BoolType
                                })
                            ) {
                                SettingsScreen(
                                    navigateBack = { navController.popBackStack() },
                                    hiltViewModel(),
                                    navigateBoardSettings = { navController.navigate("settings_board_theme") }
                                )
                            }

                            animatedComposable(
                                route = Route.NEWS_DETAIL,
                                arguments = listOf(
                                    navArgument("news") { type = NavType.StringType }
                                )
                            ){
                                val arguments = requireNotNull(it.arguments)
                                val news = arguments.getString("news")
                                 NewsDetail(
                                     navigateBack = { navController.popBackStack() },
                                     news = news!!,
                                 )
                            }

                            animatedComposable(
                                route = Route.MATCH_DETAILS,
                                arguments = listOf(
                                    navArgument("id") { type = NavType.IntType },
                                    navArgument("date") { type = NavType.StringType },
                                )
                            ) {
                                val arguments = requireNotNull(it.arguments)
                                val id = arguments.getInt("id")
                                val date = arguments.getString("date")
                                if (date != null) {
                                    MatchDetailsScreen(
                                        navigateBack = { navController.popBackStack() },
                                        id = id,
                                        date = date
                                    )
                                }

                            }

                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationBar(
    navController: NavController,
    bottomBarState: Boolean
) {
    var selectedScreen by remember { mutableStateOf(Route.STATISTICS) }
    val navBarScreens = listOf(
        Pair(Route.STATISTICS, R.string.Statistics),
        Pair(Route.HOME, R.string.Home),
        Pair(Route.STANDINGS, R.string.Standings),
        Pair(Route.FAVOURITES, R.string.Favourites),
        Pair(Route.MORE, R.string.More),
    )
    val navBarIcons = listOf(
        painterResource(com.swayy.core.R.drawable.baseline_sports_soccer_24),
        painterResource(com.swayy.core.R.drawable.baseline_newspaper_24),
        painterResource(com.swayy.core.R.drawable.baseline_leaderboard_24),
        painterResource(com.swayy.core.R.drawable.baseline_star_border_24),
        painterResource(com.swayy.core.R.drawable.ic_round_more_horiz_24)
    )
    AnimatedContent(
        targetState = bottomBarState
    ) { visible ->
        if (visible) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            LaunchedEffect(currentDestination) {
                currentDestination?.let {
                    selectedScreen = it.route ?: ""
                }
            }

            androidx.compose.material3.NavigationBar(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary,
                tonalElevation = 4.dp,
                modifier = Modifier.shadow(10.dp)
            ) {
                navBarScreens.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        icon = {
                            Icon(
                                painter = navBarIcons[index],
                                contentDescription = null,
                            )
                        },
                        selected = selectedScreen == item.first,
                        label = {
                            Text(
                                text = stringResource(item.second),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        onClick = {
                            navController.navigate(item.first) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    themeSettingsManager: ThemeSettingsManager
) : ViewModel() {

    val dc = themeSettingsManager.dynamicColors
    val darkTheme = themeSettingsManager.darkTheme
    val amoledBlack = themeSettingsManager.amoledBlack
    val currentTheme = themeSettingsManager.currentTheme
}