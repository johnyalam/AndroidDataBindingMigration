package fi.dev.databindingmigration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel : ViewModel()  {
    private val _company = MutableStateFlow<Company?>(null)
    val company: StateFlow<Company?> = _company

    fun onAddCompanyClicked() {
        val newCompany = Company("Google", "google.com")
        _company.value = newCompany
    }
}