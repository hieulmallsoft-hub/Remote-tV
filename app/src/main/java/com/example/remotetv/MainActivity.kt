package com.example.remotetv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.remotetv.navigate.Navigation
import com.example.remotetv.ui.theme.RemoteTVTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            RemoteTVTheme {

                Navigation()

            }
        }
    }
}