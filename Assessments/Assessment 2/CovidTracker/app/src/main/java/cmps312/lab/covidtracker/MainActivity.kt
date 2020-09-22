package cmps312.lab.covidtracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar as Toolbar) // Setup the Toolbar
        initRecyclerView() // Read the data from the repository then set the Recycle view adapter

        // Handel the search part
        val countryAdapter = covidStatsRv.adapter as CovidStatAdapter
        (searchbar as SearchView).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String) = false
                override fun onQueryTextChange(selectedCountry: String): Boolean {
                    countryAdapter.countryFilter(selectedCountry)
                    return true
                }
            }
        )
    }

    private fun initRecyclerView() {
        CovidStatRepository.initCovidStat(this)

        covidStatsRv.apply {
            adapter = CovidStatAdapter(CovidStatRepository.covidStat, context)
            layoutManager = LinearLayoutManager(context)
        }
    }

    // Option menu setup
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    // Option menu options (sort Options)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val covidStatRecyclerAdapter = covidStatsRv.adapter as CovidStatAdapter
        val sortBy = when (item.itemId) {
            R.id.sort_by_country -> SortBy.COUNTRY
            R.id.sort_by_active_cases -> SortBy.ACTIVE_CASES
            R.id.sort_by_population -> SortBy.POPULATION
            R.id.sort_by_total_deaths -> SortBy.TOTAL_DETHS
            else -> null
        }

        return if (sortBy == null) {
            super.onOptionsItemSelected(item)
        } else {
            covidStatRecyclerAdapter.sort(sortBy!!)
            true
        }
    }
}