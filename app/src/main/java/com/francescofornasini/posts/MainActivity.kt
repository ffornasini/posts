package com.francescofornasini.posts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.francescofornasini.posts.ui.detail.Detail
import com.francescofornasini.posts.ui.detail.DetailScreen
import com.francescofornasini.posts.ui.favorite.Favorite
import com.francescofornasini.posts.ui.favorite.FavoriteScreen
import com.francescofornasini.posts.ui.search.Search
import com.francescofornasini.posts.ui.search.SearchScreen
import com.francescofornasini.posts.ui.theme.PostsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Search) {
                    composable<Search> {
                        SearchScreen()
                    }
                    composable<Favorite> {
                        FavoriteScreen()
                    }
                    composable<Detail> {
                        DetailScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PostsTheme {
        Greeting("Android")
    }
}