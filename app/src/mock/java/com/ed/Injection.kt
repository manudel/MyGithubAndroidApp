package com.ed

import android.content.Context
import com.ed.data.FakeGitHubRemoteDataSource
import com.ed.mygithub.data.source.GitHubRepository


/**
 * Enables injection of mock implementations for
 * [GitHubDataSource] at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
object Injection {

    fun provideGitHubRepository(context: Context): GitHubRepository {
        return GitHubRepository.getInstance(FakeGitHubRemoteDataSource)
    }
}