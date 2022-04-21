package de.neugelb.presentation.ui.base.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var isLoading = false
    private var previousCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy == 0) return
        val visibleItemCount = recyclerView.childCount
        val itemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
        if (dy >= 0 || firstVisibleItem == 0)
        if (isLoading) {
            isLoading = itemCount <= previousCount
        } else if (!isLoading && itemCount - firstVisibleItem - visibleItemCount < 3) {
            isLoading = true
            previousCount = itemCount
            loadMore()
        } else if (itemCount < previousCount) {
            isLoading = false
            previousCount = itemCount
        }
    }

    fun refresh() {
        previousCount = 0
    }
}