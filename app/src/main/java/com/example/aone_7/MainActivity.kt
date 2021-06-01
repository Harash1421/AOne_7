package com.example.aone_7

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showContacts()
            }
        }
    }
    private fun showContacts(){
        var cur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null
        ,null,null)
        if (cur!!.count > 0){
            cur.moveToFirst()
            var list = ArrayList<String>()
            while (!cur.isAfterLast){
                list.add("Name: " + cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        + "\n" + "Phone: " + (cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))))
                cur.moveToNext()
            }
            var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
            view_List.adapter = adapter
        }
    }
}