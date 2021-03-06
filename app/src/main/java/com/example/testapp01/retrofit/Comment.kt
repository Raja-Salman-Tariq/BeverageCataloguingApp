package com.example.testapp01.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
public data class Comment(@SerializedName("postId") val drinkId:Int=-1,// drinkId is post id !
                          @PrimaryKey(autoGenerate = true) val id:Int=-1,
                          val name:String="",
                          val email:String="",
                          val body:String="" ){
    constructor(comment:Comment):this(comment.drinkId,comment.id, comment.name, comment.email, comment.body)
}