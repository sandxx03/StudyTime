package com.example.studytime.presentation.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.studytime.R

@Composable
fun SubjectImage(
    imageUri: Uri?,
    onImageUriChange: (Uri) -> Unit,
) {
    val context = LocalContext.current
    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { onImageUriChange(it) }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                launcher.launch("image/*")
            }
            .padding(16.dp) // Add padding to create space from the edges
    ) {
        if (imageUri != null) {
            // Image loaded with a placeholder
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(imageUri)
                        .scale(Scale.FILL)
                        .build()
                ),
                contentDescription = "Subject Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            // Placeholder when no image is selected
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray) // Background color for visibility
                    .padding(16.dp), // Padding inside the column
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_image), // Custom placeholder image
                    contentDescription = "Placeholder Image",
                    modifier = Modifier
                        .size(80.dp), // Fixed size for placeholder
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(8.dp)) // Space between image and text
                Text(
                    text = "Click to add image",
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}