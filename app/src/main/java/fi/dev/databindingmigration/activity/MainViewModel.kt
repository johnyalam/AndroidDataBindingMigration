package fi.dev.databindingmigration.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.dev.databindingmigration.activity.model.Company
import fi.dev.databindingmigration.activity.repository.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CompanyRepository) : ViewModel()  {
    private val _company = MutableStateFlow<Company?>(null)
    val company: StateFlow<Company?> = _company

    private val _companyList = MutableStateFlow<List<Company>>(emptyList())
    val companyList: StateFlow<List<Company>> = _companyList

    init {
        val initialCompany = Company("Apple", "apple.com")
        _company.value = initialCompany

        // Start adding companies automatically
        viewModelScope.launch {
            repository.addCompaniesWithInterval(_companyList.value).collect { newCompanies ->
                _companyList.value = newCompanies
            }
        }
    }

    fun onAddCompanyClicked() {
        viewModelScope.launch {
            val newCompany = async { repository.fetchCompanyInfo("Google", "google.com") }.await()
            _company.value = newCompany

            delay(2000)
        }
        fetchCompanyListParallel()
    }

    /**
     * Fetches company list in parallel using async-await
     */
    private fun fetchCompanyListParallel() {
        viewModelScope.launch {
            val appleDeferred = async(Dispatchers.IO) {repository.fetchCompanyInfo("Apple", "apple.com") }
            val googleDeferred = async(Dispatchers.IO) { repository.fetchCompanyInfo("Google", "google.com") }
            val youtubeDeferred = async(Dispatchers.IO) { repository.fetchCompanyInfo("YouTube", "youtube.com") }

            val companyList = listOf(
                appleDeferred.await(),
                googleDeferred.await(),
                youtubeDeferred.await()
            )

            _companyList.value = companyList
        }
    }
}