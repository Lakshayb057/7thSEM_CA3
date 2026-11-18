package CA3

import CA3.ui.theme.Semester7Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "inventory_db"
        ).build()

        setContent {
            Semester7Theme {
                InventoryApp(db.itemDao())
            }
        }
    }
}