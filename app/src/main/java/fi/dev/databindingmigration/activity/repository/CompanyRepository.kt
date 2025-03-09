package fi.dev.databindingmigration.activity.repository

import fi.dev.databindingmigration.activity.model.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CompanyRepository : CompanyRepositoryImp {
    override suspend fun fetchCompanyInfo(name: String, web: String): Company {
        return withContext(Dispatchers.IO) {
            delay(2000) // Simulate network delay
            Company(name, web)
        }
    }
}