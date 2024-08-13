package com.kevseryokuss.catchthescoobykotlin

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kevseryokuss.catchthescoobykotlin.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var score = 0
    private var timeLeft = 15
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable{}
    var handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageArray.add(binding.imageView1)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)


        hideImages()

        object : CountDownTimer(15500,1000) {

            override fun onTick(p0: Long) { //p0:kalan saniye
                binding.timeTextView.text = "Time : ${p0/1000}"
            }

            override fun onFinish() {

                binding.timeTextView.text = "TİME ENDED !! "
                handler.removeCallbacks(runnable)

                for (image in imageArray) {
                    image.visibility = View.INVISIBLE //ekranda kalan son görsel de gitsin diye

                }

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("GAME OVER! :)")
                alert.setMessage("Restart The Game?")

                alert.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                    val intentFromMain = intent
                    finish()
                    startActivity(intentFromMain)
                })

                alert.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(this@MainActivity,"GAME OVER.",Toast.LENGTH_LONG).show()
                })
                alert.show()
            }

        }.start()
    }

    fun hideImages() {

        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)
            }
        }

        handler.post(runnable)

    }


    fun scoobyClicked(view : View) {
        score = score+1
        binding.scoreTextView.text="Score : ${score}"


    }

}