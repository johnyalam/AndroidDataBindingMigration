package fi.dev.databindingmigration.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.dev.databindingmigration.activity.model.Company
import fi.dev.databindingmigration.activity.repository.CompanyRepository
import fi.dev.databindingmigration.activity.ui_state.CompanyIntent
import fi.dev.databindingmigration.activity.ui_state.CompanyUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CompanyRepository) : ViewModel() {
    // Use backing properties and asStateFlow/asSharedFlow for better encapsulation
    private val _state = MutableStateFlow<CompanyUiState>(CompanyUiState.Loading)
    val state: StateFlow<CompanyUiState> = _state.asStateFlow()

    private val _intent = MutableSharedFlow<CompanyIntent>()

    init {
        handleIntents()
        sendIntent(CompanyIntent.LoadInitialCompany) // Load default company on startup
    }

    fun sendIntent(intent: CompanyIntent) {
        viewModelScope.launch { _intent.emit(intent) }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            _intent.collectLatest { intent -> // Use collectLatest to handle only the latest intent
                _state.update { CompanyUiState.Loading } // Set loading state before each intent
                when (intent) {
                    is CompanyIntent.LoadInitialCompany -> loadInitialCompany()
                    is CompanyIntent.FetchCompanyList -> fetchCompanyListParallel()
                }
            }
        }
    }

    private fun loadInitialCompany() {
        // Use a more descriptive name for the initial company
        val defaultCompany = Company("Apple", "apple.com")
        _state.update { CompanyUiState.Success(listOf(defaultCompany)) }
    }

    private fun fetchCompanyListParallel() {
        viewModelScope.launch {
            try {
                // Use coroutines' async/await for better concurrency management
                val companyList = listOf(
                    asyncFetchCompany("Apple", "apple.com"),
                    asyncFetchCompany("Google", "google.com"),
                    asyncFetchCompany("YouTube", "youtube.com")
                ).map { it.await() }
                _state.update { CompanyUiState.Success(companyList) }
            } catch (e: Exception) {
                _state.update { CompanyUiState.Error("Failed to fetch company list: ${e.message}") }
            }
        }
    }

    // Helper function for async company fetching
    private fun asyncFetchCompany(name: String, website: String) = viewModelScope.async {
        repository.fetchCompanyInfo(name, website)
    }
}