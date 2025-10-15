package com.example.looksoon.ui.screens.login_register.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.looksoon.R
import com.example.looksoon.ui.theme.Divider
import com.example.looksoon.ui.theme.PurplePrimary
import com.example.looksoon.ui.theme.Surface
import com.example.looksoon.ui.theme.TextPrimary
import com.example.looksoon.ui.theme.TextSecondary

// Composable reutilizable para campos de texto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                val icon = if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = description,
                        tint = TextSecondary
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PurplePrimary,
            unfocusedBorderColor = Divider,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = PurplePrimary,
            focusedLabelColor = PurplePrimary,
            unfocusedLabelColor = TextSecondary,
            focusedContainerColor = Surface,
            unfocusedContainerColor = Surface
        ),
        modifier = modifier.fillMaxWidth()
    )
}

// Composable reutilizable para botones principales
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PurplePrimary,
            contentColor = Color.White
        ),
        enabled = enabled
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Composable reutilizable para texto con imagen/icono
@Composable
fun ImageWithText(
    imageRes: Int,
    imageSize: Int = 120,
    title: String,
    subtitle: String? = null,
    titleColor: Color = _root_ide_package_.com.example.looksoon.ui.theme.TextPrimary,
    subtitleColor: Color = _root_ide_package_.com.example.looksoon.ui.theme.TextSecondary
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "App Logo",
            modifier = Modifier.size(imageSize.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            color = titleColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        subtitle?.let {
            Text(
                text = it,
                color = subtitleColor,
                fontSize = 14.sp
            )
        }
    }
}

// Composable reutilizable para divisores con texto
@Composable
fun TextDivider(
    text: String,
    textColor: Color = _root_ide_package_.com.example.looksoon.ui.theme.TextSecondary,
    dividerColor: Color = _root_ide_package_.com.example.looksoon.ui.theme.Divider,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(color = dividerColor, thickness = 1.dp, modifier = Modifier.weight(1f))
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Divider(color = dividerColor, thickness = 1.dp, modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {
    },
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onArtistClick: () -> Unit,
    onEstablishmentClick: () -> Unit,
    onFanClick: () -> Unit,
    onCuratorClick: () -> Unit,
    viewModel: LoginViewModel = LoginViewModel()
) {
    val state by viewModel.state.collectAsState()


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo and Welcome Text
            ImageWithText(
                imageRes = R.drawable.logo_looksoon,
                title = "¡Bienvenido de nuevo!",
                subtitle = "Inicia sesión para continuar"
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email Field
            CustomOutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = "Correo electrónico",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            CustomOutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = "Contraseña",
                isPassword = true
            )

            // Forgot Password
            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = PurplePrimary,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            PrimaryButton(
                text = "Iniciar sesión",
                onClick = //onLoginClick,
                    {
                        viewModel.checkLogin(
                            state.email,
                            state.password,
                            onArtistClick,
                            onEstablishmentClick,
                            onFanClick,
                            onCuratorClick
                        )
                    },
                enabled = state.email.isNotEmpty() && state.password.isNotEmpty()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Divider with "o" in the middle
            TextDivider(text = "o")

            Spacer(modifier = Modifier.height(24.dp))

            // Social Login Buttons
            SocialLoginButton(
                text = "Continuar con Google",
                iconRes = R.drawable.ic_google,
                onClick = { /* Handle Google login */ },
                backgroundColor = Surface,
                textColor = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            SocialLoginButton(
                text = "Continuar con Facebook",
                iconRes = R.drawable.ic_facebook,
                onClick = { /* Handle Facebook login */ },
                backgroundColor = Surface,
                textColor = TextPrimary
            )

            Spacer(modifier = Modifier.weight(1f))

            // Sign Up
            AccountFlowRow(
                onLinkClick = onSignUpClick,
                infoLeft = "¿No tienes una cuenta?",
                infoRight = "Regístrate"
            )
        }
    }
}

@Composable
fun AccountFlowRow(
    onLinkClick: () -> Unit,
    infoLeft: String,
    infoRight: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = infoLeft,
            color = TextSecondary,
            fontSize = 14.sp
        )
        TextButton(
            onClick = onLinkClick,
            modifier = Modifier.padding(0.dp)
        ) {
            Text(
                text = infoRight,
                color = PurplePrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    iconRes: Int,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.dp,
        )
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun LoginScreenPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        LoginScreen(
            onLoginClick = {},
            onForgotPasswordClick = {},
            onSignUpClick = {},
            onArtistClick = {},
            onEstablishmentClick = {},
            onFanClick = {},
            onCuratorClick = {},
            //viewModel = LoginViewModel()
            )
    }
}

// Preview para componentes individuales
@Preview
@Composable
fun CustomOutlinedTextFieldPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            CustomOutlinedTextField(
                value = "",
                onValueChange = {},
                label = "Email",
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = "",
                onValueChange = {},
                label = "Password",
                isPassword = true
            )
        }
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        PrimaryButton(
            text = "Iniciar sesión",
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun ImageWithTextPreview() {
    _root_ide_package_.com.example.looksoon.ui.theme.LooksoonTheme {
        ImageWithText(
            imageRes = R.drawable.logo_looksoon,
            title = "¡Bienvenido!",
            subtitle = "Continúa con tu cuenta"
        )
    }
}