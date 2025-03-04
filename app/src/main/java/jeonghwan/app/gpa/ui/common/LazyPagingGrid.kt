package jeonghwan.app.gpa.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> LazyPagingGrid(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable (T) -> Unit,
) {
    if (lazyPagingItems.itemCount == 0) {
        EmptyView()
        return
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberLazyStaggeredGridState()
    val nestedScrollConnection =
        remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset,
                    source: NestedScrollSource,
                ): Offset {
                    if (available.y != 0f) {
                        keyboardController?.hide()
                    }
                    return Offset.Zero
                }
            }
        }
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        keyboardController?.hide()
                    }
                }
                .nestedScroll(nestedScrollConnection),
    ) {
        LazyVerticalStaggeredGrid(
            state = scrollState,
            modifier =
                Modifier
                    .weight(1f),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(lazyPagingItems.itemCount) { index ->
                lazyPagingItems[index]?.let { item ->
                    itemContent(item)
                }
            }
        }

        when (lazyPagingItems.loadState.append) {
            is LoadState.Loading -> {
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

            is LoadState.Error -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "Error",
                        color = Color.Red,
                    )
                }
            }

            is LoadState.NotLoading -> {
                if (lazyPagingItems.loadState.append.endOfPaginationReached) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No more data",
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "No Data",
                modifier = Modifier.size(64.dp),
            )
            Text("Empty", modifier = Modifier.padding(top = 8.dp))
        }
    }
}
