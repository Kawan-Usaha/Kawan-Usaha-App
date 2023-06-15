package com.jetpack.kawanusaha.ui.pages.main

import android.app.AppComponentFactory
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.AppOpsManagerCompat
import com.jetpack.kawanusaha.R

@Composable
fun SettingsScreen(
    navBack: () -> Unit,

) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(R.string.settings_screen))
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        },
        modifier = Modifier.safeDrawingPadding()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            item{
                Card(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "Language",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LocaleDropDownMenu() {
    val localeOptions = mapOf(
        R.string.en to "en",
        R.string.indo to "in"
    ).mapKeys { stringResource(it.key) }

    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = stringResource(R.string.language),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            localeOptions.keys.forEach{selectionLocale ->
                DropdownMenuItem(onClick = {
                    expanded = false
                }) {
                }
            }
        }
    }
}