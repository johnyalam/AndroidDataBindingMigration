package fi.dev.databindingmigration.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.dev.databindingmigration.activity.ui_state.CompanyIntent
import fi.dev.databindingmigration.activity.ui_state.CompanyUiState
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is CompanyUiState.Loading -> CircularProgressIndicator()
            is CompanyUiState.Success -> {
                val company = (state as CompanyUiState.Success).company
                Text(text = "Company: ${company?.name ?: "N/A"} - ${company?.website ?: "N/A"}")
            }
            is CompanyUiState.CompanyListSuccess -> {
                val companies = (state as CompanyUiState.CompanyListSuccess).companies
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

        // Add Company Section
        var name by remember { mutableStateOf("") }
        var website by remember { mutableStateOf("") }

        BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            singleLine = true
        )

        BasicTextField(
            value = website,
            onValueChange = { website = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            singleLine = true
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.sendIntent(CompanyIntent.AddCompany(name, website))
                }
            }
        ) {
            Text("Add Company")
        }
    }
}
