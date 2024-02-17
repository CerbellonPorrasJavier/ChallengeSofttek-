@file:OptIn(ExperimentalMaterial3Api::class)

package pe.javier.movieapp.ui.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import pe.javier.movieapp.R
import pe.javier.movieapp.data.model.SessionUiState

@Composable
fun LoginScreen(
    onLoginButtonClick: (user: String, password: String) -> Unit,
    sessionUiState: SessionUiState,
    modifier: Modifier
) {
    var userInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        UserTextField(
            label = R.string.user,
            leadingIcon = R.drawable.outlined_person,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = userInput,
            onValueChange = { userInput = it }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        PasswordTextField(
            label = R.string.password,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = passwordInput,
            onValueChange = { passwordInput = it }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        Button(
            enabled = userInput.isNotEmpty() && passwordInput.isNotEmpty(),
            onClick = {
                onLoginButtonClick(userInput, passwordInput)
            }
        ) {
            Text(text = stringResource(id = R.string.login))
        }
        if (sessionUiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun UserTextField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        label = { Text(text = stringResource(id = label)) },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun PasswordTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit
) {
    val isPasswordVisible = remember { mutableStateOf(false) }
    val visualTransformation = if (isPasswordVisible.value) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = label)) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            IconButton(
                onClick = {
                    isPasswordVisible.value = !isPasswordVisible.value
                }
            ) {
                val icon = if (isPasswordVisible.value) {
                    painterResource(id = R.drawable.outlined_visibility)
                } else {
                    painterResource(id = R.drawable.outlined_visibility_off)
                }
                Icon(
                    painter = icon,
                    contentDescription = null
                )
            }
        }
    )
}
