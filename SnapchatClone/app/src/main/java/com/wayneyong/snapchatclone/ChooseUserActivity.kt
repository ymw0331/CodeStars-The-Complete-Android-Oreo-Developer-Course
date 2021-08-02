package com.wayneyong.snapchatclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ChooseUserActivity : AppCompatActivity() {

    var chooseUserListView: ListView? = null
    var emails: ArrayList<String> = ArrayList()
    var keys: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
        setTitle("Choose User Activity")

        chooseUserListView = findViewById(R.id.chooseUserListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emails)
        chooseUserListView?.adapter = adapter

        //refer to realtime database that is created, when new user is cleaned or destroyed
        FirebaseDatabase.getInstance("https://snapchat-clone-1f8f2-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference().child("users").addChildEventListener(object :
                ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    //add email to arrayList (users folder)
                    val email = snapshot.child("email").value as String //get value
                    emails.add(email)
                    keys.add(snapshot.key.toString())
                    adapter.notifyDataSetChanged()

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}

            })


        chooseUserListView?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->

                //refer to firebase realtime to know what it is
                val snapMap: Map<String, String?> =
                    mapOf(
                        "from" to FirebaseAuth.getInstance().currentUser!!.email!!,
                        "imageName" to intent.getStringExtra("imageName"),
                        "imageURL" to intent.getStringExtra("imageURL"),
                        "message" to intent.getStringExtra("message")
                    )

                FirebaseDatabase.getInstance("https://snapchat-clone-1f8f2-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference()
                    .child("users").child(keys.get(position)).child("snaps").push()
                    .setValue(snapMap)

                val intent = Intent(this, SnapsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) //clear everything at back button history keep us at where we are currently at
                startActivity(intent)

            }


    }
}