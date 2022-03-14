package com.infinity.omos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.infinity.omos.data.Category
import com.infinity.omos.data.Profile
import com.infinity.omos.data.Record
import com.infinity.omos.etc.Constant
import com.infinity.omos.repository.AllRecordsRepository
import com.infinity.omos.repository.MyDjRepository
import com.infinity.omos.repository.MyRecordRepository
import com.infinity.omos.utils.GlobalApplication

class SharedViewModel(application: Application): AndroidViewModel(application) {

    private val myRecordRepository: MyRecordRepository = MyRecordRepository()
    private val allRecordsRepository: AllRecordsRepository = AllRecordsRepository()
    private val myDjRepository: MyDjRepository = MyDjRepository()

    // My 레코드
    fun setMyRecord(userId: Int){
        myRecordRepository.getMyRecord(userId)
    }
    fun getMyRecord(): LiveData<List<Record>>{
        return myRecordRepository.myRecord
    }
    fun getStateMyRecord(): LiveData<Constant.ApiState>{
        return myRecordRepository.stateMyRecord
    }

    // 전체 레코드
    private var allRecords = MutableLiveData<Category>()
    fun setAllRecords(){
        allRecords = allRecordsRepository.setAllRecords()
    }
    fun getAllRecords(): LiveData<Category>{
        return allRecords
    }
    fun getStateAllRecords(): LiveData<Constant.ApiState>{
        return allRecordsRepository.stateAllRecords
    }

    // My DJ

    // DJ 리스트
    fun setMyDj(userId: Int){
        myDjRepository.getMyDj(userId)
    }
    fun getMyDj(): LiveData<List<Profile>>{
        return myDjRepository.myDj
    }
    fun getStateMyDj(): LiveData<Constant.ApiState>{
        return myDjRepository.stateMyDj
    }

    // 선택된 DJ 레코드
    private val myDjRecord = myDjRepository.myDjRecord
    fun setMyDjRecord(fromUserId: Int, toUserId: Int){
        myDjRepository.getMyDjRecord(fromUserId, toUserId)
    }
    fun getMyDjRecord(): LiveData<List<Record>>{
        return myDjRecord
    }
    fun getStateDjRecord(): LiveData<Constant.ApiState>{
        return myDjRepository.stateMyDjRecord
    }

    // 모든 DJ 레코드
    fun setDjAllRecords(userId: Int, postId: Int?, size: Int){
        myDjRepository.getMyDjAllRecords(userId, postId, size)
    }
    fun getDjAllRecords(): LiveData<List<Record>>{
        return myDjRepository.myDjAllRecords
    }
    fun getStateDjAllRecords(): LiveData<Constant.ApiState>{
        return myDjRepository.stateMyDjAllRecords
    }
}