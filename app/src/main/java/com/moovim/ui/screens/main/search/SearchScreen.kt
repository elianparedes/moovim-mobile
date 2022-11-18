package com.moovim.ui.screens.main;

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
fun AuxSearchScreen(navController: NavHostController, viewModel: SearchViewModel){
    val state = viewModel.state
    Column(Modifier.padding(16.dp)) {
        SearchInput(
            value = state.query,
            onValueChange = { newValue -> viewModel.onQueryChange(newValue) },
            onSearch = {
                viewModel.search(state.query)
            })
        Column( modifier = Modifier
            .verticalScroll(rememberScrollState())) {
            OrderByChips(navController = navController, viewModel=viewModel, onCategory = state.categoryId!=null)
            RoutinesList(state.resultRoutines, navController)
        }
    }
}

@Composable
fun AuxCategoriesScreen(navController: NavHostController, viewModel: SearchViewModel){
    val state = viewModel.state
    Column(Modifier.padding(16.dp)) {
        SearchInput(
            value = state.query,
            onValueChange = { newValue -> viewModel.onQueryChange(newValue) },
            onSearch = {
                viewModel.search(state.query)
                viewModel.categoryChange(null)
                navController.navigate("search")
            })
            SwitchChip(left = stringResource(id = R.string.categories),
            right = stringResource(id = R.string.discover),
            onLeft = { viewModel.state = state.copy(chipSide = ChipSide.LEFT)},
            onRight = {viewModel.state = state.copy(chipSide = ChipSide.RIGHT)},
            chipSide = state.chipSide)
        if(state.chipSide ==  ChipSide.LEFT){
            Column( modifier = Modifier) {
                CategoriesScreen(
                    navController = navController,
                    categoryChanged = { categoryId ->
                        viewModel.categoryChange(categoryId)
                        viewModel.onQueryChange(TextFieldValue())
                        navController.navigate("search")
                    })
            }
        } else
        {
            viewModel.getAllRoutines()
            Column( modifier = Modifier
            ) {
                OrderByChips(navController = navController, viewModel=viewModel, onCategory = false)
                DiscoverScreen(state.resultRoutines, navController)
            }
        }

    }

}

data class Objectives(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)


@Composable
fun CategoriesScreen(navController: NavHostController, categoryChanged: (Int) -> Unit){

    val objectivesTitles: Array<String> = stringArrayResource(id = R.array.objectives_titles)
    val objectivesDescriptions: Array<String> = stringArrayResource(id = R.array.objectives_descriptions)
    val objectivesImage: Array<String> = stringArrayResource(id = R.array.objectives_image_url)

    val objectives = listOf(
        Objectives(title = objectivesTitles[0], description = objectivesDescriptions[0], imageUrl = objectivesImage[0], id = 1),
        Objectives(title = objectivesTitles[1], description = objectivesDescriptions[1], imageUrl = objectivesImage[1], id = 2),
        Objectives(title = objectivesTitles[2], description = objectivesDescriptions[2], imageUrl = objectivesImage[2], id = 3),
        Objectives(title = objectivesTitles[3], description = objectivesDescriptions[3], imageUrl = objectivesImage[3], id = 4)
    )
    val muscles: Array<String> = stringArrayResource(id = R.array.muscles_titles)

    Text(text = stringResource(id = R.string.objectives), modifier = Modifier.padding(0.dp,16.dp,0.dp,0.dp))
    Column(modifier = Modifier
        .padding(0.dp, 16.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        ) {

        ObjectivesList(objectives = objectives,navController,categoryChanged)
        //TODO: Descomentar al implementar listas de ejercicios y vistas de detalle de los mismos
        /*
        Row(modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(0.dp, 16.dp)){
            ObjectivesList(objectives = objectives,navController,getRoutinesByCategory)
        }
        */
        // TODO: Descomentar al implementar listas de ejercicios y vistas de detalle de los mismos
        /*
        Text(text = stringResource(id = R.string.muscles))
        Column(modifier = Modifier.padding(0.dp,16.dp)) {
            MusclesLists(muscles = muscles,navController)
        }

         */
    }
}

@Composable
fun DiscoverScreen(routines: List<Routine>, navController: NavHostController){
        Text(text = stringResource(id = R.string.discover_msg), modifier = Modifier.padding(vertical = 16.dp))
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        RoutinesList(routines = routines, navController = navController)
    }

}

