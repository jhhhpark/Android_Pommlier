package com.JamesPark_H.pommelier_project_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.JamesPark_H.pommelier_project_1.databinding.ItemPostBinding

class ItemPost : AppCompatActivity() {
    private lateinit var binding: ItemPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.txPostTitle.text = intent?.getStringExtra("PostTitle") ?: "noting"
        binding.txPostSummary.text = intent?.getStringExtra("PostSummary") ?: "noting"
        binding.txPostUserName.text = intent?.getStringExtra("PostUserName") ?: "noting"
        binding.imgPostIcon.setImageResource(intent.getIntExtra("PostIcon", R.drawable.ic_baseline_broken_image_24))
        binding.imgPostFood.setImageResource(intent.getIntExtra("PostFood", R.drawable.ic_baseline_broken_image_24))

    }
}