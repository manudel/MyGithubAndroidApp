package com.ed.mygithub.reposlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ed.mygithub.R
import com.ed.mygithub.util.obtainViewModel
import com.ed.mygithub.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.app_bar_main.*

class ReposActivity : AppCompatActivity() {
    private lateinit var viewModel: ReposViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.repos_act)

        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.repos_activity_name)

        setupViewFragment()

        viewModel = obtainViewModel()
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
            ?: replaceFragmentInActivity(ReposFragment.newInstance(), R.id.contentFrame)
    }

    fun obtainViewModel(): ReposViewModel = obtainViewModel(ReposViewModel::class.java)
}