package fi.dev.databindingmigration.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fi.dev.databindingmigration.activity.repository.CompanyRepository
import fi.dev.databindingmigration.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MainViewModel by lazy { viewModel<MainViewModel>().value }
                    MainScreen(viewModel)
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        val previewRepository = CompanyRepository()
        val previewViewModel: MainViewModel = MainViewModel(previewRepository)
        MainScreen(previewViewModel)
    }
}