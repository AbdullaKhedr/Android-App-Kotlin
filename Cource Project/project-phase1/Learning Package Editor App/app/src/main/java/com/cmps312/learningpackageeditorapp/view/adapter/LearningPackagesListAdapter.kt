package com.cmps312.learningpackageeditorapp.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.common.General
import com.cmps312.learningpackageeditorapp.databinding.ListItemLearningPackageBinding
import com.cmps312.learningpackageeditorapp.model.LearningPackage
import com.cmps312.learningpackageeditorapp.repository.Repository

class LearningPackagesListAdapter(
    private val deleteListener: (learningPackage: LearningPackage) -> Unit,
    private val editListener: (viewHolder: RecyclerView.ViewHolder) -> Unit
) : RecyclerView.Adapter<LearningPackagesListAdapter.PackageListViewHolder>() {

    var learningPackages = listOf<LearningPackage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PackageListViewHolder(private val binding: ListItemLearningPackageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(learningPackage: LearningPackage) {
            binding.learningPackage = learningPackage
            binding.apply {
                deleteBTN.setOnClickListener {
                    deleteListener(learningPackage)
                }
                editBTN.setOnClickListener {
                    editListener(this@PackageListViewHolder)
                }
                learningPackage.media?.let{
                    if (learningPackage.mediaType == General.ResourceTypeEnum.IMAGE)
                        packageImageView.setImageURI(Uri.parse(it))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageListViewHolder {
        val binding: ListItemLearningPackageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_learning_package,
            parent,
            false
        )
        return PackageListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PackageListViewHolder, position: Int) {
        holder.bind(learningPackages[position])
    }

    override fun getItemCount() = learningPackages.size

    fun editLearningPackage(viewHolder: RecyclerView.ViewHolder): LearningPackage {
        val position = viewHolder.adapterPosition
        return learningPackages[position]
    }

    fun searchFilter(searchText: String): Boolean {
        learningPackages = if (searchText.isEmpty())
            Repository.learningPackage
        else {
            Repository.learningPackage.filter {
                it.title.contains(searchText, true) or
                        it.level.contains(searchText, true) or
                        it.keyWords.any { keyWord -> keyWord.contains(searchText, true) }
            }.toMutableList()
        }

        notifyDataSetChanged()
        return true
    }

    fun updateRv() {
        notifyDataSetChanged()
    }
}

