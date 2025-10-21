package com.example.looksoon.data

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// --- Data classes para entender la respuesta de la API ---
data class RouteResponse(
    val routes: List<Route>
)

data class Route(
    val geometry: String
)

// --- Interfaz de Retrofit que define la llamada a la API ---
interface OsrmApi {
    // Pide una ruta para coche entre un set de coordenadas
    @GET("route/v1/driving/{coordinates}?overview=full&geometries=polyline")
    suspend fun getRoute(
        @Path("coordinates") coordinates: String
    ): RouteResponse
}

// --- Objeto Singleton para acceder fácilmente al servicio desde cualquier parte ---
object RoutingService {
    private const val BASE_URL = "https://router.project-osrm.org/"

    // Configuración de Moshi para interpretar el JSON de la respuesta
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Configuración de Retrofit para hacer la llamada a la red
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    // El objeto `api` que usaremos para hacer la llamada
    val api: OsrmApi = retrofit.create(OsrmApi::class.java)

    // Función para decodificar la geometría de la ruta (Polyline)
    fun decodePolyline(polyline: String): List<LatLng> {
        val points = mutableListOf<LatLng>()
        var index = 0
        var lat = 0
        var lng = 0

        while (index < polyline.length) {
            var result = 1
            var shift = 0
            var b: Int
            do {
                b = polyline[index++].code - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 0x1f)
            lat += if (result and 1 != 0) (result shr 1).inv() else result shr 1

            result = 1
            shift = 0
            do {
                b = polyline[index++].code - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 0x1f)
            lng += if (result and 1 != 0) (result shr 1).inv() else result shr 1

            points.add(LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5))
        }
        return points
    }
}
