package com.masranber.midashboard.news.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.masranber.midashboard.CardHeader
import com.masranber.midashboard.news.domain.NewsArticle
import com.masranber.midashboard.ui.theme.ColorPrimaryText
import com.masranber.midashboard.ui.theme.ColorSecondaryText
import com.masranber.midashboard.ui.theme.Typography
import com.masranber.midashboard.util.truncate
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun NewsArticle(newsArticle: NewsArticle) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (Header, Thumbnail, Headline, Summary) = createRefs()

        CardHeader(modifier = Modifier
            .constrainAs(Header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            title = "News",
            subtitle = newsArticle.source.name.truncate(16),
        )

        Text(
            text = newsArticle.title,
            style = Typography.headlineLarge,
            modifier = Modifier.constrainAs(Headline) {
                top.linkTo(parent.top)
                start.linkTo(Header.end, margin = 20.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        Text(
            text = newsArticle.summary,
            style = Typography.headlineSmall,
            modifier = Modifier.constrainAs(Summary) {
                top.linkTo(Headline.bottom, margin = 10.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(Headline.start)
                end.linkTo(Headline.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }

        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(newsArticle.imageUrl)
                .transformations(RoundedCornersTransformation(20f))
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier.constrainAs(Thumbnail) {
                top.linkTo(Header.bottom, margin = 10.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(Header.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewsWidget(newsViewModel: NewsViewModel = viewModel()) {

    val newsState by newsViewModel.newsArticles.collectAsState()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        pagerState.animateScrollToPage(newsState.currentArticle)
    }

    VerticalPager(
        modifier = Modifier.fillMaxSize(),
        count = newsState.newsArticles.size,
        state = pagerState,
    ) { page ->
        NewsArticle(newsState.newsArticles[page])
    }
}