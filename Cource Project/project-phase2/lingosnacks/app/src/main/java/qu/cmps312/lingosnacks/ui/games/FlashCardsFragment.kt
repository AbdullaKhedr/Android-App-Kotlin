package qu.cmps312.lingosnacks.ui.games

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.android.synthetic.main.fragment_flash_cards.*
import kotlinx.coroutines.launch
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.Resource
import qu.cmps312.lingosnacks.model.ResourceTypeEnum
import qu.cmps312.lingosnacks.model.Sentence
import qu.cmps312.lingosnacks.repositories.PackageRepository
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel
import java.io.File


class WordInfo(val word: String, val sentence: Sentence, val type: String = "S")

class FlashCardsFragment : Fragment(R.layout.fragment_flash_cards) {
    private val packageViewModel by activityViewModels<PackageViewModel>()
    private var currentIndex = 0
    private lateinit var repo: PackageRepository
    private lateinit var sentences: List<WordInfo>
    private val previousButtons = mutableListOf<ImageButton>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedPackage = packageViewModel.selectedPackage!!
        repo = PackageRepository(requireContext())
        // Merge all into 1 list of sentences
        sentences =
            selectedPackage.words.flatMap { word ->
                word.sentences.map { WordInfo(word.text, it) }
            } + selectedPackage.words.flatMap { word ->
                word.definitions.map { WordInfo(word.text, Sentence(it.text), "D") }
            }

        displaySentence(currentIndex)
        nextBtn.setOnClickListener { onMoveNext(it) }
        prevBtn.setOnClickListener { onMoveNext(it) }
    }

    private fun onMoveNext(view: View) {
        imageView.isVisible = false
        videoView.isVisible = false
        webView.isVisible = false

        when (view.id) {
            R.id.nextBtn -> ++currentIndex
            R.id.prevBtn -> --currentIndex
            else -> return
        }
        //If current index becomes == count then it will be assigned 0
        currentIndex = (currentIndex + sentences.size) % sentences.size
        displaySentence(currentIndex)
    }

    private fun displaySentence(index: Int) {
        currentIndexTv.text = "${index + 1} of ${sentences.size}"
        definitionEt.text = sentences[index].word
        val sentence =
            if (sentences[index].type == "D") "Definition: ${sentences[index].sentence.text}" else sentences[index].sentence.text
        sentenceTv.text = sentence

        previousButtons.forEach {
            mainLayout.removeView(it)
        }
        previousButtons.clear()
        sentences[index].sentence.resources.forEachIndexed { index, resource ->
            println(">> Debug: ${resource.title}")
            val imgBtn = createButton(resource)
            imgBtn.setOnClickListener {
                Toast.makeText(requireContext(), "${resource.title}", Toast.LENGTH_SHORT).show()
                when (resource.type) {
                    ResourceTypeEnum.Photo -> displayPhoto(resource.url)
                    ResourceTypeEnum.Video -> playVideo(resource.url)
                    ResourceTypeEnum.Website -> viewPage(resource.url)
                }
            }
            mainLayout.addView(imgBtn)
            mediaFlow.addView(imgBtn)
            if (index == 0) imgBtn.performClick()
            previousButtons.add(imgBtn)
        }
    }

    private fun displayPhoto(resourceUrl: String) {
        webView.isVisible = false
        videoView.isVisible = false
        imageView.isVisible = true
        if(resourceUrl.contains("http"))
            imageView.load(resourceUrl)
        else {
            val dir = requireContext().getDir("media", Context.MODE_PRIVATE)
            val imgFile = File(dir, resourceUrl)

            if (imgFile.exists()) {
                val img = BitmapFactory.decodeFile(imgFile.absolutePath)
                imageView.setImageBitmap(img)
            } else {
                lifecycleScope.launch {
                    imageView.load(repo.fireBaseLoadFile(resourceUrl))
                }
            }
        }
    }

    private fun playVideo(resourceUrl: String) {
        webView.isVisible = false
        videoView.isVisible = true
        imageView.isVisible = false

        try {
            val mediaController = MediaController(requireContext())
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            if(resourceUrl.contains("http"))
                videoView.setVideoPath(resourceUrl)
            else {
                val dir = requireContext().getDir("media", Context.MODE_PRIVATE)
                val vidFile = File(dir, resourceUrl)

                if (vidFile.exists()) {
                    videoView.setVideoPath(vidFile.absolutePath)
                } else {
                    lifecycleScope.launch {
                        videoView.setVideoPath(repo.fireBaseLoadFile(resourceUrl))
                    }
                }
            }

            videoView.requestFocus()
            videoView.start()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error loading video ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun viewPage(resourceUrl: String) {
        webView.isVisible = true
        videoView.isVisible = false
        imageView.isVisible = false
        try {
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        url: String
                ): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webView.loadUrl(resourceUrl)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error loading page ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun createButton(resource: Resource): ImageButton {
        val imageBtn = ImageButton(requireContext())

        val iconId = when (resource.type) {
            ResourceTypeEnum.Photo -> R.drawable.ic_photo
            ResourceTypeEnum.Video -> R.drawable.ic_video
            ResourceTypeEnum.Website -> R.drawable.ic_link
        }
        val photoIcon = ContextCompat.getDrawable(requireContext(), iconId)
        imageBtn.setImageDrawable(photoIcon)
        imageBtn.setPadding(25, 25, 25, 25)
        imageBtn.id = View.generateViewId()
        return imageBtn
    }
}
