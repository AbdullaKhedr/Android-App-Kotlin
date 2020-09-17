package cmps312.qatar2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stadium_item)

        val stadiums = StadiumRepository.initStadiums(this)

        stadiumRv.adapter = Adapter(stadiums)
        stadiumRv.layoutManager = LinearLayoutManager(this) // for table use GridLayoutManager
    }
}