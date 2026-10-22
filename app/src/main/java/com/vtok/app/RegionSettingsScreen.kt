package com.vtok.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class RegionChoice(val iso: String, val name: String, val note: String)

private val regionChoices = listOf(
    RegionChoice("NL", "Нидерланды", "Netherlands"),
    RegionChoice("US", "США", "United States"),
    RegionChoice("GB", "Великобритания", "United Kingdom"),
    RegionChoice("DE", "Германия", "Germany"),
    RegionChoice("FR", "Франция", "France"),
    RegionChoice("ES", "Испания", "Spain"),
    RegionChoice("IT", "Италия", "Italy"),
    RegionChoice("SE", "Швеция", "Sweden"),
    RegionChoice("PL", "Польша", "Poland"),
    RegionChoice("UA", "Украина", "Ukraine"),
    RegionChoice("KZ", "Казахстан", "Kazakhstan"),
    RegionChoice("RU", "Россия", "Russia"),
    RegionChoice("TR", "Турция", "Turkey"),
    RegionChoice("BR", "Бразилия", "Brazil"),
    RegionChoice("MX", "Мексика", "Mexico"),
    RegionChoice("CA", "Канада", "Canada"),
    RegionChoice("AU", "Австралия", "Australia"),
    RegionChoice("JP", "Япония", "Japan"),
    RegionChoice("KR", "Южная Корея", "South Korea"),
    RegionChoice("IN", "Индия", "India"),
    RegionChoice("ID", "Индонезия", "Indonesia"),
    RegionChoice("VN", "Вьетнам", "Vietnam"),
    RegionChoice("TH", "Таиланд", "Thailand"),
    RegionChoice("PH", "Филиппины", "Philippines"),
    RegionChoice("BY", "Беларусь", "Belarus"),
    RegionChoice("MD", "Молдова", "Moldova"),
    RegionChoice("GE", "Грузия", "Georgia"),
    RegionChoice("AM", "Армения", "Armenia"),
    RegionChoice("AZ", "Азербайджан", "Azerbaijan"),
    RegionChoice("UZ", "Узбекистан", "Uzbekistan"),
    RegionChoice("KG", "Кыргызстан", "Kyrgyzstan"),
    RegionChoice("TJ", "Таджикистан", "Tajikistan"),
    RegionChoice("LT", "Литва", "Lithuania"),
    RegionChoice("LV", "Латвия", "Latvia"),
    RegionChoice("EE", "Эстония", "Estonia"),
    RegionChoice("FI", "Финляндия", "Finland"),
    RegionChoice("NO", "Норвегия", "Norway"),
    RegionChoice("DK", "Дания", "Denmark"),
    RegionChoice("IE", "Ирландия", "Ireland")
)

@Composable
internal fun RegionSettingsScreen(
    accent: Color,
    enabled: Boolean,
    countryCode: String,
    onEnabled: (Boolean) -> Unit,
    onCountry: (String) -> Unit,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filtered = regionChoices.filter {
        it.name.contains(query, ignoreCase = true) ||
            it.note.contains(query, ignoreCase = true) ||
            it.iso.contains(query, ignoreCase = true)
    }
    Column(Modifier.fillMaxSize().background(Color(0xFF090909))) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Назад", tint = Color.White) }
            Column(Modifier.weight(1f)) {
                Text("Регион и обход", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Настройки региона MargyT · VTok", color = Color(0xFFB8B8B8), fontSize = 12.sp)
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1D1D1D)), shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Подмена системного региона", color = accent, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Выбор страны и сохранение настроек готовы. Реальная подмена ответов TelephonyManager и обход сетевых ограничений пока не подключены к сборке: для этого нужно перенести и проверить bytecode-патчер MargyT именно для TikTok 47.2.41.",
                            color = Color.White, fontSize = 13.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("Включить регион", color = Color.White, fontWeight = FontWeight.SemiBold)
                                Text("Сохраняет выбранное состояние", color = Color(0xFFB8B8B8), fontSize = 11.sp)
                            }
                            Switch(checked = enabled, onCheckedChange = onEnabled)
                        }
                    }
                }
            }
            item {
                OutlinedTextField(
                    value = query, onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Найти страну") }, singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                        focusedBorderColor = accent, unfocusedBorderColor = Color(0xFF555555),
                        focusedLabelColor = accent, unfocusedLabelColor = Color(0xFFB8B8B8)
                    )
                )
            }
            items(filtered.size) { index ->
                val item = filtered[index]
                val selected = item.iso.equals(countryCode, ignoreCase = true)
                Card(
                    onClick = { onCountry(item.iso) },
                    colors = CardDefaults.cardColors(containerColor = if (selected) accent.copy(alpha = .16f) else Color(0xFF1D1D1D)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(item.name, color = Color.White, fontWeight = FontWeight.SemiBold)
                            Text(item.note + " · " + item.iso, color = Color(0xFFB8B8B8), fontSize = 12.sp)
                        }
                        if (selected) Icon(Icons.Default.Check, "Выбрано", tint = accent)
                    }
                }
            }
            item {
                Text(
                    "Важно: регион устройства не меняет IP-адрес, GPS, регион регистрации аккаунта или серверный store_region. Для сетевого доступа нужен отдельный VPN/прокси, если он доступен в твоей сети.",
                    color = Color(0xFFB8B8B8), fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
