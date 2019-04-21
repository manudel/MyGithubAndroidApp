package com.ed.mygithub.data

data class Repository(
    val id: Double,
    val name: String,
    val description: String,
    val owner: RepoType
)

interface RepoType

class Organization : RepoType

data class User(
    val login: String,
    val avatar_url: String
) : RepoType