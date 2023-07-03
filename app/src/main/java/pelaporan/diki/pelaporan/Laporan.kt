package pelaporan.diki.pelaporan

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.diki.pelaporan.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class Laporan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

        val imageView: ImageView = findViewById(R.id.imageView)
        val nama : EditText = findViewById(R.id.editTextTextPersonName3)
        val bundle: Bundle? = intent.extras!!
        //val resId: Bitmap = bundle?.getInt("resId") as Bitmap
        val resId: String = bundle?.getString("resId") as String

        val imgFile: File =  File(resId)
        if(imgFile.exists()){
            val myBitmap: Bitmap = BitmapFactory.decodeFile(imgFile.toString())
            imageView.setImageBitmap(myBitmap)


        }
        nama.setText(resId)
        //val imageBitmap = bundle?.get("resId") as Bitmap
        //imageView.setImageBitmap(imageBitmap)

    }
}