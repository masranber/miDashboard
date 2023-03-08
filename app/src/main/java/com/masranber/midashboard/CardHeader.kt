package com.masranber.midashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Dimension
import com.masranber.midashboard.ui.theme.ColorPrimaryText
import com.masranber.midashboard.ui.theme.ColorSecondaryText
import com.masranber.midashboard.ui.theme.Typography

@Composable
fun CardHeader(modifier: Modifier = Modifier, title: String, subtitle: String? = null) {
    Row(
        //horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = Typography.headlineSmall,
            modifier = Modifier
        )
        subtitle?.let {
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                modifier = Modifier.size(width = 1.dp, height = 24.dp),
                color = ColorSecondaryText
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = subtitle,
                style = Typography.headlineSmall.copy(color = ColorSecondaryText),
                modifier = Modifier
            )
        }
    }
}