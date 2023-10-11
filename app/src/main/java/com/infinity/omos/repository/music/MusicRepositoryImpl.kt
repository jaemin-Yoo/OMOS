package com.infinity.omos.repository.music

import androidx.paging.PagingData
import com.infinity.omos.data.music.Music
import com.infinity.omos.data.music.MusicTitle
import com.infinity.omos.source.remote.music.MusicRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicRemoteDataSource: MusicRemoteDataSource
) : MusicRepository {

    override suspend fun getMusicTitle(keyword: String): Result<List<MusicTitle>> {
        return musicRemoteDataSource.getMusicTitle(keyword)
    }

    override fun getMusicStream(keyword: String): Flow<PagingData<Music>> {
        return musicRemoteDataSource.getMusicStream(keyword)
    }
}