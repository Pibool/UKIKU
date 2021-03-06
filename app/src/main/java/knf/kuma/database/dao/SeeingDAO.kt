package knf.kuma.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import knf.kuma.database.BaseConverter
import knf.kuma.pojos.SeeingObject

@Dao
@TypeConverters(BaseConverter::class)
interface SeeingDAO {

    @get:Query("SELECT * FROM seeingobject ORDER BY title")
    val all: LiveData<List<SeeingObject>>

    @get:Query("SELECT * FROM seeingobject ORDER BY title")
    val allRaw: MutableList<SeeingObject>

    @Query("SELECT * FROM seeingobject WHERE state=:state ORDER BY title")
    fun getLiveByState(state: Int): LiveData<List<SeeingObject>>

    @get:Query("SELECT count(*) FROM seeingobject")
    val countLive: LiveData<Int>

    @get:Query("SELECT count(*) FROM seeingobject WHERE state=1")
    val countWatchingLive: LiveData<Int>

    @get:Query("SELECT count(*) FROM seeingobject WHERE state=3")
    val countCompletedLive: LiveData<Int>

    @get:Query("SELECT count(*) FROM seeingobject WHERE state=4")
    val countDroppedLive: LiveData<Int>

    @Query("SELECT * FROM seeingobject WHERE aid LIKE :aid")
    fun getByAid(aid: String): SeeingObject?

    @Query("SELECT count(*) FROM seeingobject WHERE aid = :aid AND state>0 AND state <3")
    fun isSeeing(aid: String): Boolean

    @Query("SELECT count(*) FROM seeingobject WHERE aid = :aid")
    fun isSeeingAll(aid: String): Boolean

    @Query("SELECT count(*) FROM seeingobject WHERE aid IN (:list) AND state=3")
    fun isAnimeCompleted(list: List<String>): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(seeingObject: SeeingObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(list: MutableList<SeeingObject>)

    @Update
    fun update(seeingObject: SeeingObject)

    @Delete
    fun remove(seeingObject: SeeingObject)

    @Query("DELETE FROM seeingobject")
    fun clear()
}
