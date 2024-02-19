@file:OptIn(ExperimentalMaterial3Api::class)

package pe.javier.movieapp.ui.views

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.javier.movieapp.R
import pe.javier.movieapp.data.model.SessionUiState

@Composable
fun LoginScreen(
    onLoginButtonClick: (user: String, password: String) -> Unit,
    onLoginSuccess: () -> Unit,
    sessionUiState: SessionUiState,
    modifier: Modifier
) {
    val context = LocalContext.current
    var userInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    if (sessionUiState.isLoading) {
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
    } else {
        if (sessionUiState.isLogin) {
            onLoginSuccess()
        } else {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.login_message),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 64.sp,
                            fontSize = 64.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_triple_extra_large)))
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
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                    PasswordTextField(
                        label = R.string.password,
                        leadingIcon = R.drawable.outlined_password,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        value = passwordInput,
                        onValueChange = { passwordInput = it }
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_double_extra_large)))
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_double_extra_large)))
                    Button(
                        enabled = userInput.isNotEmpty() && passwordInput.isNotEmpty(),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                        onClick = {
                            if (userInput != "Admin" || passwordInput != "Password*123") {
                                Toast.makeText(context, R.string.login_failed, Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                onLoginButtonClick(userInput, passwordInput)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.login),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                        )
                    }
                }
            }
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
        onValueChange = onValueChange,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        label = { Text(text = stringResource(id = label)) },
        keyboardOptions = keyboardOptions,
        shape = ShapeDefaults.ExtraLarge,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordTextField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
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
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
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
        },
        shape = ShapeDefaults.ExtraLarge,
        modifier = Modifier.fillMaxWidth()
    )
}
