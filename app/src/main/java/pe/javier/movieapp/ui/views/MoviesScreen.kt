@file:OptIn(ExperimentalMaterial3Api::class)

package pe.javier.movieapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pe.javier.movieapp.R
import pe.javier.movieapp.domain.model.Movie
import pe.javier.movieapp.ui.viewmodels.MovieUiState

@Composable
fun MoviesScreen(
    movieUiState: MovieUiState,
    onCreateAndRetryAction: () -> Unit,
    currentMoviePage: Int,
    totalMoviePage: Int,
    moveToAnotherPage: (page: Int) -> Unit,
    onMovieClicked: (movie: Movie) -> Unit,
    modifier: Modifier
) {
    when (movieUiState) {
        is MovieUiState.Inactive -> onCreateAndRetryAction()
        is MovieUiState.Success -> MovieGridScreen(
            movies = movieUiState.movies,
            onMovieClicked = onMovieClicked,
            currentMoviePage = currentMoviePage,
            totalMoviePage = totalMoviePage,
            moveToAnotherPage = moveToAnotherPage,
            modifier = modifier
        )

        is MovieUiState.Loading -> LoadingScreen(modifier)
        is MovieUiState.Error -> ErrorScreen(onCreateAndRetryAction, modifier)
    }
}

@Composable
fun MovieGridScreen(
    movies: List<Movie>,
    onMovieClicked: (movie: Movie) -> Unit,
    currentMoviePage: Int,
    totalMoviePage: Int,
    moveToAnotherPage: (page: Int) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = movies, key = { movie -> movie.id }) { movie ->
                    MovieCard(
                        movie = movie,
                        onMovieClicked = { onMovieClicked(movie) },
                        baseUrlPosters = "https://image.tmdb.org/t/p/w500",
                        modifier = Modifier
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.padding_medium),
                                vertical = dimensionResource(id = R.dimen.padding_small)
                            )
                            .fillMaxWidth()
                    )
                }
            }
        }
        BottomNavigationBar(
            currentMoviePage = currentMoviePage,
            totalMoviePage = totalMoviePage,
            moveToAnotherPage = moveToAnotherPage
        )
    }
}

@Composable
fun BottomNavigationBar(
    currentMoviePage: Int,
    totalMoviePage: Int,
    moveToAnotherPage: (page: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(dimensionResource(id = R.dimen.padding_extra_small)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { moveToAnotherPage(currentMoviePage - 1) },
            enabled = currentMoviePage > 1
        ) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                stringResource(id = R.string.last_page)
            )
        }
        Text(
            text = currentMoviePage.toString(),
            style = MaterialTheme.typography.headlineSmall,
        )
        IconButton(
            onClick = { moveToAnotherPage(currentMoviePage + 1) },
            enabled = currentMoviePage < totalMoviePage
        ) {
            Icon(
                Icons.Filled.KeyboardArrowRight,
                stringResource(id = R.string.next_page)
            )
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onMovieClicked: (movie: Movie) -> Unit,
    baseUrlPosters: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        onClick = { onMovieClicked(movie) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(baseUrlPosters + movie.posterPath)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.movie_poster),
                modifier = Modifier.aspectRatio(0.6f),
                contentScale = ContentScale.Crop
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ErrorScreen(onRetryAction: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(id = R.string.loading_failed)
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = { onRetryAction() }
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}
