package fi.dev.databindingmigration

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val viewModel : MainActivityViewModel by viewModels()

    private val addCompanyInfo : Button by lazy { findViewById(R.id.addCompanyInfo) }
    private val companyName : TextView by lazy { findViewById(R.id.companyName) }
    private val companyWebsite : TextView by lazy { findViewById(R.id.companyWebsite) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addCompanyInfo.setOnClickListener {
            viewModel.onAddCompanyClicked()
        }

        viewModel.company.observe(this) {
            Log.d(TAG, "Company changed")
            companyName.text = it.name
            companyWebsite.text = it.website
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}