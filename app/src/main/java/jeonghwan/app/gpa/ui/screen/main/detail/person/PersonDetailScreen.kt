package jeonghwan.app.gpa.ui.screen.main.detail.person

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import timber.log.Timber

@Composable
fun PersonDetailScreen(
    modifier: Modifier = Modifier,
    personKey: Int,
    viewModel: PersonDetailViewModel = hiltViewModel(),
    close: () -> Unit,
    goToMap: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = personKey) {
        viewModel.fetchPersonDetail(personKey)
    }


    val loading by remember {
        derivedStateOf { uiState.isLoading }
    }

    val person by remember {
        derivedStateOf { uiState.personEntity }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            PersonDetailAppBar(
                personEntity = uiState.personEntity,
                close = close,
            ) { personEntity ->
                Location(
                    key = personEntity.key,
                    goToMap = goToMap,
                )
                FavoriteIcon(
                    person = personEntity,
                    isFavorite = loading,
                    onFavoriteCheckedChange = viewModel::toggleFavorite,
                )
            }
        },
    ) { innerPadding ->
        if (loading || person == null) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), // Box의 크기를 최대로 확장
                contentAlignment = Alignment.Center // 내부 콘텐츠를 가운데 정렬
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), // Box의 크기를 최대로 확장,
            ) {
                PersonDetailWidget(
                    person = uiState.personEntity!!,
                    father = uiState.father,
                    mother = uiState.mother,
                    spouse = uiState.spouse,
                    children = uiState.children,
                    goToPerson = viewModel::fetchPersonDetail,
                )
            }
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
    )
}
