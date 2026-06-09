package com.pdfforge.app.updater

data class GithubRelease(
    val tag_name: String,
    val name: String,
    val body: String,
    val assets: List<GithubAsset>
)

data class GithubAsset(
    val name: String,
    val browser_download_url: String
)
