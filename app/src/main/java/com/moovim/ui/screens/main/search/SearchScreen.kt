package com.moovim.ui.screens.main;

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.R
import com.moovim.domain.model.Exercise
import com.moovim.domain.model.Routine
import com.moovim.ui.components.*
import com.moovim.ui.screens.main.search.SearchViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    val state = viewModel.state
    var onSearch by remember { mutableStateOf(false) }
    var chipSide by remember { mutableStateOf(ChipSide.LEFT) }
    Column(Modifier.padding(16.dp)) {
        SearchInput(
            value = state.query,
            onValueChange = { newValue -> viewModel.onQueryChange(newValue) },
            onSearch = {
                viewModel.search(state.query)
                onSearch = true
            })
        if(onSearch){
            RoutinesList(state.resultRoutines, navController)
        }
        else{
            SwitchChip(left = stringResource(id = R.string.categories),
                right = stringResource(id = R.string.discover),
                onLeft = { chipSide = ChipSide.LEFT },
                onRight = { chipSide = ChipSide.RIGHT },
                chipSide = chipSide)
            if(chipSide == ChipSide.LEFT){
                CategoriesScreen(navController)
            } else
            {
                viewModel.getAllRoutines()
                DiscoverScreen(state.resultRoutines, navController)
            }
        }
    }


}

data class Objectives(
    val title: String,
    val description: String,
    val imageUrl: String
)


@Composable
fun CategoriesScreen(navController: NavHostController){

    val objectivesTitles: Array<String> = stringArrayResource(id = R.array.objectives_titles)
    val objectivesDescriptions: Array<String> = stringArrayResource(id = R.array.objectives_descriptions)
    val objectivesImage: Array<String> = stringArrayResource(id = R.array.objectives_image_url)

    val objectives = listOf(
        Objectives(title = objectivesTitles[0], description = objectivesDescriptions[0], imageUrl = objectivesImage[0]),
        Objectives(title = objectivesTitles[1], description = objectivesDescriptions[1], imageUrl = objectivesImage[1]),
        Objectives(title = objectivesTitles[2], description = objectivesDescriptions[2], imageUrl = objectivesImage[2]),
        Objectives(title = objectivesTitles[3], description = objectivesDescriptions[3], imageUrl = objectivesImage[3])
    )
    val muscles: Array<String> = stringArrayResource(id = R.array.muscles_titles)

    Column(modifier = Modifier
        .padding(0.dp, 16.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {
        Text(text = stringResource(id = R.string.objectives))
        Row(modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(0.dp, 16.dp)){
            ObjectivesList(objectives = objectives,navController)
        }
        Text(text = stringResource(id = R.string.muscles))
        Column(modifier = Modifier.padding(0.dp,16.dp)) {
            MusclesLists(muscles = muscles,navController)
        }
    }
}

@Composable
fun DiscoverScreen(routines: List<Routine>, navController: NavHostController){
        RoutinesList(routines = routines, navController = navController)
}

@Composable
fun ObjectivesList(objectives: List<Objectives>, navController: NavHostController){
    objectives.forEachIndexed { index ,objective ->
        val modifier: Modifier = if(index == objectives.size-1){
            Modifier
                .padding(0.dp)
                .fillMaxWidth()
        } else {
            Modifier
                .padding(0.dp, 0.dp, 16.dp, 0.dp)
                .fillMaxWidth()
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            ObjectiveCard(title = objective.title,
                description = objective.description,
                imageUrl = objective.imageUrl,
                modifier = modifier,
                onClickCard = {}
            )
        }
    }
}

@Composable
fun MusclesLists(muscles: Array<String>,navController: NavHostController) {
    muscles.forEach { muscle ->
            MusclesCard(title = muscle, imageUrl = "", onClickCard = {}, modifier = Modifier.padding(vertical = 8.dp))
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
fun RoutinesList(routines: List<Routine>, navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.verticalScroll(
            rememberScrollState()
        ).padding(0.dp, 16.dp)
    ) {
        routines.forEach { routine ->
            RoutineCard(
                title = routine.name,
                description = routine.detail,
                author = routine.author,
                score = routine.score,
                imageUrl = routine.imageUrl,
                avatarUrl = routine.avatarUrl,
                onClickCard = { navController.navigate("routines/${routine.id}") },
                onShareClick = { /*TODO*/ },
                favText = "Añadir a favoritos",
                onFavClick = { /*TODO*/ }) {

            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoovimRoutineCard(name: String, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(name)
        }

    }
}