package com.zybooks.DnDPlayerCompanion

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.zybooks.DnDPlayerCompanion.model.DiceRoll
import kotlin.math.abs

class DiceActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var dice: DiceRoll
    private lateinit var diceImageView: ImageView
    private lateinit var diceTotal: TextView
    private lateinit var isProfBox: CheckBox
    private var timer: CountDownTimer? = null
    private var last_x: Float = 0f
    private var last_y:Float = 0f
    private var last_z:Float = 0f
    private var lastUpdate: Long = 0L
    private val SHAKE_THRESHOLD = 10f
    private var mediaPlayer: MediaPlayer? = null
    private var charLevel: Int = 0
    private var skillValue: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_roll)

        //get intents
        skillValue = intent.getIntExtra("EXTRA_CHARACTER_SKILL", 3)
        charLevel = intent.getIntExtra("EXTRA_CHARACTER_LEVEL", 0)
        //create Textview and CheckBox
        diceTotal = findViewById(R.id.DiceTotalTXTView)
        isProfBox = findViewById(R.id.ProfCheckBox)
        //create die
        dice = DiceRoll(1)

        // Create Dice Image
        diceImageView = findViewById(R.id.dice)

        showDice()

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        mediaPlayer = MediaPlayer.create(this, R.raw.shakedice)
    }

    private fun showDice() {

        // Show Dice Image
        val diceDrawable = ContextCompat.getDrawable(this, dice.imageId)
        diceImageView.setImageDrawable(diceDrawable)
    }

    private fun rollDice() {
        timer?.cancel()

        // Start a timer that periodically changes Dice image in correlation with dice value
        timer = object : CountDownTimer(1000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                dice.roll()
                showDice()
                mediaPlayer?.start()
            }

            override fun onFinish() {
                timer?.cancel()
                val rollTot: Int = dice.number + getProfBonus() + getSkillValue()
                diceTotal.text = "Your Roll Total is: $rollTot"
            }
        }.start()
    }

    private fun getSkillValue(): Int {
        return ((skillValue - 10) / 2)
    }

    fun getProfBonus(): Int {
        if (isProfBox.isActivated) {
            if (charLevel < 5) {
                return 2
            }
            else if (charLevel < 9)
            {
                return 3
            }
            else if (charLevel < 13)
            {
                return 4
            }
            else if (charLevel < 17)
            {
                return 5
            }
            else
            {
                return 6
            }
        }
        else {
            return 0
        }
    }

    fun onClick(view: View) {
        rollDice()
    }

    override fun onSensorChanged(event: SensorEvent) {
        //https://stackoverflow.com/questions/30228586/android-studio-gyroscope-examples
        //ensure right type of event
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            //get current position values after shake
            var x = event.values[0];
            var y = event.values[1];
            var z = event.values[2];

            //store the time event occurs
            var currTime = System.currentTimeMillis();

            //check if event occurred after a certain period of time
            if ((currTime - lastUpdate) >= 10L) {
                //get the time difference between last shake
                var diffTime = (currTime - lastUpdate);
                //store currtime for next event
                lastUpdate = currTime;

                //determine speed of shake
                var speed = abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                //if shake was fast enough decide that the user wants to roll the dice
                if (speed > SHAKE_THRESHOLD) {
                    rollDice()
                }

                //store last used position values
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, accelerometer)
    }

}