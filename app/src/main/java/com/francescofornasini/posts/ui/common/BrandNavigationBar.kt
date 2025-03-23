package com.francescofornasini.posts.ui.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.francescofornasini.posts.R
import com.francescofornasini.posts.ui.favorite.Favorite
import com.francescofornasini.posts.ui.search.Search

data class NavigationItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: Any
)

val navigationItems = listOf(
    NavigationItem(
        title = R.string.section_title_search,
        icon = Icons.Default.Search,
        route = Search
    ),
    NavigationItem(
        title = R.string.section_title_favorite,
        icon = Icons.Default.Star,
        route = Favorite
    )
)

@Composable
fun BrandNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar {
        navigationItems.forEach { navigationItem ->
            NavigationBarItem(
                selected = with(navBackStackEntry?.destination) {
                    this != null && hasRoute(navigationItem.route::class)
                },
                onClick = {
                    // we want to exit from the app on back from any section
                    navController.navigate(navigationItem.route) { popUpTo(0) }
                },
                icon = {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = stringResource(navigationItem.title)
                    )
                },
                label = { Text(stringResource(navigationItem.title)) },
            )
        }
    }
}