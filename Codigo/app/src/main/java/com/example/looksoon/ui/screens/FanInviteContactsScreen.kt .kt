package com.example.looksoon.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FanInviteContactsScreen(
    navController: NavController? = null,
    onBackClick: () -> Unit = {}
) {
    val searchState = remember { mutableStateOf(TextFieldValue("")) }

    val contacts = listOf("Laura Pérez", "Carlos Díaz", "Marta Gómez", "Andrés Herrera", "Sofía Morales")

    Scaffold(
        containerColor = Background,
        bottomBar = {
            FanBottomNavBar(
                selectedTab = "Invitar",
                onTabSelected = { /* manejar navegación */ }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Background),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            item {
                HeaderFan(
                    section = "Invitar contactos",
                    iconLeft = Icons.Default.ArrowBack,
                    iconRight = Icons.Default.Search,
                    contentDescriptionLeft = "Volver",
                    contentDescriptionRight = "Buscar",
                    onIconLeftClick = onBackClick,
                    onIconRightClick = { /* acción buscar */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(MaterialTheme.colorScheme.primary, Background)
                            )
                        )
                        .padding(12.dp)
                )
            }

            // Campo de búsqueda
            item {
                OutlinedTextField(
                    value = searchState.value,
                    onValueChange = { searchState.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    placeholder = { Text("Buscar contactos...", color = TextSecondary) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = TextSecondary
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(color = TextPrimary),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        cursorColor = PurplePrimary,
                        focusedContainerColor = Surface,
                        unfocusedContainerColor = Surface,
                        focusedIndicatorColor = PurplePrimary,
                        unfocusedIndicatorColor = Divider,
                        focusedLeadingIconColor = PurplePrimary,
                        unfocusedLeadingIconColor = TextSecondary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Divider, thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Lista de contactos
            items(contacts) { contact ->
                ContactCard(
                    name = contact,
                    onInvite = { /* acción de invitar */ }
                )
            }

            item { Spacer(modifier = Modifier.height(64.dp)) }
        }
    }
}

@Composable
fun ContactCard(name: String, onInvite: () -> Unit) {
    Surface(
        color = Surface,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name, color = TextPrimary, fontSize = 16.sp)

            IconButton(onClick = onInvite) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = "Invitar",
                    tint = PurplePrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FanBottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf("Inicio Fan", "Buscar", "Invitar", "Perfil Fan")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.PersonAdd,
        Icons.Default.AccountCircle
    )

    NavigationBar(containerColor = Background) {
        items.forEachIndexed { index, item ->
            val isSelected = item == selectedTab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item) },
                icon = {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(Color(0xFFB84DFF), Color(0xFF5A0FC8))
                                    )
                                )
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = item,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                label = {
                    Text(
                        item,
                        color = if (isSelected) Color(0xFFB84DFF) else TextSecondary,
                        fontSize = 11.sp
                    )
                }
            )
        }
    }
}

/** Header de la pantalla Fan (renombrado para evitar conflictos con el de artista) */
@Composable
fun HeaderFan(
    section: String,
    iconLeft: ImageVector,
    iconRight: ImageVector,
    contentDescriptionLeft: String,
    contentDescriptionRight: String,
    onIconLeftClick: () -> Unit,
    onIconRightClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onIconLeftClick) {
            Icon(iconLeft, contentDescription = contentDescriptionLeft, tint = TextPrimary)
        }

        Text(
            text = section,
            style = MaterialTheme.typography.titleLarge.copy(
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            textAlign = TextAlign.Center
        )

        IconButton(onClick = onIconRightClick) {
            Icon(iconRight, contentDescription = contentDescriptionRight, tint = TextSecondary)
        }
    }
}

@Composable
fun HorizontalDivider(color: Color, thickness: Dp = 1.dp) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness)
            .background(color = color)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFanInviteContacts() {
    LooksoonTheme {
        FanInviteContactsScreen(navController = rememberNavController(), onBackClick = {})
    }
}
