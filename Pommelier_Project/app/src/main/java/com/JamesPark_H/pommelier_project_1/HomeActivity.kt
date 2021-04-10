package com.JamesPark_H.pommelier_project_1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.JamesPark_H.pommelier_project_1.databinding.ActivityHomeBinding
import com.JamesPark_H.pommelier_project_1.databinding.ItemPostBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // 로그인
        if (Firebase.auth.currentUser == null) {
            login()
        } else {
            Log.d("testt", "로그인 상태")
        }

        // 로그아웃
        binding.btnLogOut.setOnClickListener {
            logout()
        }


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

        // 관찰 UI Update
        viewModel.postLiveData.observe(this, Observer {
            // liveData가 갱신되면서 해당 람다함수가 호출됨.
            (binding.homePostRecyclerView.adapter as PostListAdapter).setData(it)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 추가 액티비티
        if (requestCode == RQCODE) {
            if (resultCode == RESULT_OK) {
                val post = PostData(
                    txPostUserName = FirebaseAuth.getInstance().currentUser.displayName,
                    txPostTitle = data?.getStringExtra("homePostTitle"),
                    txPostSummary = data?.getStringExtra("homePostSummary")

                )


                // 데이타 추가
                viewModel.addPostList(post)
            }
        }

        // 로그인 성공 시
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                viewModel.fetchData()
            } else {
                // Sign in failed. If response is null the user canceled the
                finish()
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
            adapter = PostListAdapter(
                viewModel.getPostData(),
                this@HomeActivity,
                onClickDelete = { post ->
                    viewModel.deletePostList(post)
                }
            )
        }
    }

    companion object {
        // 게시글 추가 요청코드드
        private const val RQCODE = 123
        private const val RC_SIGN_IN = 1000
    }

    private fun login() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // 로그아웃 후에 바로 로그인 화면을 띄움
                login()
            }
    }
}


// ViewModel : Data Management
class HomeViewModel : ViewModel() {
    val postLiveData = MutableLiveData<List<DocumentSnapshot>>()

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    private val postData = arrayListOf<QueryDocumentSnapshot>()

    init {
        fetchData()
    }


    fun fetchData() {
        val TAG = "readData"
        val user = Firebase.auth.currentUser

        if (user != null) {
            db.collection("postdatas")
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }

                    postData.clear()
                    for (doc in value!!) {
                        postData.add(doc)
                    }

                    if (value != null) {
                        postLiveData.value = value.documents
                    }
                }
        }

    }

    fun getPostData(): List<DocumentSnapshot> {
        return postData
    }

    // 게시글 추가 메서드
    fun addPostList(data: PostData) {
        val TAG = "addPostList"
        FirebaseAuth.getInstance().currentUser?.let { user ->
            db.collection("postdatas").add(data)
        }
    }

    // 사용자 게시글 삭제 메서드
    fun deletePostList(post: DocumentSnapshot) {
        val TAG = "deletePostList"

        FirebaseAuth.getInstance().currentUser?.let { user ->
            if (post.getString("txPostUserName") == user.displayName) {
                // 유저ID가 일치할때에만 삭제
                db.collection("postdatas").document(post.id)
                    .delete()
            } else {
                Log.d(TAG, "nothing your document")
            }
        }
    }
}

