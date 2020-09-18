package cmps312.lab.covidtracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        CovidStatRepository.initCovidStat(this)

        covidStatsRv.apply {
            adapter = CovidStatAdapter(CovidStatRepository.covidStat, context)
            layoutManager = LinearLayoutManager(context)
        }

    }

    ///////////////////////////////////////////Option menu/////////////////////////////////////////////
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_country -> {

            }
            R.id.sort_by_active_cases -> {

            }
            R.id.sort_by_population -> {

            }
            R.id.sort_by_total_deaths -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}