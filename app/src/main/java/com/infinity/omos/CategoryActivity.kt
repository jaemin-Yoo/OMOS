package com.infinity.omos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infinity.omos.adapters.DetailCategoryListAdapter
import com.infinity.omos.databinding.ActivityCategoryBinding
import com.infinity.omos.etc.Constant
import com.infinity.omos.utils.GlobalApplication
import com.infinity.omos.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_register.toolbar

class CategoryActivity : AppCompatActivity() {

    private val viewModel: CategoryViewModel by viewModels()

    private var ctg = ""

    private var page = 0
    private val pageSize = 5
    private var isLoading = false

    private val userId = GlobalApplication.prefs.getLong("userId").toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityCategoryBinding>(this, R.layout.activity_category)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        var category = intent.getStringExtra("category")
        initToolBar(category!!)

        // 카테고리 Record 가져오기
        when (category) {
            resources.getString(R.string.a_line) -> {
                ctg = "A_LINE"
            }
            resources.getString(R.string.ost) -> {
                ctg = "OST"
            }
            resources.getString(R.string.story) -> {
                ctg = "STORY"
            }
            resources.getString(R.string.lyrics) -> {
                ctg = "LYRICS"
            }
            else -> {
                ctg = "FREE"
            }
        }

        val mAdapter = DetailCategoryListAdapter(this)
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.loadMoreCategory(ctg, 0, pageSize, "createdDate,desc", userId)
        viewModel.category.observe(this, Observer { category ->
            category?.let {
                mAdapter.addCategory(it)
                isLoading = if (it.isEmpty()) {
                    mAdapter.deleteLoading()
                    mAdapter.notifyItemRemoved(mAdapter.itemCount-1)
                    true
                } else {
                    mAdapter.notifyItemRangeInserted(page * pageSize, it.size)
                    false
                }
            }
        })

        viewModel.stateCategory.observe(this, Observer { state ->
            state?.let {
                when(it){
                    Constant.ApiState.LOADING -> {
                        if (page == 0){
                            binding.recyclerView.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }

                    Constant.ApiState.DONE -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }

                    else -> {

                    }
                }
            }
        })

        // 무한 스크롤
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                // 스크롤이 끝에 도달했는지 확인
                if (!binding.recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount && !isLoading) {
                    isLoading = true
                    mAdapter.deleteLoading()
                    viewModel.loadMoreCategory(ctg, ++page*pageSize, pageSize, "createdDate,desc", userId)
                }
            }
        })
    }

    private fun initToolBar(category: String){
        toolbar.title = category
        setSupportActionBar(toolbar) // 툴바 사용

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_action_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }

            R.id.action_sort -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}