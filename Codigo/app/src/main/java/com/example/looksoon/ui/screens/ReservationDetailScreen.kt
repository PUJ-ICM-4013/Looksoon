package com.example.looksoon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.looksoon.R
import com.example.looksoon.ui.theme.*

@Composable
fun ReservationDetailScreen(navController: NavController) {
    Scaffold(
        containerColor = Background,
        bottomBar = { BottomBarReservation() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Background)
        ) {
            HeaderRDS(title = "Reserva", navController = navController)


            Text(
                "Resumen de reserva y estado",
                modifier = Modifier.padding(start = 16.dp, bottom = 12.dp),
                color = TextSecondary,
                fontSize = 14.sp
            )

            ArtistReservationCard()
            Spacer(Modifier.height(12.dp))
            PaymentSummary()
            Spacer(Modifier.height(12.dp))
            NotesAndContacts()
            Spacer(Modifier.height(12.dp))
            TimelineSection()
        }
    }
}

@Composable
fun ArtistReservationCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.jazz),
                    contentDescription = "Artist",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Luna Trío", color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("Jazz contemporáneo", color = TextSecondary, fontSize = 13.sp)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Surface)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Confirmada", color = Color(0xFF4CAF50), fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(12.dp))

            ReservationInfoRow("Evento", "Noche de Jazz en Looksoon")
            ReservationInfoRow("Fecha y hora", "12/10 · 20:00")
            ReservationInfoRow("Ubicación", "Palermo, Buenos Aires")
            ReservationInfoRow("Duración", "2 horas (2x45 + intervalo)")

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Text("Equipo", color = TextSecondary, fontSize = 13.sp, modifier = Modifier.weight(1f))
                Tag("Equipo propio")
                Spacer(Modifier.width(6.dp))
                Tag("Amplificación")
            }
        }
    }
}

@Composable
fun PaymentSummary() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("$120.000", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Caché acordado", color = TextSecondary, fontSize = 13.sp)

            Spacer(Modifier.height(8.dp))

            ReservationInfoRow("Pagado", "$30.000")
            ReservationInfoRow("Saldo pendiente", "$90.000")
            ReservationInfoRow("Método", "Transferencia bancaria")

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Surface)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("Factura B", color = PurplePrimary, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun NotesAndContacts() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Detalles y notas", color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("Preferencias, logística y requerimientos", color = TextSecondary, fontSize = 13.sp)
                }
                Text(
                    "Ver contrato",
                    color = PurplePrimary,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                "Escenario de 3×2m, requerimos toma eléctrica cercana. Volumen moderado hasta las 23:00. Incluir 2 temas bossa nova en el segundo set.",
                color = TextPrimary,
                fontSize = 13.sp
            )

            Spacer(Modifier.height(12.dp))
            ReservationInfoRow("Contacto artista", "+54 9 11 5555 1234")
            ReservationInfoRow("Contacto del local", "+54 9 11 4444 5678")
        }
    }
}

@Composable
fun TimelineSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Línea de tiempo", color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("Estados de la reserva", color = TextSecondary, fontSize = 13.sp)
                }
                Text(
                    "Última actualización: hoy 10:32",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            Text("Solicitud enviada · 08/10 14:12", color = TextPrimary, fontSize = 13.sp)
            Text("Artista preseleccionado · 09/10 09:05", color = TextPrimary, fontSize = 13.sp)
        }
    }
}

@Composable
fun ReservationInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, fontSize = 13.sp)
        Text(value, color = TextPrimary, fontSize = 13.sp)
    }
}

@Composable
fun Tag(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Surface)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, color = PurplePrimary, fontSize = 12.sp)
    }
}

@Composable
fun BottomBarReservation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Reserva confirmada", color = TextSecondary, fontSize = 13.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(50),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Text("Compartir")
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                shape = RoundedCornerShape(50)
            ) {
                Text("Descargar")
            }
        }
    }
}

@Composable
fun HeaderRDS(title: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Atrás",
            tint = PurplePrimary,
            modifier = Modifier
                .size(24.dp)
                .clickable { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            title,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ReservationDetailScreenPreview() {
    LooksoonTheme {
        ReservationDetailScreen(navController = rememberNavController())
    }
}
