package com.JamesPark_H.pommelier_project_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentRank: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_fragment2_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rankRecyclerView: RecyclerView = view.findViewById(R.id.homeRankRecyclerView)
        rankRecyclerView?.let {
            it.adapter = RankListAdapter()
            it.layoutManager = LinearLayoutManager(view.context)
        }
    }
}