package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.data.TravelRepository
import com.example.ui.components.FloatingToastBanner
import com.example.ui.components.NavigationTab
import com.example.ui.components.PortalBottomNavBar
import com.example.ui.components.PortalTopAppBar
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RaiseRequestScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val repository = remember { TravelRepository() }
        val isAuthenticated by repository.isAuthenticated.collectAsState()
        val toastMessage by repository.toastMessage.collectAsState()
        var currentTab by remember { mutableStateOf(NavigationTab.TRIPS) }

        Box(modifier = Modifier.fillMaxSize()) {
          if (!isAuthenticated) {
            AuthScreen(
              onLoginSuccess = { email ->
                repository.login(email)
                currentTab = NavigationTab.TRIPS
              },
              onShowNotice = { msg ->
                repository.showToast(msg)
              }
            )
          } else {
            Scaffold(
              modifier = Modifier.fillMaxSize(),
              topBar = {
                PortalTopAppBar(
                  title = "Travel Desk",
                  subtitle = when (currentTab) {
                    NavigationTab.TRIPS -> "Dashboard & Active Itineraries"
                    NavigationTab.NEW_REQUEST -> "Raise Travel Request"
                    NavigationTab.POLICY_CAPS -> "Corporate Policy & Spend Caps"
                    NavigationTab.PROFILE -> "Profile, Entitlements & Sync"
                  },
                  onNotificationsClick = {
                    repository.showToast("No critical travel alerts. Desk operational.")
                  },
                  onProfileClick = {
                    currentTab = NavigationTab.PROFILE
                  }
                )
              },
              bottomBar = {
                PortalBottomNavBar(
                  currentTab = currentTab,
                  onTabSelected = { selected ->
                    currentTab = selected
                  }
                )
              }
            ) { innerPadding ->
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(innerPadding)
              ) {
                when (currentTab) {
                  NavigationTab.TRIPS -> {
                    DashboardScreen(
                      repository = repository,
                      modifier = Modifier.fillMaxSize()
                    )
                  }
                  NavigationTab.NEW_REQUEST -> {
                    RaiseRequestScreen(
                      repository = repository,
                      modifier = Modifier.fillMaxSize()
                    )
                  }
                  NavigationTab.POLICY_CAPS, NavigationTab.PROFILE -> {
                    ProfileScreen(
                      repository = repository,
                      onLogoutRequested = {
                        repository.logout()
                      },
                      modifier = Modifier.fillMaxSize()
                    )
                  }
                }
              }
            }
          }

          // Top floating notification banner
          FloatingToastBanner(
            message = toastMessage,
            onDismiss = { repository.clearToast() }
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}
