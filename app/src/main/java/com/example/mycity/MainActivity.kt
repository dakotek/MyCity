package com.example.mycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

        val listaRestaurantes = resources.getStringArray(R.array.listaRestaurantes).toList()
        val listaParques = resources.getStringArray(R.array.listaParques).toList()
        val listaHoteles = resources.getStringArray(R.array.listaHoteles).toList()
        val listaSupermercados = resources.getStringArray(R.array.listaSupermercados).toList()
        val listaGasolineras = resources.getStringArray(R.array.listaGasolineras).toList()
        val dirRestaurantes = resources.getStringArray(R.array.dirRestaurantes).toList()
        val dirParques = resources.getStringArray(R.array.dirParques).toList()
        val dirHoteles = resources.getStringArray(R.array.dirHoteles).toList()
        val dirSupermercados = resources.getStringArray(R.array.dirSupermercados).toList()
        val dirGasolineras = resources.getStringArray(R.array.dirGasolineras).toList()
        val descRestaurantes = resources.getStringArray(R.array.descRestaurantes).toList()
        val descParques = resources.getStringArray(R.array.descParques).toList()
        val descHoteles = resources.getStringArray(R.array.descHoteles).toList()
        val descSupermercados = resources.getStringArray(R.array.descSupermercados).toList()
        val descGasolineras = resources.getStringArray(R.array.descGasolineras).toList()

        setContent {
            MyCityTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = R.id.categoryScreen.toString()) {
                    composable(R.id.categoryScreen.toString()) {
                        CategoryScreen(
                            navController = navController,
                            listaRestaurantes = listaRestaurantes,
                            listaParques = listaParques,
                            listaHoteles = listaHoteles,
                            listaSupermercados = listaSupermercados,
                            listaGasolineras = listaGasolineras
                        )
                    }
                    composable(
                        route = "category_detail/{categoryName}",
                        arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                        val categoryElements = when (categoryName) {
                            "Restaurantes" -> listaRestaurantes
                            "Parques" -> listaParques
                            "Hoteles" -> listaHoteles
                            "Supermercados" -> listaSupermercados
                            "Gasolineras" -> listaGasolineras
                            else -> emptyList()
                        }
                        val categoryImage = when (categoryName) {
                            "Restaurantes" -> R.drawable.restaurante
                            "Parques" -> R.drawable.parque
                            "Hoteles" -> R.drawable.hotel
                            "Supermercados" -> R.drawable.supermercado
                            "Gasolineras" -> R.drawable.gasolinera
                            else -> R.drawable.resource_default
                        }
                        CategoryDetailScreen(categoryName, categoryImage, categoryElements, navController)
                    }
                    composable(
                        route = "item_detail/{categoryName}/{itemIndex}",
                        arguments = listOf(
                            navArgument("categoryName") { type = NavType.StringType },
                            navArgument("itemIndex") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                        val itemIndex = backStackEntry.arguments?.getInt("itemIndex") ?: 0
                        val itemName = when (categoryName) {
                            "Restaurantes" -> listaRestaurantes[itemIndex]
                            "Parques" -> listaParques[itemIndex]
                            "Hoteles" -> listaHoteles[itemIndex]
                            "Supermercados" -> listaSupermercados[itemIndex]
                            "Gasolineras" -> listaGasolineras[itemIndex]
                            else -> ""
                        }
                        val itemImage = when (categoryName) {
                            "Restaurantes" -> R.drawable.restaurante
                            "Parques" -> R.drawable.parque
                            "Hoteles" -> R.drawable.hotel
                            "Supermercados" -> R.drawable.supermercado
                            "Gasolineras" -> R.drawable.gasolinera
                            else -> R.drawable.resource_default
                        }
                        val itemAddress = when (categoryName) {
                            "Restaurantes" -> dirRestaurantes[itemIndex]
                            "Parques" -> dirParques[itemIndex]
                            "Hoteles" -> dirHoteles[itemIndex]
                            "Supermercados" -> dirSupermercados[itemIndex]
                            "Gasolineras" -> dirGasolineras[itemIndex]
                            else -> ""
                        }
                        val itemDescription = when (categoryName) {
                            "Restaurantes" -> descRestaurantes[itemIndex]
                            "Parques" -> descParques[itemIndex]
                            "Hoteles" -> descHoteles[itemIndex]
                            "Supermercados" -> descSupermercados[itemIndex]
                            "Gasolineras" -> descGasolineras[itemIndex]
                            else -> ""
                        }
                        ItemDetailScreen(categoryName, itemName, itemImage, itemAddress, itemDescription, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(categoryName: Int, imageName: Int, categoryElements: List<String>, navController: NavController) {
    val categoryNameString = stringResource(categoryName)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(imageName),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = categoryNameString,
            modifier = Modifier.clickable {
                navController.navigate("category_detail/$categoryNameString")
            },
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CategoryScreen(
    navController: NavController,
    listaRestaurantes: List<String>,
    listaParques: List<String>,
    listaHoteles: List<String>,
    listaSupermercados: List<String>,
    listaGasolineras: List<String>
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item{
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

@Composable
fun CategoryDetailScreen(categoryName: String, categoryImage: Int, categoryElements: List<String>, navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
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
            categoryElements.forEachIndexed { index, elementName ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(categoryImage),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = elementName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("item_detail/${categoryName}/${index}")
                                },
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDetailScreen(categoryName: String, itemName: String, itemImage: Int, itemAddress: String, itemDescription: String, navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
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
                Image(
                    painter = painterResource(itemImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                )
                Text(
                    text = itemName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = itemAddress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 20.sp
                )
                Text(
                    text = itemDescription,
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
