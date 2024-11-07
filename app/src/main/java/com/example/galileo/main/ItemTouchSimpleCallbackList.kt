package com.example.galileo.main

import android.graphics.Canvas
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.galileo.R
import com.example.galileo.adpter.Move_UserAdapter_main
import com.example.galileo.adpter.Move_UserAdapter_sub2_postlist
import com.example.galileo.dataClass.Move_user
import com.example.galileo.dataClass.Post_list_01
import java.util.Collections
import kotlin.math.max
import kotlin.math.min

class ItemTouchSimpleCallbackList: ItemTouchHelper.SimpleCallback(
ItemTouchHelper.UP or ItemTouchHelper.DOWN,
ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
) {

    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f

    // 삭제 버튼 width를 넣을 값
    private var clamp = 0f

    interface OnItemMoveListener {
        fun onItemMove(from: Int, to: Int)
    }

    private var listener: OnItemMoveListener? = null

    fun setOnItemMoveListener(listener: OnItemMoveListener) {
        this.listener = listener
    }

    override fun onMove(

        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {


        // 어댑터 획득
        val adapter = recyclerView.adapter as Move_UserAdapter_sub2_postlist

        // 현재 포지션 획득
        val fromPosition = viewHolder.absoluteAdapterPosition

        // 옮길 포지션 획득
        val toPosition = target.absoluteAdapterPosition

        // adapter가 가지고 있는 현재 리스트 획득
        val list = arrayListOf<Post_list_01>()
        list.addAll(adapter.differ.currentList)

        // 리스트 순서 바꿈
        Collections.swap(list, fromPosition, toPosition)

        // adapter.notifyItemMoved(fromPosition, toPosition)
        adapter.differ.submitList(list)

        // 추가적인 조치가 필요할 경우 인터페이스를 통해 해결
        listener?.onItemMove(fromPosition, toPosition)

        return true


    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        // 순서 조정 완료 후 투명도 다시 1f로 변경
        viewHolder.itemView.alpha = 1.0f
        ItemTouchHelper.SimpleCallback.getDefaultUIUtil().clearView(getView(viewHolder))
        previousPosition = viewHolder.absoluteAdapterPosition
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            // 순서 변경 시 alpha를 0.5f
            viewHolder?.itemView?.alpha = 0.5f
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder?.let {
                // 삭제 버튼 width 획득
                clamp = getViewWidth(viewHolder)
                // 현재 뷰홀더
                currentPosition = viewHolder.bindingAdapterPosition
                ItemTouchHelper.SimpleCallback.getDefaultUIUtil().onSelected(getView(it))

            }
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)

            val x = clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)

            currentDx = x

            ItemTouchHelper.SimpleCallback.getDefaultUIUtil().onDraw(
                c, recyclerView, view, x, dY, actionState, isCurrentlyActive
            )
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    private fun getViewWidth(viewHolder: RecyclerView.ViewHolder): Float{
        val viewWidth = (viewHolder as Move_UserAdapter_sub2_postlist.ViewHolder).itemView.findViewById<TextView>(
            R.id.remove_slide_list).width
        return viewWidth.toFloat()
    }

    private fun getView(viewHolder: RecyclerView.ViewHolder): View {
        return (viewHolder as Move_UserAdapter_sub2_postlist.ViewHolder).itemView.findViewById(R.id.swipe_viewlist)
    }

    private fun getTag(viewHolder: RecyclerView.ViewHolder): Boolean {
        return viewHolder.itemView.tag as? Boolean ?: false
    }


    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
        viewHolder.itemView.tag = isClamped
    }

    private fun clampViewPositionHorizontal(
        view: View,
        dX: Float,
        isClamped: Boolean,
        isCurrentlyActive: Boolean
    ): Float {
        val maxSwipe: Float = -clamp * 1.5f

        val right = 0f

        val x = if (isClamped) {
            if (isCurrentlyActive) dX - clamp else -clamp
        } else dX

        return min(
            max(maxSwipe, x),
            right
        )
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 10

    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        setTag(viewHolder, currentDx <= -clamp)

        return 2f
    }


    fun removePreviousClamp(recyclerView: RecyclerView) {

        if (currentPosition == previousPosition)
            return
        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).translationX = 0f
            setTag(viewHolder, false)
            previousPosition = null

        }
    }

}