package com.vtok.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tune
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
private val Gold = Color(0xFFB99A36)
private val Muted = Color(0xFFB8B8B8)
private val Glass = Color(0xB31D1D1D)
private const val PREFS = "vtok_settings"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { VTokApp(this) }
    }
}

@Composable
private fun VTokApp(context: Context) {
    val prefs = remember { context.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var subPage by remember { mutableStateOf("") }
    var liquidGlass by remember { mutableStateOf(prefs.getBoolean("liquid_glass", true)) }
    var mutedColors by remember { mutableStateOf(prefs.getBoolean("muted_colors", true)) }
    var animations by remember { mutableStateOf(prefs.getBoolean("animations", true)) }
    var streakReminder by remember { mutableStateOf(prefs.getBoolean("streak_reminder", false)) }
    var autoRenewStreak by remember { mutableStateOf(prefs.getBoolean("auto_renew_streak", false)) }
    var customAccent by remember { mutableIntStateOf(prefs.getInt("accent", 0xFFB99A36.toInt())) }
    var profileBackground by remember { mutableStateOf(prefs.getString("profile_background", "") ?: "") }
    var regionEnabled by remember { mutableStateOf(prefs.getBoolean("region_enabled", false)) }
    var regionCountry by remember { mutableStateOf(prefs.getString("region_country", "NL") ?: "NL") }
    val accent = Color(customAccent)
    val surfaceColor = if (liquidGlass) Glass else Color(0xFF171717)
    val borderColor = if (liquidGlass) Color(0x55FFFFFF) else Color.Transparent
    fun saveBool(key: String, value: Boolean) { prefs.edit().putBoolean(key, value).apply() }
    MaterialTheme(colorScheme = darkColorScheme(primary = accent, onPrimary = Ink, background = Ink, surface = surfaceColor, onBackground = Color.White, onSurface = Color.White)) {
        if (subPage == "developers") {
            DevelopersScreen(accent, liquidGlass, surfaceColor, borderColor, onBack = { subPage = "" }, onOpen = { url ->
                runCatching { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
            })
            return@MaterialTheme
        }
        if (subPage == "plugins") {
            PluginsScreen(accent, liquidGlass, surfaceColor, borderColor, onBack = { subPage = "" })
            return@MaterialTheme
        }
        if (subPage == "badges") {
            BadgesScreen(accent, liquidGlass, surfaceColor, borderColor, onBack = { subPage = "" })
            return@MaterialTheme
        }
        if (subPage == "updates") {
            UpdatesScreen(accent, liquidGlass, surfaceColor, borderColor, onBack = { subPage = "" }, onOpen = { url ->
                runCatching { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
            })
            return@MaterialTheme
        }
        if (subPage == "region") {
            RegionSettingsScreen(
                accent = accent, enabled = regionEnabled, countryCode = regionCountry,
                onEnabled = { regionEnabled = it; saveBool("region_enabled", it) },
                onCountry = { regionCountry = it; prefs.edit().putString("region_country", it).apply() },
                onBack = { subPage = "settings" }
            )
            return@MaterialTheme
        }
        if (subPage == "settings") {
            SettingsScreen(
                accent = accent, liquidGlass = liquidGlass, surfaceColor = surfaceColor, borderColor = borderColor,
                mutedColors = mutedColors, animations = animations, streakReminder = streakReminder,
                autoRenewStreak = autoRenewStreak, profileBackground = profileBackground,
                onGlass = { liquidGlass = it; saveBool("liquid_glass", it) },
                onMuted = { mutedColors = it; saveBool("muted_colors", it) },
                onAnimations = { animations = it; saveBool("animations", it) },
                onReminder = { streakReminder = it; saveBool("streak_reminder", it) },
                onAutoRenew = { autoRenewStreak = it; saveBool("auto_renew_streak", it) },
                onAccent = { customAccent = it; prefs.edit().putInt("accent", it).apply() },
                onProfileBackground = { profileBackground = it; prefs.edit().putString("profile_background", it).apply() },
                onDevelopers = { subPage = "developers" }, onPlugins = { subPage = "plugins" }, onBadges = { subPage = "badges" }, onUpdates = { subPage = "updates" }, onRegion = { subPage = "region" },
                onBack = { subPage = "" }
            )
            return@MaterialTheme
        }
        Scaffold(
            containerColor = Ink,
            topBar = {
                Row(Modifier.fillMaxWidth().background(Ink).padding(horizontal = 18.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("VTok", color = accent, fontSize = 25.sp, fontWeight = FontWeight.Black)
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { subPage = "settings" }) { Icon(Icons.Default.Settings, "Настройки", tint = Color.White) }
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF101010), contentColor = Color.White) {
                    NavigationBarItem(selected = selectedTab == 0, onClick = { selectedTab = 0 }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Лента") })
                    NavigationBarItem(selected = selectedTab == 1, onClick = { selectedTab = 1 }, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Поиск") })
                    NavigationBarItem(selected = selectedTab == 2, onClick = { selectedTab = 2 }, icon = { Icon(Icons.Default.Add, null) }, label = { Text("Создать") })
                    NavigationBarItem(selected = selectedTab == 3, onClick = { selectedTab = 3 }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Профиль") })
                }
            }
        ) { padding ->
            when (selectedTab) {
                0 -> FeedScreen(Modifier.padding(padding), accent, liquidGlass, surfaceColor, borderColor, animations)
                1 -> SimpleScreen("Поиск", "Поиск TikTok ещё не подключён. Этот экран не выполняет сетевые запросы.", Modifier.padding(padding), accent)
                2 -> SimpleScreen("Создание", "Публикация доступна только после настройки разрешённой интеграции TikTok.", Modifier.padding(padding), accent)
                else -> ProfileScreen(Modifier.padding(padding), accent, liquidGlass, surfaceColor, borderColor, profileBackground, onSettings = { subPage = "settings" })
            }
        }
    }
}

@Composable
private fun FeedScreen(modifier: Modifier, accent: Color, glass: Boolean, surface: Color, border: Color, animations: Boolean) {
    Box(modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF242424), Ink, Color(0xFF1B1B1B))))) {
        Column(Modifier.align(Alignment.Center).padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("VTok", color = accent, fontSize = 42.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(10.dp))
            Text("Твой TikTok — с твоими настройками", color = Color.White, fontSize = 17.sp)
            Spacer(Modifier.height(12.dp))
            Text("Реальная лента, вход, лайки и комментарии ещё не подключены. Это не выдаётся за работающий TikTok.", color = Muted, fontSize = 13.sp)
            Spacer(Modifier.height(20.dp))
            GlassCard(glass, surface, border) {
                Text("VTok 0.1 · ранняя сборка", color = accent, modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp), fontWeight = FontWeight.Bold)
            }
        }
        Column(Modifier.align(Alignment.CenterEnd).padding(end = 12.dp, bottom = 36.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            ActionBubble(Icons.Default.Favorite, "Лайк", accent)
            Spacer(Modifier.height(18.dp)); ActionBubble(Icons.Default.Person, "Профиль", accent)
            Spacer(Modifier.height(18.dp)); ActionBubble(Icons.Default.Share, "Поделиться", accent)
        }
        Row(Modifier.align(Alignment.TopCenter).padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Text("Подписки", color = Muted, fontWeight = FontWeight.SemiBold)
            Text("Для вас", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ActionBubble(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, accent: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(shape = CircleShape, color = Color(0x66000000), modifier = Modifier.size(46.dp)) {
            Box(contentAlignment = Alignment.Center) { Icon(icon, contentDescription = label, tint = accent) }
        }
        Text(label, color = Color.White, fontSize = 10.sp)
    }
}

@Composable
private fun ProfileScreen(modifier: Modifier, accent: Color, glass: Boolean, surface: Color, border: Color, backgroundValue: String, onSettings: () -> Unit) {
    Column(modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(32.dp))
        Surface(shape = CircleShape, color = accent, modifier = Modifier.size(82.dp)) {
            Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Ink, modifier = Modifier.size(44.dp)) }
        }
        Spacer(Modifier.height(14.dp))
        Text("Профиль VTok", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Аккаунт TikTok не подключён", color = Muted, fontSize = 13.sp)
        if (backgroundValue.isNotBlank()) Text("Фон профиля сохранён: $backgroundValue", color = Muted, fontSize = 11.sp)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onSettings, colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Ink)) { Icon(Icons.Default.Tune, null); Spacer(Modifier.width(8.dp)); Text("Настройки и функции") }
        Spacer(Modifier.height(12.dp))
        GlassCard(glass, surface, border) { Text("Бейджи, плагины и обновления", color = Color.White, modifier = Modifier.padding(16.dp)) }
    }
}

