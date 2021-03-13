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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun LoginScreen(navController: NavController) {
    Scaffold {
        LoginScreenContent { navController.navigate("") }
    }
}

@Composable
fun LoginScreenContent(onLogInClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val termsOfUseString = buildAnnotatedString {
            val str =
                "By clicking below, you agree to our Terms of Use and consent to our Privacy Policy."
            append(str)
            addStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline
                ),
                start = 36, end = 48
            )
            addStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline
                ),
                start = 68, end = 82
            )
        }
        Text(
            modifier = Modifier.paddingFromBaseline(top = 184.dp),
            text = "Log in with email",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
        )
        LoginTextField(
            hint = "Email address",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        LoginTextField(
            // modifier = Modifier
            //     .padding(top = 8.dp), // TextField already seems to have padding
            hint = "Password (8+ characters)",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
        Text(
            modifier = Modifier
                .paddingFromBaseline(top = 24.dp, bottom = 16.dp),
            text = termsOfUseString,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
        )
        ThemedButton(
            text = "Log in",
            onClick = onLogInClicked
        )
    }
}

@Composable
@Preview(widthDp = 360, heightDp = 640, name = "Light theme")
fun LoginScreenPreview() {
    MyTheme {
        Scaffold {
            LoginScreenContent {}
        }
    }
}

@Composable
@Preview(widthDp = 360, heightDp = 640, name = "Dark theme")
fun LoginScreenPreviewDarkMode() {
    MyTheme(darkTheme = true) {
        Scaffold {
            LoginScreenContent {}
        }
    }
}

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    hint: String,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = {
            Text(
                text = hint,
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
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = true
    )
}

@Composable
@Preview
fun LoginTextFieldPreview() {
    LoginTextField(
        hint = "Hint text",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}
