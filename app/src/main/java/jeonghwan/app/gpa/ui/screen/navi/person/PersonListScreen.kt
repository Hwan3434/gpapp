package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jeonghwan.app.domain.model.PersonEntity

@Composable
fun TestPersonListScreen(
    uiState: () -> PersonUiState,
    listState: LazyListState,
    isFavorite: (PersonEntity) -> Boolean,
    onToggleFavorite: (PersonEntity) -> Unit,
    onDetailScreen: (Int) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .padding(16.dp),
        state = listState,
    ) {
        items(
            items = uiState().personData,
            key = { it.key },
        ) { person ->
            PersonItem(
                person = person,
                isFavorite = isFavorite,
                onDetailButtonClicked = onDetailScreen,
                onToggleFavorite = onToggleFavorite,
            )
        }
    }
}

// @Composable
// fun PersonListScreen(
//    modifier: Modifier = Modifier,
//    uiState: PersonUiState,
//    favorite: () -> Set<PersonEntity>,
//    listState: LazyListState,
//    onDetailButtonClicked: (Int) -> Unit,
//    onFavoriteButtonClicked: (PersonEntity) -> Unit,
// ) {
//    LazyColumn(
//        modifier = modifier.padding(16.dp),
//        state = listState,
//    ) {
//        items(items = uiState.personData, key = {
//            it.key
//        }) { person ->
//            PersonItem(
//                person = person,
//                isFavorite = favorite,
//                onDetailButtonClicked = onDetailButtonClicked,
//                onToggleFavorite = onFavoriteButtonClicked,
//            )
//        }
//    }
// }
//
// @Preview
// @Composable
// fun PreviewPersonListScreen() {
//    val uiState =
//        PersonUiState(
//            personData =
//                listOf(
//                    jeonghwan.app.domain.model.PersonEntity(
//                        key = 1,
//                        name = "길동",
//                        alive = true,
//                        genderType = jeonghwan.app.domain.model.GenderType.Male,
//                        family = "가평이씨",
//                        spouse = 0,
//                        generator = 1,
//                        clan = "사직공파",
//                        etc = "",
//                        dateDeath = null,
//                        father = 0,
//                        mather = 0,
//                        tombKey = 1,
//                    ),
//                    jeonghwan.app.domain.model.PersonEntity(
//                        key = 1,
//                        name = "나비",
//                        alive = true,
//                        genderType = jeonghwan.app.domain.model.GenderType.Female,
//                        family = "김해김씨",
//                        spouse = 0,
//                        generator = 1,
//                        clan = "",
//                        etc = "etc.",
//                        dateDeath = null,
//                        father = 0,
//                        mather = 0,
//                        tombKey = 1,
//                    ),
//                ),
//            isLoading = false,
//            isLastPage = false,
//            errorMessage = null,
//        )
//    PersonListScreen(
//        uiState = uiState,
//        listState = rememberLazyListState(),
//        favorite = { emptySet() },
//        onDetailButtonClicked = {},
//        onFavoriteButtonClicked = {},
//    )
// }
