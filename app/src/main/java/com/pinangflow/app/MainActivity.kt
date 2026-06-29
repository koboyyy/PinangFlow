package com.pinangflow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.pinangflow.app.presentation.theme.PinangFlowTheme
import com.pinangflow.app.presentation.ui.NavigationRoutes
import com.pinangflow.app.presentation.ui.components.BottomNavBar
import com.pinangflow.app.presentation.ui.components.bottomNavItems
import com.pinangflow.app.presentation.ui.screens.*
import com.pinangflow.app.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinangFlowTheme {
                PinangFlowMainScreen()
            }
        }
    }
}

@Composable
fun PinangFlowMainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Always start with SPLASH
    val startDestination = NavigationRoutes.SPLASH

    val shouldShowBottomBar = currentRoute in bottomNavItems.map { it.route }
    var showBottomBar by remember { mutableStateOf(false) }

    LaunchedEffect(shouldShowBottomBar) {
        if (shouldShowBottomBar) {
            // Tunggu transisi layar selesai (sekitar 300-400ms) baru munculkan bottom bar
            kotlinx.coroutines.delay(400)
            showBottomBar = true
        } else {
            showBottomBar = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(NavigationRoutes.BERANDA) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // === SPLASH SCREEN ===
            composable(NavigationRoutes.SPLASH) {
                SplashScreen(
                    onNavigateNext = {
                        val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
                        val nextRoute = if (isLoggedIn) NavigationRoutes.BERANDA else NavigationRoutes.LOGIN
                        navController.navigate(nextRoute) {
                            popUpTo(NavigationRoutes.SPLASH) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            // === AUTH SCREENS ===
            composable(NavigationRoutes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(NavigationRoutes.BERANDA) {
                            popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(NavigationRoutes.REGISTER)
                    }
                )
            }

            composable(NavigationRoutes.REGISTER) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(NavigationRoutes.BERANDA) {
                            popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigateUp()
                    }
                )
            }

            // === MAIN TABS ===
            composable(NavigationRoutes.BERANDA) {
                DashboardPengepulScreen(
                    onNavigateToKalkulator = {
                        navController.navigate(NavigationRoutes.TIMBANG_GRADING)
                    },
                    onNavigateToAllTransactions = {
                        navController.navigate(NavigationRoutes.TRANSAKSI_ALL)
                    },
                    onNavigateToProfil = {
                        navController.navigate(NavigationRoutes.PROFIL) {
                            popUpTo(NavigationRoutes.BERANDA) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToEkspor = {
                        navController.navigate(NavigationRoutes.PENJUALAN_EKSPOR)
                    }
                )
            }

            composable(NavigationRoutes.DATA_PETANI) {
                DataPetaniScreen(
                    onNavigateToProfile = { petaniId ->
                        navController.navigate(NavigationRoutes.profilPetani(petaniId))
                    },
                    onNavigateToRegistration = {
                        navController.navigate(NavigationRoutes.REGISTRASI_PETANI)
                    }
                )
            }

            composable(NavigationRoutes.MANAJEMEN_GUDANG) {
                ManajemenGudangScreen(
                    onNavigateToDetail = { batchId ->
                        navController.navigate(NavigationRoutes.gudangDetail(batchId))
                    },
                    onBackClick = { navController.navigateUp() }
                )
            }

            composable(NavigationRoutes.LAPORAN) {
                LaporanScreen()
            }

            // === DETAIL SCREENS ===
            composable(
                route = NavigationRoutes.PROFIL_PETANI,
                arguments = listOf(navArgument("petaniId") { type = NavType.StringType })
            ) { backStackEntry ->
                val petaniId = backStackEntry.arguments?.getString("petaniId") ?: ""
                ProfilPetaniScreen(
                    petaniId = petaniId,
                    onBackClick = { navController.navigateUp() }
                )
            }

            composable(
                route = NavigationRoutes.GUDANG_DETAIL,
                arguments = listOf(navArgument("batchId") { type = NavType.StringType })
            ) { backStackEntry ->
                val batchId = backStackEntry.arguments?.getString("batchId") ?: ""
                GudangDetailScreen(
                    batchId = batchId,
                    onBackClick = { navController.navigateUp() }
                )
            }

            composable(NavigationRoutes.REGISTRASI_PETANI) {
                RegistrasiPetaniScreen(
                    onBackClick = { navController.navigateUp() }
                )
            }

            composable(NavigationRoutes.TRANSAKSI_ALL) {
                SemuaTransaksiScreen(
                    onBackClick = { navController.navigateUp() }
                )
            }

            composable(NavigationRoutes.TIMBANG_GRADING) {
                TimbangGradingScreen(
                    onBackClick = { navController.navigateUp() }
                )
            }

            composable(NavigationRoutes.PENJUALAN_EKSPOR) {
                PenjualanEksporScreen(
                    onBackClick = { navController.navigateUp() }
                )
            }

            composable(NavigationRoutes.PROFIL) {
                ProfilScreen(
                    onLogout = {
                        navController.navigate(NavigationRoutes.LOGIN) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
