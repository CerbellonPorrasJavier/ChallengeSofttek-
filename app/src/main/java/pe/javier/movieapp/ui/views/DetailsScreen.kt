package pe.javier.movieapp.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pe.javier.movieapp.R
import pe.javier.movieapp.domain.model.Movie

@Composable
fun DetailsScreen(
    selectedMovie: Movie,
    modifier: Modifier
) {
    val context = LocalContext.current
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .height(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    R.string.marked_favourite_movie,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(Icons.Filled.Favorite, stringResource(id = R.string.favourite_movie))
            }
        }
    }
}