package com.example.remotetv.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.remotetv.ui.screen.connect.ConnectTvScreen
import com.example.remotetv.ui.screen.onboarding.OnboardingScreen
import com.example.remotetv.ui.screen.pairing.PairingScreen
import com.example.remotetv.ui.screen.remote.ConnectedScreen
import com.example.remotetv.ui.screen.remote.RemoteScreen
import com.example.remotetv.ui.screen.voice.VoiceControlScreen
import com.example.remotetv.ui.screen.apps.AppsScreen
import com.example.remotetv.ui.screen.register.CreateAccountScreen
import com.example.remotetv.ui.screen.select.SelectTvScreen
import com.example.remotetv.ui.screen.splash.SplashScreen
import com.example.remotetv.ui.theme.screen.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("onboarding") {
            OnboardingScreen(
                onFinish = {
                    navController.navigate("connect_tv")
                }
            )
        }

        composable("connect_tv") {
            ConnectTvScreen(
                onContinueClick = {
                    navController.navigate("select_tv")
                },
                onSelectTvClick = {
                    navController.navigate("select_tv")
                }
            )
        }

        composable("select_tv") {
            SelectTvScreen(
                onTvSelected = {
                    navController.navigate("pairing")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("register") {
            CreateAccountScreen(
                onCloseClick = {
                    navController.popBackStack()
                },
                onContinueClick = {
                    navController.navigate("pairing")
                },
                onLoginClick = {
                    navController.navigate("home")
                }
            )
        }

        composable("pairing") {
            PairingScreen(
                onConnected = { navController.navigate("connected") },
                onCancel = { navController.popBackStack() }
            )
        }

        composable("connected") {
            ConnectedScreen(
                onStart = { navController.navigate("remote") },
                onOtherTv = { navController.popBackStack() }
            )
        }

        composable("remote") { RemoteScreen(onVoiceClick = { navController.navigate("voice") }, onAppsClick = { navController.navigate("apps") }) }
        composable("voice") { VoiceControlScreen(onBack = { navController.popBackStack() }) }
        composable("apps") { AppsScreen(onBack = { navController.popBackStack() }, onRemote = { navController.navigate("remote") }, onVoice = { navController.navigate("voice") }) }

        composable("home") {
            HomeScreen()
        }
    }
}
