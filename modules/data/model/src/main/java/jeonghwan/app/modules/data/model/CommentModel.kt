package jeonghwan.app.modules.data.models

data class CommentModel (
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)