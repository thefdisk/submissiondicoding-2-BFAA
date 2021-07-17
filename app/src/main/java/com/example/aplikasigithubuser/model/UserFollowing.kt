package com.example.aplikasigithubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFollowing(
        var userName: String? = null,
        var avatar: String? = null,
) : Parcelable
