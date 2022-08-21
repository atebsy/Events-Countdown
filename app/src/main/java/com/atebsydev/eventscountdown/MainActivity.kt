package com.atebsydev.eventscountdown

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.atebsydev.eventscountdown.databinding.ActivityMainBinding
import com.atebsydev.eventscountdown.ui.add.AddEventActivity
import com.atebsydev.eventscountdown.ui.events.TabsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = TabsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_text_1)
                1 -> tab.text = getString(R.string.tab_text_2)
            }
        }.attach()

        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddEventActivity::class.java))
            finish()
        }
    }
}