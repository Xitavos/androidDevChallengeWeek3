/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun HomeScreen() {

    val items = listOf(
        Screen.Home,
        Screen.Favorites,
        Screen.Profile,
        Screen.Cart
    )

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier.height(56.dp),
                elevation = 16.dp,
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null, Modifier.size(24.dp)) },
                        label = { Text(screen.name) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) { HomeScreenContent() }
            composable(Screen.Favorites.route) { HomeScreenContent() }
            composable(Screen.Profile.route) { HomeScreenContent() }
            composable(Screen.Cart.route) { HomeScreenContent() }
        }
    }
}

@Composable
fun HomeScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
    ) {
        SearchBar()
        Text(
            modifier = Modifier
                .paddingFromBaseline(top = 32.dp),
            text = "Browse themes",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        ThemesRow()
    }
}

@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = {
            Text(
                text = "Search",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
        },
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            cursorColor = MaterialTheme.colors.onBackground,
            focusedBorderColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.onBackground
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        }
    )
}

@Composable
fun ThemesRow() {
    val themes = listOf(
        Theme.DesertChic,
        Theme.TinyTerrariums,
        Theme.JungleVibes,
        Theme.EasyCare,
        Theme.Statements
    )

    LazyRow(
        modifier = Modifier
            .height(136.dp)
            .fillMaxWidth(),
        content = {
            items(themes) {
                ThemeCard(theme = it)
            }
        },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    )
}

@Composable
fun ThemeCard(theme: Theme) {
    Card(
        modifier = Modifier
            .size(136.dp),
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 1.dp
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                painter = painterResource(
                    id = theme.resourceId
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    text = theme.text,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                )
            }
        }
    }
}

@Composable
@Preview(widthDp = 360, heightDp = 640, name = "Light theme")
fun HomeScreenPreview() {
    MyTheme {
        Scaffold {
            HomeScreenContent()
        }
    }
}

@Composable
@Preview(widthDp = 360, heightDp = 640, name = "Dark theme")
fun HomeScreenPreviewDarkMode() {
    MyTheme(darkTheme = true) {
        Scaffold {
            HomeScreenContent()
        }
    }
}

@Composable
@Preview
fun SearchBarPreview() {
    SearchBar()
}

@Composable
@Preview
fun ThemeCardPreview() {
    MyTheme {
        ThemeCard(theme = Theme.DesertChic)
    }
}

@Composable
@Preview(showBackground = false, showSystemUi = false)
fun ThemeCardPreviewDarkTheme() {
    MyTheme(darkTheme = true) {
        ThemeCard(theme = Theme.DesertChic)
    }
}

sealed class Screen(val route: String, val name: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Favorites : Screen("favorites", "Favorites", Icons.Filled.FavoriteBorder)
    object Profile : Screen("profile", "Profile", Icons.Filled.AccountCircle)
    object Cart : Screen("cart", "Cart", Icons.Filled.ShoppingCart)
}

sealed class Theme(@DrawableRes val resourceId: Int, val text: String) {
    object DesertChic : Theme(
        resourceId = R.drawable.desert_chic,
        text = "Desert chic"
    )

    object TinyTerrariums : Theme(
        resourceId = R.drawable.tiny_terrariums,
        text = "Tiny terrariums"
    )

    object JungleVibes : Theme(
        resourceId = R.drawable.jungle_vibes,
        text = "Jungle vibes"
    )

    object EasyCare : Theme(
        resourceId = R.drawable.easy_care,
        text = "Easy care"
    )

    object Statements : Theme(
        resourceId = R.drawable.statements,
        text = "Statements"
    )
}
