package com.omsharma.grubio

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omsharma.grubio.data.FoodApi
import com.omsharma.grubio.ui.features.auth.AuthScreen
import com.omsharma.grubio.ui.features.auth.login.LoginScreen
import com.omsharma.grubio.ui.features.auth.signup.SignUpScreen
import com.omsharma.grubio.ui.navigation.AuthScreen
import com.omsharma.grubio.ui.navigation.Home
import com.omsharma.grubio.ui.navigation.Login
import com.omsharma.grubio.ui.navigation.Signup
import com.omsharma.grubio.ui.theme.GrubioTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var foodApi: FoodApi
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GrubioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AuthScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Signup> {
                            SignUpScreen(navController)
                        }
                        composable<AuthScreen> {
                            AuthScreen(navController)
                        }
                        composable<Login> {
                            LoginScreen(navController)
                        }
                        composable<Home> {
                            Box(modifier = Modifier.fillMaxSize().background(Color.Red))
                        }
                    }
                }
            }
        }

        if(::foodApi.isInitialized) {
            Log.d("MainActivity", "FoodApi is initialized")
        }
    }
}