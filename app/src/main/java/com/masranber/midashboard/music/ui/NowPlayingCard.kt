package com.masranber.midashboard.music.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masranber.midashboard.CardHeader
import com.masranber.midashboard.ui.theme.ColorSecondaryText
import com.masranber.midashboard.ui.theme.ElevatedFrameBgColor
import com.masranber.midashboard.ui.theme.FrameBgColor
import com.masranber.midashboard.ui.theme.Typography

@Composable
fun AlbumArt(modifier: Modifier = Modifier) {
    Text(
        text = "Nothing currently playing.",
        style = Typography.titleSmall,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun NowPlayingCard(nowPlayingViewModel: NowPlayingViewModel = viewModel()) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(FrameBgColor)
            .border(1.dp, ElevatedFrameBgColor, shape = RoundedCornerShape(20.dp))
            .padding(10.dp)
    ) {
        val (Header, AlbumArt, MusicInfo) = createRefs()

        AlbumArt(
            modifier = Modifier
                .constrainAs(AlbumArt) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
        )

        CardHeader(modifier = Modifier
            .constrainAs(Header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            title = "Now Playing",
            subtitle = "N/A"
        )
        
        Column(
            modifier = Modifier
                .constrainAs(MusicInfo) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Artist",
                    style = Typography.headlineSmall.copy(color = ColorSecondaryText),
                )
                Text(
                    text = "•",
                    style = Typography.headlineSmall.copy(color = ColorSecondaryText),
                )
                Text(
                    text = "Year",
                    style = Typography.headlineSmall.copy(color = ColorSecondaryText),
                )
            }
            Text(
                text = "Song Title",
                style = Typography.headlineSmall,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "-:--",
                    style = Typography.headlineSmall
                )
                LinearProgressIndicator(
                    progress = 0.0f,
                    modifier = Modifier.weight(1f).height(10.dp),
                    trackColor = ElevatedFrameBgColor
                )
                Text(
                    text = "-:--",
                    style = Typography.headlineSmall
                )
            }
        }
    }
}