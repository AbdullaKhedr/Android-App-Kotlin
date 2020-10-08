package com.cmps312.countryvisitsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cmps312.countryvisitsapp.R
import com.cmps312.countryvisitsapp.databinding.ListItemBinding
import com.cmps312.countryvisitsapp.model.VisitedCountry
import com.cmps312.countryvisitsapp.repository.Repository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_item.view.*

class VisitedCountryListAdapter(
    private val deleteListener: (viewHolder: RecyclerView.ViewHolder) -> Unit
) : RecyclerView.Adapter<VisitedCountryListAdapter.VisitedCountryViewHolder>() {

    var visitedCountries: MutableList<VisitedCountry> = Repository.visitedCountries
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class VisitedCountryViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(visitedCountry: VisitedCountry) {
            binding.visitedCountry = visitedCountry
            itemView.apply {
                val image = resources.getIdentifier(
                    "flag_${visitedCountry.code.toLowerCase()}",
                    "drawable",
                    context.packageName
                )
                flagImageView.setImageResource(image)
            }

            itemView.deletBtn.setOnClickListener {
                deleteListener(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitedCountryViewHolder {
        val binding: ListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item,
            parent,
            false
        )

        return VisitedCountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VisitedCountryViewHolder, position: Int) {
        holder.bind(visitedCountries[position])
    }

    override fun getItemCount() = visitedCountries.size

    fun deleteCountry(viewHolder: RecyclerView.ViewHolder) {
        // Get the position of the item that was swiped
        val position = viewHolder.adapterPosition
        val deletedCountry = visitedCountries[position]

        //Remove from filtered list
        visitedCountries.removeAt(position)

        // Inform the RecyclerView adapter that an item has been removed at a specific position.
        notifyItemRemoved(position)

        Snackbar.make(viewHolder.itemView, "${deletedCountry.name} removed", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                visitedCountries.add(position, deletedCountry)
                visitedCountries.add(deletedCountry)
                notifyItemInserted(position)
            }.show()
    }

}