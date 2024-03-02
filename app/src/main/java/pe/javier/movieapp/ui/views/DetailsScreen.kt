package pe.javier.movieapp.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pe.javier.movieapp.R
import pe.javier.movieapp.domain.model.Movie

@Composable
fun DetailsScreen(
    selectedMovie: Movie,
    modifier: Modifier
) {
    val baseUrlPosters = "https://image.tmdb.org/t/p/w500"
    MovieDetailContent(
        selectedMovie = selectedMovie,
        baseUrlPosters = baseUrlPosters,
        modifier = modifier
    )
}

@Composable
fun MovieDetailContent(
    selectedMovie: Movie?,
    baseUrlPosters: String,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(selectedMovie?.posterPath?.let { baseUrlPosters + it } ?: "")
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.movie_poster),
                contentScale = ContentScale.Crop,
                modifier = Modifier.heightIn(min = 300.dp)
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Text(text = selectedMovie?.title ?: "", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        Text(
            text = stringResource(R.string.release_date, selectedMovie?.releaseDate ?: ""),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        Text(
            text = stringResource(R.string.vote_average, selectedMovie?.voteAverage ?: ""),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
        Text(
            text = stringResource(id = R.string.overview),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        Text(
            text = selectedMovie?.overview ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        )
    }
}