package br.com.ramirosneto.github.repos.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.ramirosneto.github.repos.app.R
import br.com.ramirosneto.github.repos.app.domain.usecase.GetPullRequestsUseCase
import br.com.ramirosneto.github.repos.app.domain.usecase.GetRepositoriesUseCase
import br.com.ramirosneto.github.repos.app.presentation.model.PullRequestDTO
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class GitHubRepositoriesViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val getPullRequestsUseCase: GetPullRequestsUseCase
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val _repoErrorState = BehaviorSubject.create<String>()
    val repoErrorState: Observable<String> = _repoErrorState

    private val _pullRequests = BehaviorSubject.create<List<PullRequestDTO>>()
    val pullRequests: Observable<List<PullRequestDTO>> = _pullRequests.hide()

    val repositories = getRepositoriesUseCase.invoke().flow.cachedIn(viewModelScope)

    fun loadPullRequests(owner: String, repo: String) {
        disposables.add(
            getPullRequestsUseCase(owner, repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { pullRequests ->
                        _pullRequests.onNext(pullRequests)
                        _repoErrorState.onNext("")
                    },
                    { error ->
                        _repoErrorState.onNext(error.message ?: "Unknown error")
                        _pullRequests.onNext(emptyList())
                    }
                )
        )
    }

    fun clearPullRequests() {
        _pullRequests.onNext(emptyList())
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}