package com.moovim.ui.screens.main;

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.moovim.ui.components.ExerciseCard
import com.moovim.ui.components.RoutineCard
import com.moovim.domain.model.Exercise
import com.moovim.domain.model.Routine
import com.moovim.ui.screens.main.search.SearchViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    val state = viewModel.state

    Column(Modifier.padding(16.dp)) {
        SearchInput(
            value = state.query,
            onValueChange = { newValue -> viewModel.onQueryChange(newValue) },
            onSearch = {
                viewModel.search(state.query)
            })
        RoutinesList(state.resultRoutines, navController)
    }

}

@Composable
fun SearchInput(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, onSearch: KeyboardActionScope.() -> Unit) {
    TextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = {
            Text(
                text = "Buscar rutinas"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        leadingIcon = { Icon(Icons.Rounded.Search, "Buscar") },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardActions = KeyboardActions(onSearch = onSearch)
    )

}

@Composable
fun ExerciseList(exercises: List<Exercise>){
    exercises.forEach { exercise ->
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)) {
            Text(exercise.name)
        }
    }
}

@Composable
fun RoutinesList(routines: List<Routine>, navController: NavHostController){
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        routines.forEach { routine ->
            RoutineCard(name = routine.name, onClick = {navController.navigate("routines/${routine.id}")})
        }
    }

}