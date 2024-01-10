import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mega.screens.AccountScreen
import com.example.mega.screens.BagActionsScreen
import com.example.mega.screens.BagsScreen
import com.example.mega.screens.ListPage

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = "list"){
        composable("list"){
            ListPage()
        }
        composable("bag"){
            BagsScreen()
        }
        composable("person"){
            AccountScreen()
        }
    }
}