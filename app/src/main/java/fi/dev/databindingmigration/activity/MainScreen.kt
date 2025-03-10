package fi.dev.databindingmigration.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.dev.databindingmigration.activity.ui_state.CompanyIntent
import fi.dev.databindingmigration.activity.ui_state.CompanyUiState

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is CompanyUiState.Loading -> CircularProgressIndicator()
            is CompanyUiState.Success -> {
                val companies = (state as CompanyUiState.Success).companies
                companies.forEach { company ->
                    Text(text = "${company.name} - ${company.website}")
                }
            }
            is CompanyUiState.Error -> {
                val message = (state as CompanyUiState.Error).message
                Text(text = "Error: $message", color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Fetch Company List Button
        Button(onClick = { viewModel.sendIntent(CompanyIntent.FetchCompanyList) }) {
            Text("Fetch Company List")
        }
    }
}
