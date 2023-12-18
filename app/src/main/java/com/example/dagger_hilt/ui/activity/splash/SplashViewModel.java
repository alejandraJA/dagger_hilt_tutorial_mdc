package com.example.dagger_hilt.ui.activity.splash;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dagger_hilt.domain.ApiRepository;
import com.example.dagger_hilt.sys.util.Constants;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SplashViewModel extends ViewModel {
    ApiRepository apiRepository;

    @Inject
    public SplashViewModel(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public MutableLiveData<Constants.StatusResponse> loadMovies() {
        var result = new MutableLiveData<Constants.StatusResponse>();
        var executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->
                apiRepository.loadMovies(errorResource ->
                        result.postValue(errorResource.getStatus())));
        return result;
    }
}
