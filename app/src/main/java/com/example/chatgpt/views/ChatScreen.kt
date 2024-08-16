package com.example.chatgpt.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatgpt.data.Message
import com.example.chatgpt.data.Sender
import com.example.chatgpt.views.viewModel.ChatApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@Preview
fun ChatWindow(navController: NavHostController = rememberNavController()) {
    val viewModel = viewModel<ChatApp>()
    ChatDrawerContainer(navController, viewModel)
}

@Composable
fun ChatDrawerContainer(navController: NavHostController = rememberNavController(), viewModel: ChatApp) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerContainerColor = Color(0xFF252525)
            ) {
                ChatDrawerContent()
            }
        },
        drawerState = drawerState,
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF1B1B1B)) {
            ChatMainContent(navController, scope, drawerState, viewModel)
        }
    }
}

@Composable
fun ChatDrawerContent(chatNames: List<String> = listOf("Chat 1", "Chat 2", "Long chat 3")) {
    Text(text = "Your chats", color = Color.White, modifier = Modifier.padding(20.dp),
        fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    HorizontalDivider()
    Column {
        LazyColumn {
            items(chatNames) { chatName ->
                Button(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RectangleShape,
                ) {
                    Text(text = chatName, modifier = Modifier.fillMaxWidth(), color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ChatMainContent(
    navController: NavHostController = rememberNavController(),
    drawerCoroutine: CoroutineScope, drawerState: DrawerState, viewModel: ChatApp
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            ChatTopBar(drawerState, drawerCoroutine, viewModel)
        },
        bottomBar = {
            ChatUserInput(viewModel)
        },
        containerColor = Color(0xFF1B1B1B),
    ) { paddingValues ->
        ChatContent(externalPadding = paddingValues, viewModel)
    }
}

@Composable
fun ChatTopBar(drawerState: DrawerState, drawerCoroutine: CoroutineScope, viewModel: ChatApp) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            Log.d("Drawer", "Drawer opened")
            drawerCoroutine.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Menu button",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Text(text = "Llama 3.1", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)

        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = {
                viewModel.messageHandler.clearMessages()
            }) {
                Icon(
                    imageVector = Icons.Rounded.AddCircle,
                    contentDescription = "Menu button",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "AI model selection",
                    tint = Color.White)
            }
        }
    }
}

@Composable
fun ChatUserInput(viewModel: ChatApp) {
    val actualUserInput = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(value = actualUserInput.value, onValueChange = { actualUserInput.value = it}, modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        shape = RoundedCornerShape(90.dp), placeholder = { Text(text = "Write your query here")},
        trailingIcon = {
            IconButton(onClick = {
                if (actualUserInput.value != "") {
                    //keyboardController?.hide()
                    viewModel.messageHandler.sendUserMessage(actualUserInput.value)
                    actualUserInput.value = ""
                }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.Send, contentDescription = "Send message")
            }
        })
}

@Composable
fun ChatContent(externalPadding: PaddingValues, viewModel: ChatApp) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(externalPadding), color = Color(0xFF1B1B1B)) {

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(state =  listState, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)) {
                coroutineScope.launch {
                    if (viewModel.getChatMessages().isNotEmpty()) {
                        listState.animateScrollToItem(viewModel.getChatMessages().size - 1)
                    }
                }
                itemsIndexed(viewModel.getChatMessages(), key = { index, _ -> index }) { index ,message ->
                    MessageComponent(message)
                }
            }
        }
    }
}

@Composable
fun MessageComponent(message: Message) {
    val horizontalArrangement: Arrangement.Horizontal = when (message.sender) {
        Sender.User -> Arrangement.End
        Sender.AIBot -> Arrangement.Start
    }

    val surfaceColor = when (message.sender) {
        Sender.User -> Color.DarkGray
        Sender.AIBot -> Color.Black
    }

    Row(horizontalArrangement = horizontalArrangement, modifier = Modifier.fillMaxWidth()) {
        Surface(color = surfaceColor, shape = RoundedCornerShape(4.dp)) {
            Text(text = message.text, modifier = Modifier.padding(10.dp), color = Color.White)
        }
    }

    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun ContentRecomendation() {

}

@Composable
fun Int.toDp(): Dp {
    return with(LocalDensity.current) {
        this@toDp.toDp()
    }
}