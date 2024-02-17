@file:OptIn(ExperimentalMaterial3Api::class)

package pe.javier.movieapp

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pe.javier.movieapp.MainActivity.Companion.ANIMATION_TIME
import pe.javier.movieapp.MainActivity.Companion.API_KEY
import pe.javier.movieapp.ui.viewmodels.MovieViewModel
import pe.javier.movieapp.ui.views.DetailsScreen
import pe.javier.movieapp.ui.views.LoginScreen
import pe.javier.movieapp.ui.views.MoviesScreen

enum class MovieAppScreen(@StringRes val title: Int) {
    Login(title = R.string.app_name),
    Movies(title = R.string.movies),
    MovieDetails(title = R.string.details)
}

@Composable
fun MovieAppBar(
    currentScreen: MovieAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun MovieAppScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MovieAppScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieAppScreen.Login.name
    )
    Scaffold(
        topBar = {
            MovieAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = MovieAppScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = MovieAppScreen.Login.name,
                enterTransition = {
                    fadeIn(animationSpec = tween(ANIMATION_TIME)) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIMATION_TIME)
                    )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(ANIMATION_TIME)) + slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down, tween(ANIMATION_TIME)
                    )
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(ANIMATION_TIME)) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up, tween(ANIMATION_TIME)
                    )
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(ANIMATION_TIME)) + slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIMATION_TIME)
                    )
                }
            ) {
                LoginScreen(
                    onLoginButtonClick = { user, password ->
                        viewModel.validateUserToLogin(user, password)
                    },
                    onLoginSuccess = {
                        navController.navigate(MovieAppScreen.Movies.name)
                    },
                    sessionUiState = uiState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
            composable(
                route = MovieAppScreen.Movies.name,
                enterTransition = {
                    fadeIn(animationSpec = tween(ANIMATION_TIME)) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIMATION_TIME)
                    )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(ANIMATION_TIME)) + slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down, tween(ANIMATION_TIME)
                    )
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(ANIMATION_TIME)) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up, tween(ANIMATION_TIME)
                    )
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(ANIMATION_TIME)) + slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIMATION_TIME)
                    )
                }
            ) {
                MoviesScreen(
                    movieUiState = viewModel.movieUiState,
                    onRetryAction = {
                        viewModel.getMovies(
                            page = uiState.currentMoviePage,
                            apiKey = API_KEY
                        )
                    },
                    currentMoviePage = uiState.currentMoviePage,
                    totalMoviePage = uiState.totalMoviePage,
                    moveToAnotherPage = { page ->
                        viewModel.getMovies(page = page, apiKey = API_KEY)
                    },
                    onMovieClicked = { movie ->
                        viewModel.setSelectedMovie(movie)
                        navController.navigate(MovieAppScreen.MovieDetails.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
            composable(
                route = MovieAppScreen.MovieDetails.name,
                enterTransition = {
                    fadeIn(animationSpec = tween(ANIMATION_TIME)) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIMATION_TIME)
                    )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(ANIMATION_TIME)) + slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down, tween(ANIMATION_TIME)
                    )
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(ANIMATION_TIME)) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up, tween(ANIMATION_TIME)
                    )
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(ANIMATION_TIME)) + slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIMATION_TIME)
                    )
                }
            ) {
                DetailsScreen(
                    selectedMovie = uiState.selectedMovie!!,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }
}