package fi.dev.databindingmigration.activity.ui_state

sealed class CompanyIntent {
    data object LoadInitialCompany : CompanyIntent()
    data object FetchCompanyList : CompanyIntent()
}