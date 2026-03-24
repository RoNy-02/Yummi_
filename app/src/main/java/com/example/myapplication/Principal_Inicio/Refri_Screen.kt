package com.example.myapplication.Principal_Inicio

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.FoodItem
import com.example.myapplication.SessionManager
import com.example.myapplication.componentes.CustomBottomBar
import com.example.myapplication.ui.theme.*
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Refri_Screen(navController: NavHostController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    
    var foodItems by remember { mutableStateOf(sessionManager.getFoodItems()) }
    var searchQuery by remember { mutableStateOf("") }
    
    // Selection state
    var selectedIds by remember { mutableStateOf(setOf<String>()) }
    var isSelectionMode by remember { mutableStateOf(false) }
    
    // Dialog states
    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<String?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var itemToEdit by remember { mutableStateOf<FoodItem?>(null) }

    val filteredItems = foodItems.filter { 
        it.name.contains(searchQuery, ignoreCase = true) 
    }

    Scaffold(
        topBar = {
            if (isSelectionMode) {
                TopAppBar(
                    title = { Text("${selectedIds.size} seleccionados", fontSize = 18.sp) },
                    navigationIcon = {
                        IconButton(onClick = { 
                            isSelectionMode = false
                            selectedIds = emptySet()
                        }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (!isSelectionMode) {
                FloatingActionButton(
                    onClick = { },
                    containerColor = MainOrange,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 80.dp)
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan")
                }
            }
        },
        bottomBar = { CustomBottomBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BgGradientStart, BgGradientEnd)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                if (!isSelectionMode) {
                    Text(
                        text = "Mi Refri",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainPurple
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar alimento...", color = TextGray, fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = MainPurple,
                            unfocusedBorderColor = Color.Transparent,
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                if (filteredItems.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay alimentos agregados", color = TextGray)
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(filteredItems, key = { it.id }) { item ->
                            val isSelected = selectedIds.contains(item.id)
                            RefriItemRow(
                                item = item,
                                isSelected = isSelected,
                                isSelectionMode = isSelectionMode,
                                onLongClick = {
                                    if (!isSelectionMode) {
                                        isSelectionMode = true
                                        selectedIds = setOf(item.id)
                                    }
                                },
                                onClick = {
                                    if (isSelectionMode) {
                                        selectedIds = if (isSelected) selectedIds - item.id else selectedIds + item.id
                                        if (selectedIds.isEmpty()) isSelectionMode = false
                                    }
                                },
                                onDelete = {
                                    itemToDelete = item.id
                                    showDeleteDialog = true
                                },
                                onEdit = {
                                    itemToEdit = item
                                    showEditDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }

        // Delete Confirmation Dialog
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { 
                    showDeleteDialog = false
                    itemToDelete = null
                },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar los elementos seleccionados?") },
                confirmButton = {
                    TextButton(onClick = {
                        if (isSelectionMode) {
                            sessionManager.deleteMultipleFoodItems(selectedIds)
                            isSelectionMode = false
                            selectedIds = emptySet()
                        } else {
                            itemToDelete?.let { sessionManager.deleteFoodItem(it) }
                        }
                        foodItems = sessionManager.getFoodItems()
                        showDeleteDialog = false
                        itemToDelete = null
                    }) {
                        Text("Eliminar", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { 
                        showDeleteDialog = false
                        itemToDelete = null
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Edit Dialog
        if (showEditDialog && itemToEdit != null) {
            FoodItemDialog(
                initialItem = itemToEdit,
                onDismiss = { showEditDialog = false },
                onConfirm = { updatedItem ->
                    sessionManager.saveFoodItem(updatedItem)
                    foodItems = sessionManager.getFoodItems()
                    showEditDialog = false
                }
            )
        }
    }
}

@Composable
fun FoodItemDialog(
    initialItem: FoodItem? = null,
    onDismiss: () -> Unit,
    onConfirm: (FoodItem) -> Unit
) {
    var name by remember { mutableStateOf(initialItem?.name ?: "") }
    var details by remember { mutableStateOf(initialItem?.details ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialItem == null) "Agregar Alimento" else "Editar Alimento") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text("Detalles (ej: 1 Lt • Exp: 2026)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(
                            FoodItem(
                                id = initialItem?.id ?: UUID.randomUUID().toString(),
                                name = name,
                                details = details,
                                colorValue = initialItem?.colorValue ?: 0xFFFFFFFF.toInt()
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MainPurple)
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RefriItemRow(
    item: FoodItem,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFF3E5F5) else Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelectionMode) {
                Checkbox(checked = isSelected, onCheckedChange = { onClick() })
            }
            
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(item.colorValue), RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(item.details, fontSize = 12.sp, color = TextGray)
            }
            
            if (!isSelectionMode) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Edit", tint = TextGray)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Delete", tint = TextGray)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Refri_ScreenPreview() {
    MyApplicationTheme {
        Refri_Screen(rememberNavController())
    }
}
