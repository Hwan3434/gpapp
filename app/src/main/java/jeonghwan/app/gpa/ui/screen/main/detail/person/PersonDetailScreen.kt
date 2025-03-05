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

//    if(uiState.isLoading){
//        Scaffold(
//            modifier = modifier,
//        ) { innerPadding ->
//            Box(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize(), // Box의 크기를 최대로 확장
//                contentAlignment = Alignment.Center // 내부 콘텐츠를 가운데 정렬
//            ){
//                CircularProgressIndicator()
//            }
//        }
//        return
//    }

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
//                mother = uiState.mother,
//                spouse = uiState.spouse,
//                children = uiState.children.toImmutableList(),
                goToPerson = {
                    viewModel.fetchPersonDetail(it)
                },
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
    )
}
