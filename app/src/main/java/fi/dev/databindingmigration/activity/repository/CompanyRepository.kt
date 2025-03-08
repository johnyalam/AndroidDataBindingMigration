package fi.dev.databindingmigration.activity.repository

import fi.dev.databindingmigration.activity.model.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CompanyRepository {
    suspend fun fetchCompanyInfo(name: String, web: String): Company {
        return withContext(Dispatchers.IO) {
            delay(2000) // Simulate network delay
            Company(name, web)
        }
    }

    fun addCompaniesWithInterval(existingCompanies: List<Company>): Flow<List<Company>> = flow {
        var count = 0
        val maxCount = 5
        val currentList = existingCompanies.toMutableList()

        while (count < maxCount) {
            val newCompany = Company("${count}. Youtube", "youtube.com")
            currentList.add(newCompany)
            emit(currentList.toList()) // Emit a new list
            delay(1000) // Wait for 1 second
            count++
        }
    }
}