package com.cmps312.learningpackageeditorapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.model.Word
import kotlinx.android.synthetic.main.word_list_item.view.*

class WordsAdapter(
    private val deleteListener: (word: Word) -> Unit,
    private val clickListener: (word: Word) -> Unit
) : RecyclerView.Adapter<WordsAdapter.WordsListViewHolder>() {

    var words = listOf<Word>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class WordsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            itemView.apply {
                wordTv.text = word.text
                sentencesNumTv.text = "${word.sentences.size} Sentences"
                definitionsNumTv.text = "${word.definitions.size} Definitions"
            }
            itemView.deleteWordBtn.setOnClickListener {
                deleteListener(word)
            }
            itemView.setOnClickListener {
                clickListener(word)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.word_list_item, parent, false)
        return WordsListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordsListViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount() = words.size

}