package com.example.mycity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.mycity.ui.theme.MyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyCityTheme {
                // Configuración del controlador de navegación
                val navController = rememberNavController()

                // Definición de las rutas y pantallas en el NavHost
                NavHost(navController, startDestination = "@+id/categoryScreen") {
                    // Pantalla 1 (Categorías)
                    composable("@+id/categoryScreen") {
                        CategoryScreen(navController = navController, context = this@MainActivity)
                    }
                    // Pantalla 2 (Items de la categoría)
                    composable(
                        route = "category_detail/{categoryName}/{imageName}/{serializedElements}",
                        arguments = listOf(
                            navArgument("categoryName") { type = NavType.StringType; nullable = false },
                            navArgument("imageName") { type = NavType.IntType; nullable = false },
                            navArgument("serializedElements") { type = NavType.StringArrayType; nullable = false }
                        )
                    ) { backStackEntry ->
                        val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                        val imageName = backStackEntry.arguments?.getInt("imageName") ?: 0
                        val serializedElements = backStackEntry.arguments?.getStringArray("serializedElements")?.toList() ?: emptyList()
                        CategoryDetailScreen(categoryName, imageName, serializedElements, navController)
                    }
                    //Pantalla 3 (Detalles del item)
                    composable(
                        route = "item_detail/{categoryName}/{itemIndex}/{imageName}",
                        arguments = listOf(
                            navArgument("categoryName") { type = NavType.StringType; nullable = false },
                            navArgument("itemIndex") { type = NavType.IntType; nullable = false },
                            navArgument("imageName") { type = NavType.IntType; nullable = false }
                        )
                    ) { backStackEntry ->
                        val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                        val itemIndex = backStackEntry.arguments?.getInt("itemIndex") ?: 0
                        val imageName = backStackEntry.arguments?.getInt("imageName") ?: 0

                        ItemDetailScreen(categoryName, itemIndex, imageName, navController)
                    }
                }
            }
        }
    }
}

