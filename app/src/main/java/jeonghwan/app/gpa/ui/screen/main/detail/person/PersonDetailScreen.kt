package jeonghwan.app.gpa.ui.screen.main.detail.person

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PersonDetailScreen(
    modifier: Modifier = Modifier,
    personKey: Int,
    viewModel: PersonDetailViewModel = hiltViewModel(),
    close: () -> Unit,
    goToMap: (Int) -> Unit,
    goToPerson: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = personKey) {
        viewModel.fetchPersonDetail(personKey)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            PersonDetailAppBar(
                personEntity = uiState.personEntity,
                close = close,
                goToMap = goToMap,
            ) {
                FavoriteIcon(
                    person = it,
                    isFavorite = uiState.isFavorite,
                    onFavoriteCheckedChange = viewModel::toggleFavorite,
                )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            PersonDetailWidget(
                person = uiState.personEntity,
                father = uiState.father,
                mother = uiState.mother,
                spouse = uiState.spouse,
                children = uiState.children.toImmutableList(),
                siblings = uiState.siblings.toImmutableList(),
                goToPerson = goToPerson,
            )
        }
    }
}

@Preview
@Composable
fun PreviewPersonDetailScreen() {
    PersonDetailScreen(
        personKey = 1,
        close = {},
        goToMap = {},
        goToPerson = {},
    )
}
