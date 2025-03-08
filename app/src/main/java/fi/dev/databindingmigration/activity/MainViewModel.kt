package fi.dev.databindingmigration.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.dev.databindingmigration.activity.model.Company
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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
        val newCompany = Company("Youtube", "Youtube.com")
        _company.value = newCompany
    }

    // Adding company with interval of 1 second
    // Reactive data stream
    private fun addCompaniesWithInterval() : Flow<List<Company>> = flow {
        var count = 0
        val maxCount = 12

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