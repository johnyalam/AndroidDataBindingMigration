package fi.dev.databindingmigration.activity.repository

import fi.dev.databindingmigration.activity.model.Company

interface CompanyRepositoryImp {
    suspend fun fetchCompanyInfo(name: String, web: String): Company
}