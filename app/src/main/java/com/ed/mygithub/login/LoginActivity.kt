package com.ed.mygithub.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ed.mygithub.R
import com.ed.mygithub.reposlist.ReposActivity
import com.ed.mygithub.util.obtainViewModel
import com.ed.mygithub.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.app_bar_main.*

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.login_act)

        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.login_activity_name)

        setupViewFragment()

        viewModel = obtainViewModel()

        subscribeToNagivationChanges(viewModel)
    }

    private fun subscribeToNagivationChanges(viewModel: LoginViewModel) {
        val activity = this@LoginActivity
        viewModel.run {
            openReposEvent.observe(activity, Observer {
                openProductDetails()
            })
        }
    }

    private fun openProductDetails() {
        Intent(this, ReposActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
            ?: replaceFragmentInActivity(LoginFragment.newInstance(), R.id.contentFrame)
    }

    fun obtainViewModel(): LoginViewModel = obtainViewModel(LoginViewModel::class.java)
}