package qu.cmps312.lingosnacks.ui.editor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.resource_editor_item.view.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Resource
import qu.cmps312.lingosnacks.model.ResourceTypeEnum
import qu.cmps312.lingosnacks.repositories.PackageRepository

class ResourceEditorAdapter(val resources: MutableList<Resource>, repository: PackageRepository) :
    RecyclerView.Adapter<ResourceEditorAdapter.ResourceViewHolder>() {
    private val packageRepository = repository
    inner class ResourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resource: Resource) {
            itemView.titleEt.setText(resource.title)
            itemView.urlEt.setText(resource.url)
            initResourceTypeSpinner(itemView.typeSp, itemView.context)
            itemView.typeSp.setValue(resource.type.toString())

            itemView.titleEt.doOnTextChanged { text, _, _, _ ->
                resources[adapterPosition].title = text.toString()
            }

            itemView.urlEt.doOnTextChanged { text, _, _, _ ->
                resources[adapterPosition].url = text.toString()
            }

            itemView.typeSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedType = itemView.typeSp.selectedItem.toString()
                    resources[adapterPosition].type = enumValueOf<ResourceTypeEnum>(selectedType)
                }
            }

            itemView.deleteBtn.setOnClickListener {
                val position = adapterPosition
                resources.removeAt(position)
                this@ResourceEditorAdapter.packageRepository.deleteMedia(resource)
                notifyItemRemoved(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ResourceViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.resource_editor_item, parent, false)
    )

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) =
        holder.bind(resources[position])

    override fun getItemCount() = resources.size

    private fun initResourceTypeSpinner(spinner: Spinner, context: Context) {
        val resourceTypes = ResourceTypeEnum.values().map {
            it.toString()
        }.toTypedArray()

        val adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_dropdown_item_1line, resourceTypes
        )
        spinner.adapter = adapter
    }
}