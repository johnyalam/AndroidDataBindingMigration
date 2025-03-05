package fi.dev.databindingmigration.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.dev.databindingmigration.activity.model.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel()  {
    private val _company = MutableStateFlow<Company?>(null)
    val company: StateFlow<Company?> = _company

    private val _companyList = MutableStateFlow<List<Company>>(emptyList())
    val companyList: StateFlow<List<Company>> = _companyList

    init {
        val initialCompany = Company("Apple", "apple.com")
        _company.value = initialCompany

        // Start adding companies automatically
        viewModelScope.launch {
            addCompaniesWithInterval().collect { newCompanies ->
                _companyList.value = newCompanies
            }
        }
    }

    fun onAddCompanyClicked() {
        viewModelScope.launch {
            val newCompany = async { fetchCompanyInfo("Google", "google.com") }.await()
            _company.value = newCompany

            delay(2000)
        }
        fetchCompanyListParallel()
    }

    /**
     * Fetches a company from a simulated request
     */
    private suspend fun fetchCompanyInfo(name: String, web: String): Company {
        return withContext(Dispatchers.IO) { // Switching to IO dispatcher
            delay(2000) // Simulate network delay
            Company(name, web) // Return new company
        }
    }

    /**
     * Fetches company list in parallel using async-await
     */
    private fun fetchCompanyListParallel() {
        viewModelScope.launch {
            val appleDeferred = async(Dispatchers.IO) {fetchCompanyInfo("Apple", "apple.com") }
            val googleDeferred = async(Dispatchers.IO) { fetchCompanyInfo("Google", "google.com") }
            val youtubeDeferred = async(Dispatchers.IO) { fetchCompanyInfo("YouTube", "youtube.com") }

            val companyList = listOf(
                appleDeferred.await(),
                googleDeferred.await(),
                youtubeDeferred.await()
            )

            _companyList.value = companyList
        }
    }

    // Adding company with interval of 1 second
    // Reactive data stream
    private fun addCompaniesWithInterval() : Flow<List<Company>> = flow {
        var count = 0
        val maxCount = 5

        while (count < maxCount) {
            val currentList = _companyList.value.toMutableList()

            val newCompany = Company("${count}. Youtube", "youtube.com")
            currentList.add(newCompany)

            emit(currentList.toList()) // Emit a new list
            delay(1000) // Wait for 1 second
            count++
        }
    }
}