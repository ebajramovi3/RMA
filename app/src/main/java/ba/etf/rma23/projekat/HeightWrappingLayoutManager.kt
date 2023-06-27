package ba.etf.rma23.projekat

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HeightWrappingLayoutManager(context: Context, orientation: Int) : LinearLayoutManager(context, orientation, false) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        calculateItemPositions(recycler)
    }

    private fun calculateItemPositions(recycler: RecyclerView.Recycler?) {
        val itemCount = itemCount
        var left = paddingLeft
        val top = paddingTop

        for (i in 0 until itemCount) {
            val view = recycler?.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view!!, 0, 0)

            val right = left + getDecoratedMeasuredWidth(view)
            val bottom = top + getDecoratedMeasuredHeight(view)
            layoutDecorated(view, left, top, right, bottom)

            left += getDecoratedMeasuredWidth(view)
        }
    }
}