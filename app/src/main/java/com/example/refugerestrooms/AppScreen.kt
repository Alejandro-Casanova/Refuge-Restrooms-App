package com.example.refugerestrooms

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.refugerestrooms.data.Screens
import com.example.refugerestrooms.ui.AppViewModel
import com.example.refugerestrooms.ui.navigation.BackPressHandler
import com.example.refugerestrooms.ui.navigation.BottomBar
import com.example.refugerestrooms.ui.navigation.Drawer
import com.example.refugerestrooms.ui.navigation.TopBar
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun RefugeRestroomsApp(
    appViewModel: AppViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val uiState by appViewModel.uiState.collectAsState()
    val currentScreen = uiState.currentScreen

    if (scaffoldState.drawerState.isOpen) {
        BackPressHandler (onBackPressed = {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        })
    }
//    else {
//        BackPressHandler {
//            // navController.navigateUp()
//            navController.popBackStack()
//        }
//    }

    var topBar : @Composable () -> Unit = {
        TopBar(
            title = stringResource(currentScreen.title),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
    }

    if (currentScreen != Screens.DrawerScreens.Home && currentScreen !is Screens.HomeScreens) {
        topBar = {
            TopBar(
                title = stringResource(currentScreen.title),
                buttonIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    }

    val bottomBar: @Composable () -> Unit = {
        if (currentScreen == Screens.DrawerScreens.Home || currentScreen is Screens.HomeScreens) {
            BottomBar(
                onNextScreenClicked = { screen ->
                    //navController.navigate(screen.route)
                    //appViewModel.setCurrentScreen(it)
                    if(screen == Screens.HomeScreens.Search){
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                        }
                    }else{
                        val ret = navController.popBackStack(screen.route, inclusive = false)
                        Log.d("NAVIGATION_DEBUG_2", ret.toString())
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }

    Scaffold(
        topBar = {
            topBar()
        },
        bottomBar = {
            bottomBar()
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer(onDestinationClicked =  { screen ->
                scope.launch {
                    scaffoldState.drawerState.close()
                }
                val ret = navController.popBackStack(Screens.DrawerScreens.Home.route, inclusive = false)
                Log.d("NAVIGATION_DEBUG_2", ret.toString())
                navController.navigate(screen.route) {
                    launchSingleTop = true
                }
                //appViewModel.setCurrentScreen(screen)
            })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
    ) { innerPadding ->
        NavigationHost(
            navController = navController,
            viewModel = appViewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }

}

@Composable
fun NavigationHost(
    navController: NavController,
    viewModel: AppViewModel,
    modifier : Modifier = Modifier
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.DrawerScreens.Home.route,
    ) {
        composable(Screens.DrawerScreens.Home.route) {
            viewModel.setCurrentScreen(Screens.DrawerScreens.Home)
            Text(text="HOME TEST", style = MaterialTheme.typography.displayMedium, modifier = modifier)
        }
        composable(Screens.DrawerScreens.Settings.route) {
            viewModel.setCurrentScreen(Screens.DrawerScreens.Settings)
            Text(text="SETTINGS TEST", style = MaterialTheme.typography.displayMedium, modifier = modifier)
        }
        composable(Screens.DrawerScreens.About.route) {
            viewModel.setCurrentScreen(Screens.DrawerScreens.About)
            Text(text="ABOUT TEST", style = MaterialTheme.typography.displayMedium, modifier = modifier)
        }
        composable(Screens.HomeScreens.Search.route) {
            viewModel.setCurrentScreen(Screens.HomeScreens.Search)
            Text(text="SEARCH TEST", style = MaterialTheme.typography.displayMedium, modifier = modifier)
        }
        composable(Screens.HomeScreens.List.route) {
            viewModel.setCurrentScreen(Screens.HomeScreens.List)
            Text(text="LIST TEST", style = MaterialTheme.typography.displayMedium, modifier = modifier)
        }
        composable(Screens.HomeScreens.Map.route) {
            viewModel.setCurrentScreen(Screens.HomeScreens.Map)
            Text(text="MAP TEST", style = MaterialTheme.typography.displayMedium, modifier = modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RefugeRestroomsApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreviewDarkTheme() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RefugeRestroomsApp()
        }
    }
}