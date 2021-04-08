package com.JamesPark_H.pommelier_project_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.JamesPark_H.pommelier_project_1.databinding.ActivityPostWriteBinding

class PostWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostWriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAddPost.setOnClickListener {
            intent.putExtra("homePostTitle", "${binding.editPostTitle.text.toString()}")
            intent.putExtra("homePostSummary", "${binding.editPostSummary.text.toString()}")
            setResult(RESULT_OK, intent)
            finish()

            // 글을 db에 저장
        }
    }
}