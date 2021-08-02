package com.wayneyong.snapchatclone

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.net.HttpURLConnection
import java.net.URL

class ViewSnapActivity : AppCompatActivity() {

    var messageTextView: TextView? = null
    var snapImageView: ImageView? = null
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snap)
        setTitle("ViewSnap Activity")

        messageTextView = findViewById(R.id.messageTextView)
        snapImageView = findViewById(R.id.snapImageView)
        messageTextView?.text = intent.getStringExtra("message")

        //Access to imageDownloader
        val task = ImageDownloader()
        val myImage: Bitmap
        try {
            myImage = task.execute(intent.getStringExtra("imageURL")).get()
            snapImageView?.setImageBitmap(myImage)

        } catch (e: java.lang.Exception) {
            Log.e("onCreate", "cannot asyncTask image")
            e.printStackTrace()
        }
    }


    inner class ImageDownloader : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {

            //convert string url into new url object
            return try {
                val url = URL(urls[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val `in` = connection.inputStream
                BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("doInBackground", "Error")
                e.printStackTrace()
                return null
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        //delete database reference and storage
        FirebaseDatabase.getInstance("https://snapchat-clone-1f8f2-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference().child("users").child(mAuth.currentUser?.uid!!).child("snaps")
            .child(intent.getStringExtra("snapKey")!!).removeValue()

        FirebaseStorage.getInstance().getReference().child("images").child(intent.getStringExtra("imageName")!!).delete()

    }
}