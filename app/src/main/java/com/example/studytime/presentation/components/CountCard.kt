package com.example.studytime.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountCard(
    // parameters for the text in the countcard
    modifier: Modifier = Modifier,
    headingText: String,
    count: String
){
    ElevatedCard(modifier = modifier) {
        // place texts above and below in vertical manner
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // header of the countcard
            Text(
                text = headingText,
                style = MaterialTheme.typography.labelSmall
            )

            // space between header and the count
            Spacer(modifier = Modifier.height(5.dp))

            // count of the countcard
            Text(
                text = count,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 30.sp)
            )
        }

    }
}