@Composable
private fun SettingsScreen(
    accent: Color, liquidGlass: Boolean, surfaceColor: Color, borderColor: Color,
    mutedColors: Boolean, animations: Boolean, streakReminder: Boolean, autoRenewStreak: Boolean,
    profileBackground: String, onGlass: (Boolean) -> Unit, onMuted: (Boolean) -> Unit,
    onAnimations: (Boolean) -> Unit, onReminder: (Boolean) -> Unit, onAutoRenew: (Boolean) -> Unit,
    onAccent: (Int) -> Unit, onProfileBackground: (String) -> Unit, onDevelopers: () -> Unit,
    onPlugins: () -> Unit, onBadges: () -> Unit, onUpdates: () -> Unit, onRegion: () -> Unit, onBack: () -> Unit
) {
    var showAccentOptions by remember { mutableStateOf(false) }
    var backgroundInput by remember { mutableStateOf(profileBackground) }
    val accents = listOf(0xFFB99A36.toInt(), 0xFF7C83FD.toInt(), 0xFF55C9A5.toInt(), 0xFFE879A9.toInt(), 0xFFB7B7B7.toInt())
    Column(Modifier.fillMaxSize().background(Ink)) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Назад", tint = Color.White) }
            Text("Настройки VTok", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item { SectionTitle("Оформление", accent) }
            item { ToggleCard("Жидкое стекло", "Полупрозрачные панели и тонкие границы", liquidGlass, accent, surfaceColor, borderColor, onGlass) }
            item { ToggleCard("Приглушённые цвета", "Менее яркая минималистичная палитра", mutedColors, accent, surfaceColor, borderColor, onMuted) }
            item { ToggleCard("Анимации интерфейса", "Можно отключить для более спокойной работы", animations, accent, surfaceColor, borderColor, onAnimations) }
            item {
                SettingsCard(surfaceColor, borderColor) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Цвет акцента", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("Выбери цвет интерфейса", color = Muted, fontSize = 12.sp)
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            accents.forEach { value ->
                                Surface(onClick = { onAccent(value) }, shape = CircleShape, color = Color(value), modifier = Modifier.size(34.dp).then(if (value == accent.value.toLong().toInt()) Modifier.border(2.dp, Color.White, CircleShape) else Modifier)) {}
                            }
                        }
                    }
                }
            }
            item { SectionTitle("Регион и доступ", accent) }
            item { SettingsCard(surfaceColor, borderColor) { Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Tune, null, tint = accent); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Выбор страны и обход", color = Color.White, fontWeight = FontWeight.SemiBold); Text("Регион MargyT · страна сохраняется на устройстве", color = Muted, fontSize = 12.sp) }; TextButton(onClick = onRegion) { Text("Открыть") } } } }
            item { SectionTitle("Огонёк", accent) }
            item { ToggleCard("Напоминать об огоньке", "Локальная настройка напоминания; уведомления ещё не подключены", streakReminder, accent, surfaceColor, borderColor, onReminder) }
            item { ToggleCard("Автопродление огонька", "Переключатель сохранён, но продлевать огонёк автоматически без официальной функции TikTok приложение не может", autoRenewStreak, accent, surfaceColor, borderColor, onAutoRenew) }
            item { SectionTitle("Профиль и расширения", accent) }
            item {
                SettingsCard(surfaceColor, borderColor) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Фон профиля", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("Сохраняется текстовая ссылка/описание; выбор фото из галереи пока не реализован.", color = Muted, fontSize = 12.sp)
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(value = backgroundInput, onValueChange = { backgroundInput = it }, label = { Text("Описание или ссылка") }, singleLine = true)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { onProfileBackground(backgroundInput) }, colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Ink)) { Text("Сохранить фон") }
                    }
                }
            }
            item { SettingsCard(surfaceColor, borderColor) { Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Code, null, tint = accent); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Плагины", color = Color.White, fontWeight = FontWeight.SemiBold); Text("Каталог и документация интерфейса", color = Muted, fontSize = 12.sp) }; TextButton(onClick = onPlugins) { Text("Открыть") } } } }
            item { SettingsCard(surfaceColor, borderColor) { Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Favorite, null, tint = accent); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Бейджи", color = Color.White, fontWeight = FontWeight.SemiBold); Text("TESTER, OG/OLD, FRIENDS, TEAM", color = Muted, fontSize = 12.sp) }; TextButton(onClick = onBadges) { Text("Открыть") } } } }
            item { SettingsCard(surfaceColor, borderColor) { Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Share, null, tint = accent); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Обновления VTok", color = Color.White, fontWeight = FontWeight.SemiBold); Text("Страница релизов проекта", color = Muted, fontSize = 12.sp) }; TextButton(onClick = onUpdates) { Text("Открыть") } } } }
            item { SettingsCard(surfaceColor, borderColor) { Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Person, null, tint = accent); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Разработчики", color = Color.White, fontWeight = FontWeight.SemiBold); Text("Автор, помощник, версия и ссылки", color = Muted, fontSize = 12.sp) }; TextButton(onClick = onDevelopers) { Text("Открыть") } } } }
            item { Text("VTok 0.1 · Настройки сохраняются на устройстве", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(vertical = 18.dp)) }
        }
    }
}

@Composable
private fun PluginsScreen(accent: Color, glass: Boolean, surface: Color, border: Color, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Ink).padding(18.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Назад", tint = Color.White) }; Text("Плагины VTok", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold) }
        Spacer(Modifier.height(12.dp))
        GlassCard(glass, surface, border) {
            Column(Modifier.padding(16.dp)) {
                Text("Система плагинов — подготовка", color = accent, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("Безопасная загрузка и выполнение сторонних плагинов пока не реализованы. Сейчас это информационная страница, а не работающий загрузчик.", color = Color.White)
                Spacer(Modifier.height(12.dp))
                Text("План API: версия манифеста, список разрешений, проверка подписи, отключение плагина и документация.", color = Muted, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun DevelopersScreen(accent: Color, glass: Boolean, surface: Color, border: Color, onBack: () -> Unit, onOpen: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(Ink).padding(18.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Назад", tint = Color.White) }
            Text("Разработчики", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(16.dp))
        GlassCard(glass, surface, border) {
            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                Text("ramzilzokirov-lgtm", color = accent, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text("Автор и владелец проекта VTok", color = Color.White)
                TextButton(onClick = { onOpen("https://github.com/ramzilzokirov-lgtm") }) { Text("GitHub автора") }
            }
        }
        Spacer(Modifier.height(12.dp))
        GlassCard(glass, surface, border) {
            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                Text("ChatGPT", color = accent, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text("ИИ-помощник: идеи, архитектура и технические задачи", color = Color.White)
            }
        }
        Spacer(Modifier.height(12.dp))
        GlassCard(glass, surface, border) {
            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                Text("VTok 0.1", color = Color.White, fontWeight = FontWeight.Bold)
                Text("Android · ранняя разработка", color = Muted)
                TextButton(onClick = { onOpen("https://github.com/ramzilzokirov-lgtm/VTok") }) { Text("Репозиторий проекта") }
                TextButton(onClick = { onOpen("https://github.com/ramzilzokirov-lgtm/VTok/releases") }) { Text("Обновления и релизы") }
            }
        }
    }
}

@Composable
private fun ToggleCard(title: String, description: String, checked: Boolean, accent: Color, surface: Color, border: Color, onChange: (Boolean) -> Unit) {
    SettingsCard(surface, border) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f).padding(end = 8.dp)) {
                Text(title, color = Color.White, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(3.dp))
                Text(description, color = Muted, fontSize = 12.sp, lineHeight = 16.sp)
            }
            Switch(checked = checked, onCheckedChange = onChange, colors = SwitchDefaults.colors(checkedThumbColor = accent, checkedTrackColor = accent.copy(alpha = .35f)))
        }
    }
}

@Composable
private fun SectionTitle(title: String, accent: Color) {
    Text(title, color = accent, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp, bottom = 2.dp))
}

