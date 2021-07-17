package com.example.aplikasigithubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFollower(
        var userName: String? = null,
        var avatar: String? = null,
) : Parcelable
