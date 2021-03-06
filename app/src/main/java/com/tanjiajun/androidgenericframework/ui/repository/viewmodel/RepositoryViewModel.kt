package com.tanjiajun.androidgenericframework.ui.repository.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryData
import com.tanjiajun.androidgenericframework.data.repository.GitHubRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel
import com.tanjiajun.androidgenericframework.ui.UIState

/**
 * Created by TanJiaJun on 2020-02-07.
 */
class RepositoryViewModel(
        private val repository: GitHubRepository
) : BaseViewModel() {

    private val _isShowRepositoryView = MutableLiveData<Boolean>()
    val isShowRepositoryView: LiveData<Boolean> = _isShowRepositoryView

    private val _repositories = MutableLiveData<List<RepositoryData>>()
    val repositories: LiveData<List<RepositoryData>> = _repositories

    fun getRepositories(languageName: String) =
            launch(
                    uiState = UIState(isShowLoadingView = true, isShowErrorView = true),
                    block = { repository.getRepositories(languageName) },
                    success = {
                        if (it.isNotEmpty()) {
                            _repositories.value = it
                            _isShowRepositoryView.value = true
                        }
                    }
            )

}