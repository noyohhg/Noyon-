package com.noyon.hosting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage

private val Navy = Color(0xFF142B55)
private val Blue = Color(0xFF2563EB)
private val Pale = Color(0xFFF4F7FD)
private val Ink = Color(0xFF17233C)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HostingApp() }
    }
}

@Composable
fun HostingApp() {
    var page by remember { mutableStateOf("Home") }
    var sites by remember { mutableStateOf(listOf<String>()) }
    var showAdd by remember { mutableStateOf(false) }
    var siteName by remember { mutableStateOf("") }
    MaterialTheme(colorScheme = lightColorScheme(primary = Blue, background = Pale, surface = Color.White)) {
        Scaffold(containerColor = Pale, bottomBar = {
            NavigationBar(containerColor = Color.White) {
                listOf("Home", "My Sites", "Upload", "Analytics", "Profile").forEach { item ->
                    NavigationBarItem(
                        selected = page == item,
                        onClick = { if (item == "Upload") showAdd = true else page = item },
                        icon = { Icon(when(item) {
                            "Home" -> Icons.Default.Home
                            "My Sites" -> Icons.Default.Language
                            "Upload" -> Icons.Default.AddCircle
                            "Analytics" -> Icons.Default.BarChart
                            else -> Icons.Default.Person
                        }, null) },
                        label = { Text(item, fontSize = 10.sp) }
                    )
                }
            }
        }) { pad ->
            Column(Modifier.fillMaxSize().padding(pad).verticalScroll(rememberScrollState()).padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("NOYON HOSTING", color = Navy, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Your websites, simplified", color = Color.Gray, fontSize = 12.sp)
                    }
                    AsyncImage(model = "https://i.ibb.co/My6Mn3SL/1000015189.png", contentDescription = "App icon", modifier = Modifier.size(48.dp).clip(CircleShape))
                }
                Spacer(Modifier.height(22.dp))
                when(page) {
                    "Home" -> {
                        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Navy)) {
                            Column(Modifier.fillMaxWidth().padding(22.dp)) {
                                Text("WELCOME BACK", color = Color(0xFFBBD1FF), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("Host your ideas.", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                                Text("Manage your website projects in one place.", color = Color.White.copy(alpha=.8f))
                                Spacer(Modifier.height(16.dp))
                                Button(onClick = { showAdd = true }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Navy)) { Icon(Icons.Default.Add, null); Spacer(Modifier.width(6.dp)); Text("Upload Website") }
                            }
                        }
                        Spacer(Modifier.height(18.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            StatCard("MY SITES", sites.size.toString(), Modifier.weight(1f))
                            StatCard("STATUS", "Ready", Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(18.dp))
                        Text("Quick Actions", fontSize = 18.sp, color = Ink, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        ActionRow("Upload a website", "Add your next project", Icons.Default.CloudUpload) { showAdd = true }
                        ActionRow("My websites", "View saved projects", Icons.Default.Language) { page = "My Sites" }
                    }
                    "My Sites" -> {
                        Text("My Sites", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Ink)
                        Spacer(Modifier.height(12.dp))
                        if (sites.isEmpty()) Text("No websites added yet. Tap Upload to add a project.", color = Color.Gray)
                        sites.forEach { ActionRow(it, "Saved on this device", Icons.Default.Language) {} }
                    }
                    "Analytics" -> {
                        Text("Analytics", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Ink)
                        Spacer(Modifier.height(10.dp))
                        Text("Your site statistics will appear here when hosting service is connected.", color = Color.Gray)
                    }
                    else -> {
                        Text("Profile", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Ink)
                        Spacer(Modifier.height(18.dp))
                        Card(shape = RoundedCornerShape(24.dp)) {
                            Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                AsyncImage(model = "https://i.ibb.co/My6Mn3SL/1000015189.png", contentDescription = "DEVELOPER PIC", modifier = Modifier.size(116.dp).clip(CircleShape))
                                Spacer(Modifier.height(12.dp))
                                Text("DEVELOPER PIC", fontWeight = FontWeight.ExtraBold, color = Navy, fontSize = 17.sp)
                                Spacer(Modifier.height(8.dp))
                                Text("My Channel", color = Color.Gray)
                                Text("@yuyyuhj", color = Blue, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("NOYON HOSTING • Version 1.0", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
            if(showAdd) AlertDialog(
                onDismissRequest = { showAdd = false },
                title = { Text("Add website") },
                text = { OutlinedTextField(value = siteName, onValueChange = { siteName = it }, label = { Text("Website name") }, singleLine = true) },
                confirmButton = { TextButton(onClick = { if(siteName.isNotBlank()) sites = sites + siteName.trim(); siteName = ""; showAdd = false }) { Text("Save") } },
                dismissButton = { TextButton(onClick = { showAdd = false }) { Text("Cancel") } }
            )
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier, shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(18.dp)) {
            Text(label, color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(value, color = Navy, fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun ActionRow(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(vertical = 5.dp).clickable(onClick = onClick), shape = RoundedCornerShape(16.dp)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Blue, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(12.dp))
            Column { Text(title, color = Ink, fontWeight = FontWeight.Bold); Text(subtitle, color = Color.Gray, fontSize = 12.sp) }
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
        }
    }
}
