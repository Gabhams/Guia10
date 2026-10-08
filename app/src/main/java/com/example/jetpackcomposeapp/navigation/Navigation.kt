package com.example.jetpackcomposeapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.jetpackcomposeapp.ui.theme.screens.CarreraDetailScreen
import com.example.jetpackcomposeapp.ui.theme.screens.CarrerasListScreen
import com.example.jetpackcomposeapp.ui.theme.screens.MateriaDetailScreen
import com.example.jetpackcomposeapp.viewmodel.CarrerasViewModel
import kotlinx.serialization.Serializable
// Cada pantalla se identifica con una clave serializable
@Serializable
data object HomeKey : NavKey
@Serializable
data class CarreraKey(val nombre: String) : NavKey
@Serializable
data class MateriaKey(val codigo: String) : NavKey
@Composable
fun AppNavDisplay(
    modifier: Modifier = Modifier,
    viewModel: CarrerasViewModel = viewModel()
) {
    val backStack = rememberNavBackStack(HomeKey)
    val carreras by viewModel.carreras.collectAsStateWithLifecycle()
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<HomeKey> {
                CarrerasListScreen(
                    carreras = carreras,
                    onCarreraClick = { nombre ->
                        backStack.add(CarreraKey(nombre)) }
                )
            }
            entry<CarreraKey> { key ->
                CarreraDetailScreen(
                    carreraName = key.nombre,
                    materias = carreras[key.nombre] ?: emptyList(),
                    onMateriaClick = { codigo ->
                        backStack.add(MateriaKey(codigo)) }
                )
            }
            entry<MateriaKey> { key ->
                val materia = viewModel.buscarMateriaPorCodigo(key.codigo)
                if (materia != null) {
                    MateriaDetailScreen(materia = materia)
                } else {
                    Text(text = "Materia no encontrada")
                }
            }
        }
    )
}