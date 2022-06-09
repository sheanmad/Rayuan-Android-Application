package com.rayuan.view.rating

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rayuan.response.ResponseRatingItem

class RatingViewModel(application: Application) : AndroidViewModel(application) {
    private val _listRating = MutableLiveData<ResponseRatingItem>()
    val listRating: LiveData<ResponseRatingItem> = _listRating
}