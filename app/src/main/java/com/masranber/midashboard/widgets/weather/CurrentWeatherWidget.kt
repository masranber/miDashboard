package com.masranber.midashboard.widgets.weather

import com.masranber.midashboard.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview
fun CurrentWeatherWidget( ) {
    val viewModel: CurrentWeatherViewModel = viewModel()
    WeatherCard(time = "11 pm", weatherIcon = R.drawable.ic_launcher_foreground, degree = "23 C")
}

@Composable
private fun WeatherCard(date: String? = null, time: String, weatherIcon: Int, degree: String) {
    Card(
        modifier = Modifier,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                if (date != null) {
                    Text(text = date, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
                }
                Text(text = time, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
            }
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(text = degree, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp))
        }
    }
}