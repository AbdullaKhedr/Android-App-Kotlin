package cmps312.lab.covidtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_covidstat.view.*
import java.text.DecimalFormat

// Step 2
class CovidStatAdapter(val covidStatList: List<CovidStat>, val context: Context) :
    RecyclerView.Adapter<CovidStatAdapter.CovidStatViewHolder>() {

    // Step 1: Create the view holder
    class CovidStatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(covidStat: CovidStat) {
            itemView.apply {
                countryTv.text = covidStat.country
                populationTv.text = prettyCount(covidStat.population.toString())
                activeCasesTv.text = prettyCount(covidStat.activeCases.toString())
                totalDethTv.text = prettyCount(covidStat.totalDeaths.toString())
                totalRecoverdTv.text = prettyCount(covidStat.totalRecovered.toString())
            }
        }
    }

    // Step 4
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidStatViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.list_item_covidstat, parent, false)
        return CovidStatViewHolder(itemView)
    }

    // Step 5
    override fun onBindViewHolder(holder: CovidStatViewHolder, position: Int) {
        holder.bind(covidStatList[position])
    }

    // Step 3
    override fun getItemCount(): Int {
        return covidStatList.size
    }
}

fun prettyCount(number: String): String? {
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val numValue = number.toLong()
    val value = Math.floor(Math.log10(numValue.toDouble())).toInt()
    val base = value / 3
    return if (value >= 3 && base < suffix.size) {
        DecimalFormat("#0.00").format(numValue / Math.pow(10.0, base * 3.toDouble())) + " " + suffix[base]
    } else {
        DecimalFormat().format(numValue)
    }
}