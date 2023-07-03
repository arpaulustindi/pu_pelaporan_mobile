package pelaporan.diki.pelaporan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.diki.pelaporan.R
import kotlin.collections.ArrayList

class ArcoreMeasurement : AppCompatActivity() {

    private val TAG = "ArcoreMeasurement"
    private val buttonArrayList = ArrayList<String>()
    private lateinit var toMeasurement: Button
    private lateinit var main_layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arcore_measurement)
        //main_layout = findViewById(R.id.main_linear)
        val buttonArray = resources
            .getStringArray(R.array.arcore_measurement_buttons)

        buttonArray.map{it->
            buttonArrayList.add(it)
        }
        toMeasurement = findViewById(R.id.to_measurement)
        toMeasurement.text = buttonArrayList[0]
        toMeasurement.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(application, Measurement::class.java)
                startActivity(intent)
            }
        })
    }




}
