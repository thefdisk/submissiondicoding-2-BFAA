package com.example.aplikasigithubuser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.databinding.FragmentFollowingBinding
import com.example.aplikasigithubuser.model.User
import com.example.aplikasigithubuser.model.UserFollowing
import com.example.aplikasigithubuser.view.DetailActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingFragment : Fragment() {

    private val listFollowing = ArrayList<UserFollowing>()
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: ListFollowingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListFollowingAdapter(listFollowing)
        listFollowing.clear()
        val following = activity?.intent?.getParcelableExtra<User>(DetailActivity.EXTRA_USER) as User
        getListFollowing(following.username.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = ListFollowingAdapter(listFollowing)
        return binding.root
    }

    private fun getListFollowing(id: String) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                binding.progressBarFollowing.visibility = View.INVISIBLE
                val result = responseBody?.let { String(it) }
                Log.d(TAG, result.toString())

                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")
                        val following = UserFollowing()
                        following.userName = username
                        following.avatar = avatar
                        listFollowing.add(following)
                    }
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                binding.progressBarFollowing.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showRecyclerList() {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = adapter
    }

    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
    }
}