package com.infinity.omos.ui.music.search.pager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.infinity.omos.R
import com.infinity.omos.data.music.album.AlbumModel
import com.infinity.omos.ui.Dimens
import com.infinity.omos.ui.music.search.MusicSearchViewModel
import com.infinity.omos.ui.theme.OmosTheme
import com.infinity.omos.ui.theme.grey_03
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber

@Composable
fun AlbumListScreen(
    modifier: Modifier = Modifier,
    viewModel: MusicSearchViewModel = hiltViewModel(),
    itemCount: Int = -1,
    onItemClick: (String) -> Unit,
    onMoreClick: () -> Unit = {}
) {
    val state = viewModel.searchState.collectAsState().value
    LaunchedEffect(state) {
        Timber.d("Update paging items")
    }

    AlbumListScreen(
        modifier = modifier,
        albumStream = viewModel.albumStream,
        itemCount = itemCount,
        onItemClick = onItemClick,
        onMoreClick = onMoreClick
    )
}

@Composable
fun AlbumListScreen(
    modifier: Modifier = Modifier,
    albumStream: Flow<PagingData<AlbumModel>>,
    itemCount: Int = -1,
    onItemClick: (String) -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    val pagingItems: LazyPagingItems<AlbumModel> = albumStream.collectAsLazyPagingItems()
    val (count, isVisibleMore) = if (itemCount == -1) {
        pagingItems.itemCount to false
    } else {
        itemCount to true
    }
    LazyColumn(modifier = modifier) {
        item {
            PageHeader(
                title = stringResource(id = R.string.album),
                isVisibleMore = isVisibleMore,
                onMoreClick = onMoreClick
            )
        }
        items(
            count = Integer.min(count, pagingItems.itemCount),
            key = { index ->
                val album = pagingItems[index]
                "${album?.albumId ?: ""}${index}"
            }
        ) { index ->
            val album = pagingItems[index] ?: return@items
            AlbumListItem(
                album = album,
                modifier = modifier
            ) {
                onItemClick(album.albumId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun AlbumListItem(
    album: AlbumModel,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .padding(horizontal = Dimens.PaddingNormal)
            .padding(bottom = Dimens.PaddingNormal),
        onClick = onClick,
        color = MaterialTheme.colorScheme.background
    ) {
        Row {
            GlideImage(
                modifier = Modifier
                    .size(68.dp),
                model = album.albumImageUrl,
                contentDescription = stringResource(id = R.string.album_cover)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 12.dp)
                    .weight(1f),
            ) {
                Text(
                    text = album.albumTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = album.artists,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = album.releaseDate,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = grey_03
                )
            }
        }
    }
}

@Preview
@Composable
fun AlbumListScreenPreview() {
    OmosTheme {
        AlbumListScreen(albumStream = emptyFlow())
    }
}