package com.anwesh.uiprojects.linkedleaninglineview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.leaninglineview.LeaningLineView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LeaningLineView.create(this)
    }
}
