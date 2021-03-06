package com.example.mentalhealth

import androidx.room.*

@Dao
interface YoutubeDAO {

    @Query("Select * FROM videoTable")
    fun getAll():Array<Video>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insert(video: Video)

    @Query("UPDATE videoTable SET isRec = 1 WHERE videoID =:id")
    fun recommend(id: String)

    @Query("UPDATE videoTable SET isWatched = 1 WHERE videoID =:id")
    fun watch(id: String)


    @Query("UPDATE videoTable SET isLiked = 1, isDisliked = 0 WHERE videoID =:id")
    fun like(id: String)

    @Query("UPDATE videoTable SET isDisliked = 1, isLiked = 0 WHERE videoID =:id")
    fun dislike(id: String)


    @Query("SELECT * FROM videoTable WHERE isRec = 1")
    fun getAllRec():Array<Video>

    @Query("SELECT * FROM videoTable WHERE isWatched = 1")
    fun getAllWatched():Array<Video>

    @Query("DELETE FROM videoTable")
    fun deleteAll()


    @Query("SELECT Count(1) FROM videoTable WHERE isLiked == 1 AND videoID = :id")
    fun isLiked(id:String):Int

    @Query("SELECT Count(1) FROM videoTable where isDisliked == 1 AND videoID = :id")
    fun isDisliked(id:String):Int


    @Query("SELECT * FROM videoTable WHERE videoID = :id")
fun getVideoByID(id:String):Video
//    1. find videos with similar 2 provisions (both at once)
//    2. find videos with similar 5 hobbies (individually)
//    3. find videos with similar 1 mood

@Query("SELECT videoID FROM videoTable WHERE provisions LIKE '%' || :prov1 || '%' AND provisions LIKE  '%' || :prov2 || '%' AND isWatched = 0")//" AND provisions LIKE :prov3")
fun filterByProvisions(prov1: String,prov2: String) :Array<String>

@Query("SELECT videoID FROM videoTable WHERE keywords LIKE '%' || :hobby || +'%' AND isWatched = 0")
fun filterByHobby(hobby: String) :Array<String>


@Query("SELECT keywords, videoID FROM videoTable WHERE isWatched = 0")
fun filter() :Array<NameTuple>

@Query("SELECT videoID FROM videoTable WHERE moods LIKE '%' || :mood || '%' AND isWatched = 0")
fun filterByMood(mood: String) :Array<String>
}