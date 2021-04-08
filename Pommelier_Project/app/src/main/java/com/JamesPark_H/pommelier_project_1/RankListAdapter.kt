package com.JamesPark_H.pommelier_project_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.JamesPark_H.pommelier_project_1.databinding.ItemRankBinding

// 사용자 게시글 목록(리사이클러뷰)
class RankListAdapter(private val dataSet: List<RankData> = emptyList<RankData>()) :
    RecyclerView.Adapter<RankListAdapter.RankViewHolder>() {

    class RankViewHolder(val binding: ItemRankBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RankViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_rank, viewGroup, false)
        return RankViewHolder(ItemRankBinding.bind(view))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        // 데이터 바인딩
        holder.binding.imgRankIcon.setImageResource(dataSet[position]?.imgRankIcon ?: R.drawable.ic_baseline_broken_image_24 )
        holder.binding.txRankTitle.text = dataSet[position]?.txRankTitle ?: "제목이 없습니다."
        holder.binding.txRankUserName.text = dataSet[position]?.txRankUserName ?: "닉네임이 없습니다."
        holder.binding.txRank.text = dataSet[position]?.txRank ?: "0"
        holder.binding.imgRankFood.setImageResource(dataSet[position]?.imgRankFood ?: R.drawable.ic_baseline_broken_image_24)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}