// Función para crear un elemento de categoría
@Composable
fun CategoryItem(categoryName: Int, imageName: Int, categoryElements: List<String>, navController: NavController) {
    // Obtener la cadena de nombre de la categoría a partir del recurso
    val categoryNameString = stringResource(categoryName)

    // Diseño de fila para el elemento de categoría
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen de la categoría
        Image(
            painter = painterResource(imageName),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        // Texto de la categoría, clickable para la pantalla de los items de la misma
        Text(
            text = categoryNameString,
            modifier = Modifier.clickable {
                val serializedElements = categoryElements.joinToString(",")
                navController.navigate("category_detail/$categoryNameString/$imageName/$serializedElements")
            },
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Función para cargar datos desde los recursos
fun loadData(arrayResourceId: Int, context: Context): List<String> {
    return context.resources.getStringArray(arrayResourceId).toList()
}

@Composable
fun CategoryScreen(navController: NavController, context: Context) {
    val listaRestaurantes by remember { mutableStateOf(loadData(R.array.listaRestaurantes, context)) }
    val listaParques by remember { mutableStateOf(loadData(R.array.listaParques, context)) }
    val listaHoteles by remember { mutableStateOf(loadData(R.array.listaHoteles, context)) }
    val listaSupermercados by remember { mutableStateOf(loadData(R.array.listaSupermercados, context)) }
    val listaGasolineras by remember { mutableStateOf(loadData(R.array.listaGasolineras, context)) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item{
                // Título de la ciudad
                Text(
                    text = stringResource(id = R.string.ciudad),
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Divider(
                    color = Color.Black,
                    thickness = 2.dp
                )
            }
            // Categorias
            item {
                CategoryItem(R.string.restaurantes, R.drawable.restaurante, listaRestaurantes, navController)
                CategoryItem(R.string.parques, R.drawable.parque, listaParques, navController)
                CategoryItem(R.string.hoteles, R.drawable.hotel, listaHoteles, navController)
                CategoryItem(R.string.supermercados, R.drawable.supermercado, listaSupermercados, navController)
                CategoryItem(R.string.gasolineras, R.drawable.gasolinera, listaGasolineras, navController)
                CategoryItem(R.string.restaurantes, R.drawable.restaurante, listaRestaurantes, navController)
                CategoryItem(R.string.parques, R.drawable.parque, listaParques, navController)
                CategoryItem(R.string.hoteles, R.drawable.hotel, listaHoteles, navController)
                CategoryItem(R.string.supermercados, R.drawable.supermercado, listaSupermercados, navController)
                CategoryItem(R.string.gasolineras, R.drawable.gasolinera, listaGasolineras, navController)
                CategoryItem(R.string.restaurantes, R.drawable.restaurante, listaRestaurantes, navController)
                CategoryItem(R.string.parques, R.drawable.parque, listaParques, navController)
                CategoryItem(R.string.hoteles, R.drawable.hotel, listaHoteles, navController)
                CategoryItem(R.string.supermercados, R.drawable.supermercado, listaSupermercados, navController)
                CategoryItem(R.string.gasolineras, R.drawable.gasolinera, listaGasolineras, navController)
            }
        }
    }
}

// Pantalla de detalles de la categoría
@Composable
fun CategoryDetailScreen(categoryName: String, imageName: Int, categoryElements: List<String>, navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                // Encabezado con flecha de retroceso y nombre de la categoría
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(R.drawable.flecha),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clickable {
                            navController.popBackStack()
                        }
                    )
                    Text(
                        text = categoryName,
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(
                    color = Color.Black,
                    thickness = 2.dp
                )
            }

            // Items de la categoría
            val separatedElements = categoryElements.flatMap { it.split(",") }

            itemsIndexed(separatedElements) { index, elementName ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Imagen de la categoria
                    Image(
                        painter = painterResource(imageName),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // Texto del elemento, clickable para la pantalla de detalles del item
                    Text(
                        text = elementName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("item_detail/${categoryName}/${index}/$imageName")
                            },
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Pantalla de detalles del item
@Composable
fun ItemDetailScreen(categoryName: String, itemIndex: Int, imageName: Int, navController: NavController) {
    // Cargar listas de datos según la categoría
    val listaItems: List<String>
    val listaDirecciones: List<String>
    val listaDescripciones: List<String>

    when (categoryName) {
        "Restaurantes" -> {
            listaItems = loadData(R.array.listaRestaurantes, LocalContext.current)
            listaDirecciones = loadData(R.array.dirRestaurantes, LocalContext.current)
            listaDescripciones = loadData(R.array.descRestaurantes, LocalContext.current)
        }
        "Parques" -> {
            listaItems = loadData(R.array.listaParques, LocalContext.current)
            listaDirecciones = loadData(R.array.dirParques, LocalContext.current)
            listaDescripciones = loadData(R.array.descParques, LocalContext.current)
        }
        "Hoteles" -> {
            listaItems = loadData(R.array.listaHoteles, LocalContext.current)
            listaDirecciones = loadData(R.array.dirHoteles, LocalContext.current)
            listaDescripciones = loadData(R.array.descHoteles, LocalContext.current)
        }
        "Supermercados" -> {
            listaItems = loadData(R.array.listaSupermercados, LocalContext.current)
            listaDirecciones = loadData(R.array.dirSupermercados, LocalContext.current)
            listaDescripciones = loadData(R.array.descSupermercados, LocalContext.current)
        }
        "Gasolineras" -> {
            listaItems = loadData(R.array.listaGasolineras, LocalContext.current)
            listaDirecciones = loadData(R.array.dirGasolineras, LocalContext.current)
            listaDescripciones = loadData(R.array.descGasolineras, LocalContext.current)
        }
        else -> {
            listaItems = emptyList()
            listaDirecciones = emptyList()
            listaDescripciones = emptyList()
        }
    }

    // Obtener datos específicos del elemento seleccionado
    val itemSeleccionado = listaItems[itemIndex]
    val direccion = listaDirecciones[itemIndex]
    val descripcion = listaDescripciones[itemIndex]

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                // Encabezado con flecha de retroceso y nombre de la categoría
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(R.drawable.flecha),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clickable {
                            navController.popBackStack()
                        }
                    )
                    Text(
                        text = categoryName,
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(
                    color = Color.Black,
                    thickness = 2.dp
                )
                // Imagen de la categoria
                Image(
                    painter = painterResource(imageName),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                )
                // Nombre del item seleccionado
                Text(
                    text = itemSeleccionado,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                // Direccion del item seleccionado
                Text(
                    text = direccion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 20.sp
                )
                // Descripcion del item seleccionado
                Text(
                    text = descripcion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyCityTheme {

    }
}
