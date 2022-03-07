package com.infinity.omos

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.infinity.omos.databinding.ActivitySelectCategoryBinding
import com.infinity.omos.viewmodels.SelectCategoryViewModel
import kotlinx.android.synthetic.main.activity_register_nick.*
import kotlinx.android.synthetic.main.activity_register_nick.toolbar
import kotlinx.android.synthetic.main.activity_select_category.*

class SelectCategoryActivity : AppCompatActivity() {

    private val viewModel: SelectCategoryViewModel by viewModels()
    private var prevLayout: LinearLayout? = null

    private var musicId = ""
    private var musicTitle = ""
    private var artists = ""
    private var albumImageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivitySelectCategoryBinding>(this, R.layout.activity_select_category)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initToolBar()

        musicId = intent.getStringExtra("musicId")!!
        musicTitle = intent.getStringExtra("musicTitle")!!
        artists = intent.getStringExtra("artists")!!
        albumImageUrl = intent.getStringExtra("albumImageUrl")!!

        viewModel.state.observe(this, Observer { state ->
            state?.let {
                if (it == resources.getString(R.string.a_line)){
                    changeState(btn_oneline)
                } else if (it == resources.getString(R.string.ost)){
                    changeState(btn_myost)
                } else if (it == resources.getString(R.string.story)){
                    changeState(btn_mystory)
                } else if (it == resources.getString(R.string.lyrics)){
                    changeState(btn_interpret)
                } else if (it == resources.getString(R.string.free)){
                    changeState(btn_free)
                }
            }
        })
    }

    private fun changeState(layout: LinearLayout){
        if (prevLayout != null){
            prevLayout!!.background = ContextCompat.getDrawable(this, R.drawable.bg_rounded_rectangle)
            prevLayout!!.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.deep_dark))
        }

        layout.background = ContextCompat.getDrawable(this, R.drawable.rectangle_stroke_box2)
        layout.backgroundTintList = null

        prevLayout = layout
    }

    private fun initToolBar(){
        toolbar.title = ""
        setSupportActionBar(toolbar) // 툴바 사용

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_action_next, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_next -> {
                var category = viewModel.state.value
                if (category == ""){
                    Toast.makeText(this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else{
                    val intent = Intent(this, WriteRecordActivity::class.java)
                    intent.putExtra("musicId", musicId)
                    intent.putExtra("musicTitle", musicTitle)
                    intent.putExtra("artists", artists)
                    intent.putExtra("albumImageUrl", albumImageUrl)
                    intent.putExtra("category", category)
                    startActivity(intent)
                }
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}