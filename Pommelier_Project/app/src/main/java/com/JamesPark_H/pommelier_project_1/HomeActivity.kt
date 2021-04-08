package com.JamesPark_H.pommelier_project_1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.JamesPark_H.pommelier_project_1.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val postData = arrayListOf<PostData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        postData.add(
            PostData(
                R.drawable.food,
                "타이틀 입니다 test.",
                "닉네임 입니다.",
                "설명 입니다.",
                R.drawable.fastfood
            )
        )

        postData.add(
            PostData(
                R.drawable.food,
                "타이틀 입니다 test.",
                "닉네임 입니다.",
                "설명 입니다.",
                R.drawable.fastfood
            )
        )

        // 게시글 목록 생성
        createPostList()

        // 아이템 메뉴 생성
        createItemMenu()

        // 글쓰기 버튼 클릭 이벤트
        binding.btnHomeWrite.setOnClickListener {
            startActivityForResult(
                Intent(this@HomeActivity, PostWriteActivity::class.java),
                RQCODE
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQCODE) {
            if (resultCode == RESULT_OK) {
                val post = PostData(
                    txPostTitle = data?.getStringExtra("homePostTitle"),
                    txPostSummary = data?.getStringExtra("homePostSummary")
                )

                // 데이타 추가
                addPostList(post)

            }
        }
    }

    /*
    패스트 캠퍼스 강의 참조
    */
    private fun createItemMenu() {

        // 탭 부착
        binding.tabSubMenu.apply {
            addTab(newTab().setText("추천"))
            addTab(newTab().setText("순위"))
            addTab(newTab().setText("전체항목"))
        }

        val pagerAdapter = ItemViewPagerAdapter(supportFragmentManager, 3)
        binding.pagerHome.adapter = pagerAdapter

        // tabLayout과 viewPager를 연동
        binding.tabSubMenu.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.pagerHome.currentItem = tab!!.position
            }
        })

        // 페이저가 이동했을 때 탭을 이동시키는 코드
        binding.pagerHome.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabSubMenu))
    }

    private fun createPostList() {
        binding.homePostRecyclerView.apply {
            adapter = PostListAdapter(postData, this@HomeActivity)
        }
    }

    // 게시글 추가 메서드
    fun addPostList(data: PostData) {
        postData.add(data)
        binding.homePostRecyclerView.adapter?.notifyDataSetChanged()
    }

    // 사용자 게시글 삭제 메서드
    private fun deletePostList(post: PostData) {
        postData.remove(post)
        binding.homePostRecyclerView.adapter?.notifyDataSetChanged()
    }

    companion object {
        // 게시글 추가 요청코드드
       private const val RQCODE = 123
    }
}


