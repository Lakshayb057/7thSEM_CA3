package CA3
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun InventoryApp(dao: ItemDao) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {

        composable("home") {
            ItemListScreen(dao) {
                navController.navigate("details/$it")
            }
        }

        composable(
            "details/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            ItemDetailScreen(
                dao,
                it.arguments?.getInt("id") ?: 0
            )
        }
    }
}

@Composable
fun ItemListScreen(
    dao: ItemDao,
    onClick: (Int) -> Unit
) {
    val list by dao.getAllItems()
        .collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(list) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onClick(item.id) }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Name: ${item.name}")
                    Text("Quantity: ${item.quantity}")
                    Text("Price: ${item.price}")
                }
            }
        }
    }
}

@Composable
fun ItemDetailScreen(dao: ItemDao, id: Int) {

    val item by dao.getItemById(id)
        .collectAsState(initial = null)

    Column(Modifier.padding(20.dp)) {
        Text("Item Details", style = MaterialTheme.typography.headlineMedium)

        item?.let {
            Text("ID: ${it.id}")
            Text("Name: ${it.name}")
            Text("Quantity: ${it.quantity}")
            Text("Price: ${it.price}")
        } ?: Text("Loading...")
    }
}