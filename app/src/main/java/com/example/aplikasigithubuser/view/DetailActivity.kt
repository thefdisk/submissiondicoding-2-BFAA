package com.example.aplikasigithubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.ViewPagerDetailAdapter
import com.example.aplikasigithubuser.model.User

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val actionBar = supportActionBar
        actionBar?.title = "Detail User"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        setData()
        viewPagerConfig()

    }

    private fun setData() {
        val imgAvatar: ImageView = findViewById(R.id.avatar)
        val tvUsername: TextView = findViewById(R.id.txt_username)
        val tvName: TextView = findViewById(R.id.txt_name)
        val tvFollower: TextView = findViewById(R.id.followers_number)
        val tvFollowing: TextView = findViewById(R.id.following_number)
        val tvCompany: TextView = findViewById(R.id.tv_company)
        val tvLocation: TextView = findViewById(R.id.tv_location)
        val tvRepository: TextView = findViewById(R.id.tv_repository)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        Glide.with(this)
                .load(user.avatar)
                .apply(RequestOptions().override(100,100))
                .into(imgAvatar)

        tvUsername.text = user.username
        tvName.text = user.name
        tvFollower.text = user.follower.toString()
        tvFollowing.text = user.following.toString()
        tvCompany.text = user.company
        tvLocation.text = user.location
        tvRepository.text = user.repository.toString()
    }

    private fun viewPagerConfig() {
        val viewPagerDetail = ViewPagerDetailAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = viewPagerDetail
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.share -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Aplikasi Github User")
                intent.type = "text/plain"
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.followers,
                R.string.following
        )
    }
}