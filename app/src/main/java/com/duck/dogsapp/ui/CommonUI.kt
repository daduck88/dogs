package com.duck.dogsapp.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TryAgain() {
    LazyColumn {
        items(1) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .defaultMinSize(minHeight = 200.dp)
                    .padding(8.dp),
                text = "Use pull to refresh to try again",
                style = MaterialTheme.typography.h6
            )
        }
    }

}

@Composable
fun Emtpy() {
    LazyColumn {
        items(1) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .defaultMinSize(minHeight = 200.dp)
            )
        }
    }

}