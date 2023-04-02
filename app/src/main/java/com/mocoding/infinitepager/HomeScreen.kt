package com.mocoding.infinitepager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Infinite Pager"
                    )
                }
            )
        }
    ) { paddingValues ->

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            InfinitePager()
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfinitePager() {
    val pages = remember {
        listOf(
            Page(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFcc2b5e), Color(0xFF753a88))
                )
            ),
            Page(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1488CC), Color(0xFF2B32B2))
                )
            ),
            Page(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF00467F), Color(0xFF00467F))
                )
            ),
            Page(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF9796f0), Color(0xFFfbc7d4))
                )
            ),
            Page(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFB79891), Color(0xFF94716B))
                )
            ),
        )
    }
    val fakePages = remember {
        val mPages = pages.toMutableList()

        mPages.add(0, pages.last())
        mPages.add(pages.first())

        mPages
    }
    val pagerState = rememberPagerState(initialPage = 1)

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress) return@LaunchedEffect

        if (pagerState.currentPage == 0) {
            pagerState.scrollToPage(fakePages.lastIndex - 1)
        }

        if (pagerState.currentPage == fakePages.lastIndex) {
            pagerState.scrollToPage(1)
        }
    }

    Box {
        HorizontalPager(
            pageCount = fakePages.size,
            state = pagerState
        ) { index ->
            val page = fakePages[index]

            Box(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.7f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(page.brush)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .clip(CircleShape)
                .background(Color.White.copy(.4f))
                .padding(6.dp)
        ) {
            pages.indices.forEach { index ->
                val selectedPageIndex = when(pagerState.currentPage) {
                    0 -> pages.lastIndex
                    fakePages.lastIndex -> 0
                    else -> pagerState.currentPage - 1
                }

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha =
                                if (selectedPageIndex == index) 1f
                                else .4f
                        }
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
        }
    }

}