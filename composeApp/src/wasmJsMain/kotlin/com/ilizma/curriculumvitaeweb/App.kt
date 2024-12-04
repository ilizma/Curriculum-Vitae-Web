package com.ilizma.curriculumvitaeweb

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilizma.curriculumvitaeapp.BuildKonfig.PLAY_STORE_URL
import kotlinx.coroutines.launch

@Composable
fun App() {
    MaterialTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        val uriHandler = LocalUriHandler.current

        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val androidContainerColor by animateColorAsState(
            if (isHovered) Color(0xFFa4c639) else Color.White
        )
        val androidContentColor = Color(0xFFa4c639)
        val iosContainerColor by animateColorAsState(
            if (isHovered) Color(0xFF007aff) else Color.White
        )
        val iosContentColor = Color(0xFF007aff)

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        fontSize = 24.sp,
                        text = "Download the Curriculum App"
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = androidContainerColor,
                            contentColor = androidContentColor,
                        ),
                        border = BorderStroke(
                            width = .4.dp,
                            color = androidContentColor
                        ),
                        onClick = { uriHandler.openUri(PLAY_STORE_URL) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "for Android",
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = iosContainerColor,
                            contentColor = iosContentColor,
                        ),
                        border = BorderStroke(
                            width = .4.dp,
                            color = iosContentColor
                        ),
                        onClick = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Coming soon!",
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneIphone,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "for iOS",
                        )
                    }
                }
            }
        }
    }
}