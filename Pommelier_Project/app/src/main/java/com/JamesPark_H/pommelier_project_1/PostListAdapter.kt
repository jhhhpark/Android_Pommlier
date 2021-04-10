package com.JamesPark_H.pommelier_project_1

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.JamesPark_H.pommelier_project_1.databinding.ItemPostBinding
import com.google.firebase.firestore.DocumentSnapshot


/*
참고 자료 : https://developer.android.com/guide/topics/ui/layout/recyclerview
 */

// 사용자 게시글 목록(리사이클러뷰)
class PostListAdapter(
    private var dataSet: List<DocumentSnapshot>,
    private val activity: Activity,
    val onClickDelete: (postData: DocumentSnapshot) -> Unit
) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_post, viewGroup, false)

        return ViewHolder(ItemPostBinding.bind(view))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 데이터 바인딩
        val post = dataSet[position]

        holder.binding.txPostTitle.text = post?.getString("txPostTitle") ?: "제목이 없습니다."
        holder.binding.txPostUserName.text = post?.getString("txPostUserName") ?: "닉네임이 없습니다."
        holder.binding.txPostSummary.text = post?.getString("txPostSummary") ?: "내용이 없습니다."
        holder.binding.imgPostIcon.setImageResource(
            post?.getString("imgPostIcon") as Int? ?: R.drawable.ic_baseline_broken_image_24
        )
        holder.binding.imgPostFood.setImageResource(
            post?.getString("imgPostFood") as Int? ?: R.drawable.ic_baseline_broken_image_24
        )

        // 게시글 상세 페이지 열람
        holder.binding.root.setOnClickListener {
            val post = dataSet[position]
            val intent = Intent(activity, ItemPost::class.java)
            intent.putExtra("PostIcon", post.getString("imgPostIcon"))
            intent.putExtra("PostTitle", post.getString("txPostTitle"))
            intent.putExtra("PostUserName", post.getString("txPostUserName"))
            intent.putExtra("PostSummary", post.getString("txPostUserName"))
            intent.putExtra("PostFood", post.getString("txPostUserName"))

            activity.startActivity(intent)
        }

        holder.binding.imgDeleteVIew.setOnClickListener {
            onClickDelete.invoke(post)

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun setData(newData: List<DocumentSnapshot>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}