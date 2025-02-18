package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jeonghwan.app.entity.GenderType
import jeonghwan.app.entity.PersonEntity

@Composable
fun PersonListScreen(
    modifier: Modifier = Modifier,
    uiState: PersonUiState,
    onLoadPage: () -> Unit,
    onDetailButtonClicked: (Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit,
) {
    val listState = rememberLazyListState()

    // 바닥에 닿았을 때 이벤트 처리
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == uiState.personData.count()) {
                    onLoadPage()
                }
            }
    }

    Column {
        uiState.errorMessage?.let { errorMessage ->
            Text("Error: $errorMessage")
        }

        LazyColumn(
            modifier = modifier.padding(16.dp),
            state = listState,
        ) {
            items(uiState.personData) { person ->
                PersonItem(
                    person = person,
                    onDetailButtonClicked = onDetailButtonClicked,
                    onFavoriteButtonClicked = onFavoriteButtonClicked,
                )
            }

            if (!uiState.isLastPage) {
                item {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPersonListScreen() {
    val uiState =
        PersonUiState(
            personData =
                listOf(
                    PersonEntity(
                        key = 1,
                        name = "길동",
                        alive = true,
                        genderType = GenderType.Male,
                        family = "가평이씨",
                        spouse = 0,
                        generator = 1,
                        clan = "사직공파",
                        etc = "",
                        dateDeath = null,
                        father = 0,
                        mather = 0,
                        tombKey = 1,
                    ),
                    PersonEntity(
                        key = 1,
                        name = "나비",
                        alive = true,
                        genderType = GenderType.Female,
                        family = "김해김씨",
                        spouse = 0,
                        generator = 1,
                        clan = "",
                        etc = "etc.",
                        dateDeath = null,
                        father = 0,
                        mather = 0,
                        tombKey = 1,
                    ),
                ),
            isLoading = false,
            isLastPage = false,
            errorMessage = null,
        )
    PersonListScreen(
        uiState = uiState,
        onLoadPage = {},
        onDetailButtonClicked = {},
        onFavoriteButtonClicked = {},
    )
}
