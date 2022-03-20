package com.infinity.omos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.infinity.omos.data.DjProfile
import com.infinity.omos.data.Profile
import com.infinity.omos.data.Record
import com.infinity.omos.data.SimpleRecord
import com.infinity.omos.etc.Constant
import com.infinity.omos.repository.MyDjRepository

class DjViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MyDjRepository = MyDjRepository()

    fun setDjProfile(fromUserId: Int, toUserId: Int){
        repository.getDjProfile(fromUserId, toUserId)
    }
    fun getDjProfile(): LiveData<DjProfile>{
        return repository.djProfile
    }

    fun saveFollow(fromUserId: Int, toUserId: Int){
        repository.saveFollow(fromUserId, toUserId)
    }
    fun getStateSaveFollow(): LiveData<Constant.ApiState>{
        return repository.stateSaveFollow
    }

    fun deleteFollow(fromUserId: Int, toUserId: Int){
        repository.deleteFollow(fromUserId, toUserId)
    }
    fun getStateDeleteFollow(): LiveData<Constant.ApiState>{
        return repository.stateDeleteFollow
    }

    fun setDjRecord(userId: Int){
        repository.getDjRecord(userId)
    }
    fun getDjRecord(): LiveData<List<SimpleRecord>>{
        return repository.djRecord
    }
    fun getStateDjRecord(): LiveData<Constant.ApiState>{
        return repository.stateDjRecord
    }
}