@Composable
fun ObjectivesList(objectives: List<Objectives>, navController: NavHostController, onClickCard: (Int) -> Unit){
    objectives.forEachIndexed { index ,objective ->
        val modifier: Modifier = if(index == objectives.size-1){
            Modifier
                .padding(0.dp, 16.dp, 0.dp, 0.dp)
                .fillMaxWidth()
        } else {
            Modifier
                .padding(0.dp, 16.dp, 0.dp, 0.dp)
                .fillMaxWidth()
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            ObjectiveCard(title = objective.title,
                description = objective.description,
                imageUrl = objective.imageUrl,
                modifier = modifier,
                onClickCard = {onClickCard.invoke(index+1)}
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
                text = stringResource(id = R.string.search_message)
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
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
            .padding(0.dp, 16.dp)
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
                onShareClick = { shareRoutine(context, routine.id) },
                favText = stringResource(id = R.string.add_to_fav),
                onFavClick = { /*TODO*/ }) {

            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderByChips(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel(), onCategory: Boolean){
    val state = viewModel.state
Column(modifier = Modifier.padding(vertical = 8.dp)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconToggleButton(checked = state.filter, onCheckedChange = { viewModel.state = state.copy(filter = !state.filter)} ) {
            Icon(painter = painterResource(id = R.drawable.ic_filter_list), contentDescription = null)
        }
        Text(stringResource(id = R.string.filters), modifier = Modifier.padding(8.dp))
    }
    if(state.filter){
        Row() {
            FilterChip(
                modifier = Modifier.padding(0.dp,0.dp,4.dp,0.dp),
                selected= state.orderBy=="date" && state.direction=="desc",
                onClick = { viewModel.orderByChange("date","desc") },
                leadingIcon = { Icon(Icons.Rounded.DateRange, "DateRange",
                    Modifier
                        .size(24.dp)
                        .padding(4.dp)) },
                colors = ChipDefaults.outlinedFilterChipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    selectedBackgroundColor = Color.White,
                    selectedContentColor = Color.Black,
                    selectedLeadingIconColor = Color.Black,
                    leadingIconColor = Color.White
                ),
                border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
            ) {
                Text( text = stringResource(id = R.string.date_desc) )
            }
            FilterChip(
                modifier = Modifier.padding(),
                selected= state.orderBy=="date" && state.direction=="asc",
                onClick = { viewModel.orderByChange("date", "asc") },
                leadingIcon = { Icon(Icons.Rounded.DateRange, "DateRange",
                    Modifier
                        .size(24.dp)
                        .padding(4.dp)) },
                colors = ChipDefaults.outlinedFilterChipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    selectedBackgroundColor = Color.White,
                    selectedContentColor = Color.Black,
                    selectedLeadingIconColor = Color.Black,
                    leadingIconColor = Color.White
                ),
                border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
            ) {
                Text( text = stringResource(id = R.string.date_asc) )
            }
        }
        Row() {
            FilterChip(
                modifier = Modifier.padding(0.dp,0.dp,4.dp,0.dp),
                selected= state.orderBy=="score" && state.direction=="desc",
                onClick = { viewModel.orderByChange("score","desc") },
                leadingIcon = { Icon(Icons.Rounded.Star ,"Favorite",
                    Modifier
                        .size(24.dp)
                        .padding(4.dp)) },
                colors = ChipDefaults.outlinedFilterChipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    selectedBackgroundColor = Color.White,
                    selectedContentColor = Color.Black,
                    selectedLeadingIconColor = Color.Black,
                    leadingIconColor = Color.White
                ),
                border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
            ) {
                Text( text = stringResource(id = R.string.score_desc) )
            }
            FilterChip(
                modifier = Modifier.padding(),
                selected= state.orderBy=="score" && state.direction=="asc",
                onClick = { viewModel.orderByChange("score","asc") },
                leadingIcon = { Icon(Icons.Rounded.Star, "Favorite",
                    Modifier
                        .size(24.dp)
                        .padding(4.dp)) },
                colors = ChipDefaults.outlinedFilterChipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    selectedBackgroundColor = Color.White,
                    selectedContentColor = Color.Black,
                    selectedLeadingIconColor = Color.Black,
                    leadingIconColor = Color.White
                ),
                border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
            ) {
                Text( text = stringResource(id = R.string.score_asc) )
            }
        }
        Row() {
            FilterChip(
                modifier = Modifier.padding(0.dp,0.dp,4.dp,0.dp),
                selected= state.orderBy=="difficulty" && state.direction=="asc",
                onClick = { viewModel.orderByChange("difficulty","asc") },
                leadingIcon = { Icon(Icons.Rounded.Warning, "Warning",
                    Modifier
                        .size(24.dp)
                        .padding(4.dp)) },
                colors = ChipDefaults.outlinedFilterChipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    selectedBackgroundColor = Color.White,
                    selectedContentColor = Color.Black,
                    selectedLeadingIconColor = Color.Black,
                    leadingIconColor = Color.White
                ),
                border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
            ) {
                Text( text = stringResource(id = R.string.difficulty_asc) )
            }
            FilterChip(
                modifier = Modifier.padding(),
                selected= state.orderBy=="difficulty" && state.direction=="desc",
                onClick = { viewModel.orderByChange("difficulty","desc") },
                leadingIcon = { Icon(Icons.Rounded.Warning, "Warning",
                    Modifier
                        .size(24.dp)
                        .padding(4.dp)) },
                colors = ChipDefaults.outlinedFilterChipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    selectedBackgroundColor = Color.White,
                    selectedContentColor = Color.Black,
                    selectedLeadingIconColor = Color.Black,
                    leadingIconColor = Color.White
                ),
                border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
            ) {
                Text( text = stringResource(id = R.string.difficulty_desc) )
            }
        }
        if(!onCategory) {
            Row() {
                FilterChip(
                    modifier = Modifier.padding(0.dp,0.dp,2.dp,0.dp),
                    selected = state.orderBy == "category" && state.direction == "asc",
                    onClick = { viewModel.orderByChange("category", "asc") },
                    leadingIcon = { Icon(Icons.Rounded.Info, "Info",
                        Modifier
                            .size(24.dp)
                            .padding(4.dp)) },
                    colors = ChipDefaults.outlinedFilterChipColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.White,
                        selectedBackgroundColor = Color.White,
                        selectedContentColor = Color.Black,
                        selectedLeadingIconColor = Color.Black,
                        leadingIconColor = Color.White
                    ),
                    border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
                ) {
                    Text(text = stringResource(id = R.string.category_asc))
                }
                FilterChip(
                    modifier = Modifier.padding(),
                    selected = state.orderBy == "category" && state.direction == "desc",
                    onClick = { viewModel.orderByChange("category", "desc") },
                    leadingIcon = { Icon(Icons.Rounded.Info, "Info",
                        Modifier
                            .size(24.dp)
                            .padding(4.dp)) },
                    colors = ChipDefaults.outlinedFilterChipColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.White,
                        selectedBackgroundColor = Color.White,
                        selectedContentColor = Color.Black,
                        selectedLeadingIconColor = Color.Black,
                        leadingIconColor = Color.White
                    ),
                    border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
                ) {
                    Text(text = stringResource(id = R.string.category_desc))
                }
            }
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