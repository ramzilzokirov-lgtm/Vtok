package com.vtok.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF090909)
private val Gold = Color(0xFFFFD43B)
private val Muted = Color(0xFFB8B8B8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { VTokApp() }
    }
}

@Composable
private fun VTokApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    MaterialTheme(colorScheme = darkColorScheme(primary = Gold, onPrimary = Ink, background = Ink, surface = Color(0xFF171717), onBackground = Color.White, onSurface = Color.White)) {
        Scaffold(containerColor = Ink, bottomBar = {
            NavigationBar(containerColor = Color(0xFF101010), contentColor = Color.White) {
                NavigationBarItem(selected = selectedTab == 0, onClick = { selectedTab = 0 }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Лента") })
                NavigationBarItem(selected = selectedTab == 1, onClick = { selectedTab = 1 }, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Поиск") })
                NavigationBarItem(selected = selectedTab == 2, onClick = { selectedTab = 2 }, icon = { Icon(Icons.Default.Add, null) }, label = { Text("Создать") })
                NavigationBarItem(selected = selectedTab == 3, onClick = { selectedTab = 3 }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Профиль") })
            }
        }) { padding ->
            when (selectedTab) {
                0 -> FeedScreen(Modifier.padding(padding))
                1 -> SimpleScreen("Поиск", "Поиск TikTok появится после подключения поддерживаемого интерфейса.")
                2 -> SimpleScreen("Создание", "Публикация требует авторизованного доступа TikTok.")
                else -> SimpleScreen("Профиль", "Подключение настоящего аккаунта TikTok ещё не настроено.")
            }
        }
    }
}

@Composable
private fun FeedScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().background(Ink)) {
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF242424), Color(0xFF090909), Color(0xFF1B1B1B)))), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(28.dp)) {
                Text("VTok", color = Gold, fontSize = 42.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(10.dp))
                Text("Твой TikTok — с твоими настройками", color = Color.White, fontSize = 17.sp)
                Spacer(Modifier.height(12.dp))
                Text("Это стартовый экран. Реальная лента, вход, лайки и комментарии требуют доступного способа интеграции с TikTok.", color = Muted, fontSize = 13.sp)
                Spacer(Modifier.height(20.dp))
                Surface(color = Gold, shape = RoundedCornerShape(24.dp)) {
                    Text("Сборка 0.1 · прототип", color = Ink, modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp), fontWeight = FontWeight.Bold)
                }
            }
        }
        Column(Modifier.align(Alignment.CenterEnd).padding(end = 12.dp, bottom = 36.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            ActionBubble(Icons.Default.Favorite, "Лайк")
            Spacer(Modifier.height(18.dp))
            ActionBubble(Icons.Default.Person, "Профиль")
            Spacer(Modifier.height(18.dp))
            ActionBubble(Icons.Default.Share, "Поделиться")
        }
        Row(Modifier.align(Alignment.TopCenter).padding(top = 18.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Text("Подписки", color = Muted, fontWeight = FontWeight.SemiBold)
            Text("Для вас", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ActionBubble(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(shape = CircleShape, color = Color(0x66000000), modifier = Modifier.size(46.dp)) {
            Box(contentAlignment = Alignment.Center) { Icon(icon, contentDescription = label, tint = Color.White) }
        }
        Text(label, color = Color.White, fontSize = 10.sp)
    }
}

@Composable
private fun SimpleScreen(title: String, message: String) {
    Column(Modifier.fillMaxSize().background(Ink).padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(title, color = Gold, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(message, color = Color.White, fontSize = 16.sp)
    }
}
