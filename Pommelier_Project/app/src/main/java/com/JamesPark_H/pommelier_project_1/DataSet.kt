package com.JamesPark_H.pommelier_project_1

import android.widget.TextView

data class RankData(
    var txRankUserName : String? = null,
    var txRankTitle: String? = null,
    var txRank: String? = null,
    var imgRankFood: Int? = null,
    var imgRankIcon: Int? = null
)

// 게시글 데이타 포멧
data class PostData(
    val imgPostIcon: Int? = null,
    val txPostTitle: String? = null,
    val txPostUserName: String? = null,
    val txPostSummary: String? = null,
    val imgPostFood: Int? = null
)