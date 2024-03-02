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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pe.javier.movieapp.MainActivity.Companion.ANIMATION_TIME
import pe.javier.movieapp.ui.utils.MovieContentType
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
    viewModel: MovieViewModel,
    navController: NavHostController,
    currentScreen: MovieAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (currentScreen != MovieAppScreen.valueOf(MovieAppScreen.Login.name)) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(currentScreen.title),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            },
            actions = {
                if (currentScreen == MovieAppScreen.valueOf(MovieAppScreen.Movies.name)) {
                    IconButton(
                        onClick = {
                            viewModel.setIsLoggedOut()
                            navController.popBackStack(MovieAppScreen.Login.name, inclusive = false)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentDescription = stringResource(id = R.string.logout)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun MovieAppScreen(
    windowSize: WindowWidthSizeClass,
    viewModel: MovieViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val movieContentType: MovieContentType = when (windowSize) {
        WindowWidthSizeClass.Compact -> MovieContentType.LIST_ONLY
        WindowWidthSizeClass.Medium -> MovieContentType.LIST_AND_EXPANDED_DETAIL
        WindowWidthSizeClass.Expanded -> MovieContentType.LIST_AND_PERMANENT_DETAIL
        else -> MovieContentType.LIST_ONLY
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MovieAppScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieAppScreen.Login.name
    )
    Scaffold(
        topBar = {
            MovieAppBar(
                viewModel = viewModel,
                navController = navController,
                currentScreen = currentScreen,
                canNavigateBack = (
                        navController.previousBackStackEntry != null
                                && currentScreen != MovieAppScreen.valueOf(MovieAppScreen.Movies.name)
                        ),
                navigateUp = { navController.popBackStack() }
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
                    movieContentType = movieContentType,
                    onLoginButtonClick = { user, password ->
                        viewModel.validateUserToLogin(user, password)
                    },
                    onLoginSuccess = {
                        navController.navigate(MovieAppScreen.Movies.name)
                    },
                    sessionUiState = uiState,
                    modifier = Modifier.fillMaxSize()
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
                    selectedMovie = uiState.selectedMovie,
                    movieContentType = movieContentType,
                    onCreateAndRetryAction = {
                        viewModel.getMovies(
                            page = uiState.currentMoviePage,
                            isDetailScreenPermanent = movieContentType == MovieContentType.LIST_AND_PERMANENT_DETAIL
                        )
                    },
                    currentMoviePage = uiState.currentMoviePage,
                    totalMoviePage = uiState.totalMoviePage,
                    moveToAnotherPage = { page ->
                        viewModel.getMovies(
                            page = page,
                            isDetailScreenPermanent = movieContentType == MovieContentType.LIST_AND_PERMANENT_DETAIL
                        )
                    },
                    onMovieClicked = { movie ->
                        when (movieContentType) {
                            MovieContentType.LIST_ONLY -> {
                                viewModel.setSelectedMovie(movie)
                                navController.navigate(MovieAppScreen.MovieDetails.name)
                            }

                            MovieContentType.LIST_AND_EXPANDED_DETAIL -> {
                                if (movie == uiState.selectedMovie) {
                                    viewModel.setSelectedMovie(null)
                                } else {
                                    viewModel.setSelectedMovie(movie)
                                }
                            }

                            else -> {
                                viewModel.setSelectedMovie(movie)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
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
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
