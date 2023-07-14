package pelaporan.diki.pelaporan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.diki.pelaporan.R
import okhttp3.MultipartBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


import android.os.Environment

import com.google.android.material.snackbar.Snackbar

import okhttp3.MediaType

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Laporan : AppCompatActivity() {
    private lateinit var buttonKirim: Button
    private  lateinit var lokasiFile: String
    private var selectedImageUri: Uri? = null
    companion object{
        private const val IMAGE_PICK_CODE = 999
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

        val imageView: ImageView = findViewById(R.id.imageView)
        val nama : EditText = findViewById(R.id.txtName)
        val bundle: Bundle? = intent.extras!!
        //val resId: Bitmap = bundle?.getInt("resId") as Bitmap
        val resId: String = bundle?.getString("resId") as String
        lokasiFile = resId
        val imgFile: File =  File(resId)
        if(imgFile.exists()){
            val myBitmap: Bitmap = BitmapFactory.decodeFile(imgFile.toString())
            imageView.setImageBitmap(myBitmap)


        }
        nama.setText(resId)
        //val imageBitmap = bundle?.get("resId") as Bitmap
        //imageView.setImageBitmap(imageBitmap)
        buttonKirim = findViewById(R.id.btnKirim)
        buttonKirim.setOnClickListener{
            //PRINT--
            println("0  Klik Upload")
            uploadImage()
        }


    }

    private fun uploadImage() {

        /*if (selectedImageUri == null) {
            layout_root.snackbar("Select an Image First")
            return
        }*/

        /*val parcelFileDescriptor = contentResolver.openFileDescriptor(
            selectedImageUri!!, "r", null
        ) ?: return*/

        //val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(lokasiFile)

        //PRINT--
        println("1 FIle : $lokasiFile")

        val outputStream = FileOutputStream(file)
        //inputStream.copyTo(outputStream)
        //progress_bar.progress = 0
        val body = UploadRequestBody(file,"image", this)
        MyApi().uploadImage(
            MultipartBody.Part.createFormData(
            "gambar",
            file.name,

            body
        ),
            RequestBody.create(MediaType.parse("multipart/form-data"),"[{\n" +
                    "  \"Test1\": {\n" +
                    "    \"Val1\": \"37\",\n" +
                    "    \"Val2\": \"25\"\n" +
                    "  }\n" +
                    "}, {\n" +
                    "  \"Test2\": {\n" +
                    "    \"Val1\": \"25\",\n" +
                    "    \"Val2\": \"27\"\n" +
                    "  }\n" +
                    "}]")
        ).enqueue(object : Callback<UploadResponse>{
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                response.body()?.let {
                    //layout_root.snackbar(it.message)
                    //progress_bar.progress = 100
                    println("Berhasil : $it.message")
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                //layout_root.snackbar(t.message!!)
                //progress_bar.progress = 0
                println("Kesalahan API : $t.message!!")
            }

        })
        //PRINT--
        println("2 Enque : Di enque")

    }


}