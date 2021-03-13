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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        WelcomeScreenContent()
    }
}

@Composable
fun WelcomeScreenContent() {
    Box {
        Image(
            painter = painterResource(
                id = R.drawable.welcome_bg
            ),
            contentDescription = null
        )
        ConstraintLayoutContent()
    }
}

@Composable
@Preview(widthDp = 360, heightDp = 640, name = "Light theme")
fun WelcomeScreenPreview() {
    MyTheme {
        Scaffold(
            backgroundColor = MaterialTheme.colors.primary
        ) {
            WelcomeScreenContent()
        }
    }
}

@Composable
@Preview(widthDp = 360, heightDp = 640, name = "Dark theme")
fun WelcomeScreenPreviewDarkMode() {
    MyTheme(darkTheme = true) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.primary
        ) {
            WelcomeScreenContent()
        }
    }
}

@Composable
fun ContentColumn(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .firstBaselineToTop(24.dp)
                .lastBaselineToBottom(40.dp),
            text = "Beautiful home garden solutions",
            style = MaterialTheme.typography.subtitle1
        )
        Button(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            )
        ) {
            Text(
                text = "Create account",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onSecondary
            )
        }
        TextButton(
            modifier = Modifier
                .height(48.dp)
                .padding(top = 8.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onPrimary,
            )
        }
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (image, column) = createRefs()

        Image(
            modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.top, margin = 72.dp)
                start.linkTo(parent.start, margin = 88.dp)
            },
            painter = painterResource(
                id = R.drawable.welcome_illos
            ),
            contentDescription = null
        )
        ContentColumn(
            modifier = Modifier
                .constrainAs(column) {
                    top.linkTo(image.bottom, margin = 48.dp)
                }
        )
    }
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}

fun Modifier.lastBaselineToBottom(
    lastBaselineToBottom: Dp
) = Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    check(placeable[LastBaseline] != AlignmentLine.Unspecified)
    val lastBaseline = placeable[LastBaseline]

    val placeableY = placeable.height - lastBaseline
    val height = placeable.height + lastBaselineToBottom.roundToPx()
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}
