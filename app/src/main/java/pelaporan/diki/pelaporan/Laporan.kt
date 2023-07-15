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
import kotlinx.android.synthetic.main.activity_laporan.*

import okhttp3.MediaType

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Laporan : AppCompatActivity() {
    lateinit var _nama : EditText
    lateinit var _hp : EditText
    lateinit var _detail : EditText
    lateinit var _metadata: EditText

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
        val metaData: String = bundle.getString("metadata") as String
        lokasiFile = resId
        val imgFile: File =  File(resId)
        if(imgFile.exists()){
            val myBitmap: Bitmap = BitmapFactory.decodeFile(imgFile.toString())
            imageView.setImageBitmap(myBitmap)
            txtMeta.setText(metaData)


        }
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
        _nama = findViewById(R.id.txtName)
        _hp = findViewById(R.id.txtHp)
        _detail = findViewById(R.id.txtDetail)
        _metadata = findViewById(R.id.txtMeta)

        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(lokasiFile)

        val outputStream = FileOutputStream(file)

        val body = UploadRequestBody(file,"image", this)
        MyApi().uploadImage(
            MultipartBody.Part.createFormData(
            "gambar",
            file.name,

            body
        ),
            RequestBody.create(MediaType.parse("multipart/form-data"),_nama.text.toString()),
            RequestBody.create(MediaType.parse("multipart/form-data"),_hp.text.toString()),
            RequestBody.create(MediaType.parse("multipart/form-data"),_detail.text.toString()),
            RequestBody.create(MediaType.parse("multipart/form-data"),_metadata.text.toString())
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