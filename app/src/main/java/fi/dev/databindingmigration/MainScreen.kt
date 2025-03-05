package fi.dev.databindingmigration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    val company by viewModel.company.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = it.name,
                modifier = Modifier
                    .width(200.dp)
                    .background(Color(0xFF7CB342))
                    .padding(10.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = it.website,
                modifier = Modifier
                    .width(200.dp)
                    .background(Color(0xFF7CB342))
                    .padding(10.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
