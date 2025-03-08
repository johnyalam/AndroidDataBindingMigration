package fi.dev.databindingmigration.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fi.dev.databindingmigration.activity.model.Company

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val company by viewModel.company.collectAsState()
    val companyList by viewModel.companyList.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Add Company",
            modifier = Modifier
                .background(Color(0xFF43A047))
                .padding(10.dp)
                .clickable { viewModel.onAddCompanyClicked() },
            color = Color.White,
            textAlign = TextAlign.Center
        )

        company?.let {
            CompanyItem(company = it)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Company List", modifier = Modifier.padding(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(companyList) { company ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    CompanyItem(company = company)
                }

            }
        }
    }
}

@Composable
fun CompanyItem(company: Company) {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = company.name,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF7CB342))
            .padding(10.dp),
        color = Color.White,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = company.website,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF7CB342))
            .padding(10.dp),
        color = Color.White,
        textAlign = TextAlign.Center
    )
}