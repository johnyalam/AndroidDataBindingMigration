package fi.dev.databindingmigration.activity.ui_state

import fi.dev.databindingmigration.activity.model.Company

sealed class CompanyUiState {
    data object Loading : CompanyUiState()
    data class Success(val company: Company) : CompanyUiState()
    data class CompanyListSuccess(val companies: List<Company>) : CompanyUiState()
    data class Error(val message: String) : CompanyUiState()
}