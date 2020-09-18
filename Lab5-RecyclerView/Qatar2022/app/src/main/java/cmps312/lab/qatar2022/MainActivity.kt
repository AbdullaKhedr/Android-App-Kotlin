package cmps312.lab.qatar2022

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmps312.lab.qatar2022.model.StadiumRepository
import com.example.qatar2022.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_stadium.*

class MainActivity : AppCompatActivity() {

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StadiumRepository.initStadiums(this)

        update(counter)

        nextBtn.setOnClickListener {
            if (counter < StadiumRepository.stadiums.size)
                counter++
            update(counter)
        }

        prevBtn.setOnClickListener {
            if (counter >= 0)
                counter--
            update(counter)
        }

        currentIndexTv.text = "${counter + 1} of ${StadiumRepository.stadiums.size}"
    }

    fun updateCounter(action: Int) {
        if (action == -1) {
            counter--
        } else if (action == 1) {
            counter++
        }
    }

    fun update(counter: Int) {
        StadiumRepository.apply {
            nameTv.text = stadiums[counter].name
            cityTv.text = stadiums[counter].city
            statusTv.text = stadiums[counter].status

            val imageName = resources.getIdentifier(
                stadiums[counter].image,
                "drawable",
                packageName
            )
            stadiumIv.setImageResource(imageName)
        }
    }

}