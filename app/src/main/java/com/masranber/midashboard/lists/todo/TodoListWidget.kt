package com.masranber.midashboard.lists.todo

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masranber.midashboard.CardHeader
import com.masranber.midashboard.R
import com.masranber.midashboard.lists.todo.domain.TodoListItem
import com.masranber.midashboard.ui.theme.*
import java.time.ZonedDateTime
import kotlin.random.Random

fun randomColor() = Color(
    Random.nextInt(256),
    Random.nextInt(256),
    Random.nextInt(256),
    alpha = 255
)


@Composable
fun TodoListItem(
    name: String,
    isComplete: Boolean,
    onNameChanged: ((String) -> Unit),
    onCompleteChanged: ((Boolean) -> Unit),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        /*Text(
            text = "${index+1}",
            style = Typography.headlineSmall.copy(color = ColorSecondaryText),
        )
        Spacer(modifier = Modifier.width(10.dp))
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ElevatedFrameBgColor, shape = RoundedCornerShape(10.dp))
                .border(1.dp, FrameOutlineColor, shape = RoundedCornerShape(10.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BasicTextField(
                value = name,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .weight(1f),
                textStyle = Typography.headlineSmall,
                /*colors = TextFieldDefaults.textFieldColors(
                    textColor = ColorPrimaryText,
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = ColorSecondaryText
                ),*/
                singleLine = true,
                onValueChange = onNameChanged
            )

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onCompleteChanged(!isComplete)
                },
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = when(isComplete) {
                        true -> R.drawable.ic_check_circle_filled_24
                        false -> R.drawable.ic_circle_outline_24
                    }),
                    colorFilter = ColorFilter.tint(when(isComplete) {
                        true -> ColorPrimaryText
                        false -> ColorSecondaryText
                    }),
                    contentDescription = "item completion status",
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoListWidget(viewModel: TodoListViewModel = viewModel()) {
    val todoList by viewModel.todoList.collectAsState()

    Column() {
        CardHeader(title = todoList.name, subtitle = "${todoList.items.size} items")

        Spacer(modifier = Modifier.height(20.dp))

        // List Content
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonBgColor),
                border = BorderStroke(1.dp, ButtonOutlineColor),
                shape = RoundedCornerShape(20.dp),
                onClick = { viewModel.addTodoListItem("Added item") }
            ) {
                Text(
                    text = "+ add item",
                    color = ColorSecondaryText,
                    fontWeight = FontWeight.Light,
                    fontSize = 24.sp,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                todoList.items.forEachIndexed { index, item ->
                    TodoListItem(
                        name = item.name,
                        isComplete = item.isComplete,
                        onNameChanged = {
                            viewModel.setTodoListItemName(index, it)
                        },
                        onCompleteChanged = {
                            viewModel.setTodoListItemComplete(index, it)
                        }
                    )
                }
            }
        }
    }
}