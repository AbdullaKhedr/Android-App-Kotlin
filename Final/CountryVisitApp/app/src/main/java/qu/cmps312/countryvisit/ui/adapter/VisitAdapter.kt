package qu.cmps312.countryvisit.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.visit_item.view.*
import qu.cmps312.countryvisit.R
import qu.cmps312.countryvisit.databinding.VisitItemBinding
import qu.cmps312.countryvisit.model.Visit

fun Spinner.setValue(value: String) {
    var spinnerAdapter = this.adapter as ArrayAdapter<String>
    val position = spinnerAdapter.getPosition(value)
    if (position >= 0)
        this.setSelection(position)
}

class VisitAdapter(
    private val deleteListener: (Visit) -> Unit,
    private val updateListener: (Visit) -> Unit
) : RecyclerView.Adapter<VisitAdapter.VisitViewHolder>() {

    var visits = listOf<Visit>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class VisitViewHolder(private val binding: VisitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(visit: Visit) {
            binding.visit = visit
            itemView.apply { }

            //ToDo: fill the continent and country spinners
            // ToDo: display the visit details in case of edit
            itemView.deleteBtn.setOnClickListener {
                deleteListener(visit)
            }

            itemView.updateBtn.setOnClickListener {
                updateListener(visit)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitViewHolder {
        val binding: VisitItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.visit_item,
            parent,
            false
        )
        return VisitViewHolder(binding)
    }

    override fun onBindViewHolder(visitViewHolder: VisitViewHolder, position: Int) {
        visitViewHolder.bind(visits[position])
    }

    override fun getItemCount() = visits.size
}