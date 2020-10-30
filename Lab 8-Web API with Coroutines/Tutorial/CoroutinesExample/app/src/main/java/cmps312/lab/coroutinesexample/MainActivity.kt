package cmps312.lab.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var job : Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn.setOnClickListener {
            job = GlobalScope.launch {
                val jumpFinished = async { jump() }
                val runFinished = launch { run() }

                Log.d(TAG, "Jump completed ${jumpFinished.await()}")
            }
            Log.d(TAG, "Outside the coroutine Thread Name ${Thread.currentThread().name}")
        }

        stopBtn.setOnClickListener{
            job.cancel()
            job.isCancelled
            job.isCompleted
        }
    }

    override fun onPause() {
        finish()
        super.onPause()
    }

    suspend fun jump() = withContext(Dispatchers.IO) {
        repeat(1000) {
            //do in background
            val imageId = resources.getIdentifier("jump${it % 12 + 1}", "drawable", packageName)
            //do it in the main

            jumpImg.setImageResource(imageId)
            delay(10)

            Log.d(TAG, "jump: $it")
        }
    }

    suspend fun run() = withContext(Dispatchers.Main) {
        repeat(1000) {
            val imageId = resources.getIdentifier("run${it % 8 + 1}", "drawable", packageName)
            withContext(Dispatchers.Main) {
                runImg.setImageResource(imageId)
            }
        }
    }

    suspend fun playDead() = withContext(Dispatchers.IO) {
        repeat(1000) {
            val imageId = resources.getIdentifier("dead${it % 8 + 1}", "drawable", packageName)

            withContext(Dispatchers.Main) {
                deadImg.setImageResource(imageId)
            }
        }
    }

}



