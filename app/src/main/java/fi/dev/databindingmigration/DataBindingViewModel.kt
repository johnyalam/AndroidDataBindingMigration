package fi.dev.databindingmigration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataBindingViewModel : ViewModel()  {

    val company = MutableLiveData<Company>()

    fun onAddCompanyClicked() {
        val newCompany = Company("Google", "google.com")
        company.value = newCompany
        Log.d("Company", "Company added")
    }
}