@Composable
private fun SettingsCard(surface: Color, border: Color, content: @Composable () -> Unit) {
    Surface(color = surface, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth().border(1.dp, border, RoundedCornerShape(20.dp)), content = content)
}

@Composable
private fun GlassCard(glass: Boolean, surface: Color, border: Color, content: @Composable () -> Unit) {
    SettingsCard(if (glass) surface else Color(0xFF171717), if (glass) border else Color.Transparent, content)
}

@Composable
private fun SimpleScreen(title: String, message: String, modifier: Modifier = Modifier, accent: Color = Gold) {
    Column(modifier.fillMaxSize().background(Ink).padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(title, color = accent, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(message, color = Color.White, fontSize = 16.sp)
    }
}


@Composable
private fun BadgesScreen(accent: Color, glass: Boolean, surface: Color, border: Color, onBack: () -> Unit) {
    val badgeList = listOf("TESTER" to "Тестирует новые функции", "OG / OLD" to "Ранний участник", "FRIENDS" to "Друг проекта", "TEAM" to "Команда проекта")
    Column(Modifier.fillMaxSize().background(Ink).padding(18.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Назад", tint = Color.White) }
            Text("Бейджи VTok", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        }
        Text("Предпросмотр типов бейджей", color = Muted, fontSize = 13.sp, modifier = Modifier.padding(start = 8.dp, bottom = 14.dp))
        badgeList.forEach { (name, description) ->
            GlassCard(glass, surface, border) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = accent.copy(alpha = .18f), shape = RoundedCornerShape(9.dp), modifier = Modifier.border(1.dp, accent, RoundedCornerShape(9.dp))) {
                        Text(name, color = accent, fontSize = 11.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Column { Text(description, color = Color.White); Text("Выдача ещё не подключена", color = Muted, fontSize = 11.sp) }
                }
            }
            Spacer(Modifier.height(10.dp))
        }
        Spacer(Modifier.height(8.dp))
        Text("Владелец должен выдавать бейджи вручную. Для настоящей защиты от подделки нужна серверная проверка прав; этот экран пока только показывает типы.", color = Muted, fontSize = 12.sp)
    }
}

@Composable
private fun UpdatesScreen(accent: Color, glass: Boolean, surface: Color, border: Color, onBack: () -> Unit, onOpen: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(Ink).padding(18.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Назад", tint = Color.White) }
            Text("Обновления VTok", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(16.dp))
        GlassCard(glass, surface, border) {
            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                Text("Установлена версия 0.1", color = accent, fontWeight = FontWeight.Bold)
                Text("Проверка обновлений внутри приложения и автоматическая установка пока не реализованы.", color = Color.White, modifier = Modifier.padding(top = 8.dp))
                Spacer(Modifier.height(12.dp))
                Button(onClick = { onOpen("https://github.com/ramzilzokirov-lgtm/VTok/releases") }, colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Ink)) { Text("Открыть релизы GitHub") }
                TextButton(onClick = { onOpen("https://github.com/ramzilzokirov-lgtm/VTok") }) { Text("Исходный код проекта") }
            }
        }
    }
}
