package com.task.shortlyapp.repository.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.shortlyapp.repository.DateMockUtils
import com.task.shortlyapp.repository.locale.ShortlyDatabase
import com.task.shortlyapp.repository.locale.dao.ShortlyLinkDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShortlyDatabaseTest : TestCase() {

    private lateinit var db: ShortlyDatabase
    private lateinit var dao: ShortlyLinkDao

    /**
     * This function will be called at first when this test class is called
     */
    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ShortlyDatabase::class.java).build()
        dao = db.shortlyLinkDao()
    }

    /**
     * This function will be called at last when this test class is called
     */
    @After
    fun closeDb() {
        db.close()
    }

    /** Here we are first adding an item to the db and then checking if that item
     * is present in the db -- if the item is present then our test cases pass
     */
    @Test
    fun writeAndReadNewShortLink() = runBlocking {
        val link = DateMockUtils.getShortlyLink()
        dao.addShortlyLink(link)
        val shortLinks = dao.getAllShortlyLinks()
        assert(shortLinks.contains(link))
    }

    /**
     * Here we are testing if duplication does not exist.
     */
    @Test
    fun overwriteIfExisted() = runBlocking {
        val link = DateMockUtils.getShortlyLink()
        dao.addShortlyLink(link)
        dao.addShortlyLink(link)
        val shortLinks = dao.getAllShortlyLinks()
        assert(shortLinks.size == 1)
    }

    /** here we are first adding an item to the db and then checking if that item
     * is present in the db -- if the item is present then we will delete it
     * again try to get the items and check if that previous item is not available then test pass
     */
    @Test
    fun deleteQueryTestSuccess() = runBlocking {
        val code = "YAtuA"
        val link = DateMockUtils.getShortlyLink()
        dao.addShortlyLink(link)
        val shortLinks = dao.getAllShortlyLinks()
        assert(shortLinks.contains(link))
        dao.deleteByCode(code)
        val languages = dao.getAllShortlyLinks()
        assert(!languages.contains(link))
    }

    /** here we are first adding an item to the db and then checking if that item
     * is present in the db -- if the item is present then we will delete it using wrong code
     * again try to get the items and check if that previous item is still available then test pass
     */
    @Test
    fun deleteQueryTestFailure() = runBlocking {
        val code = "test_2"
        val wrongCode = "test_3"
        val link = DateMockUtils.getShortlyLink()
        dao.addShortlyLink(link)
        val shortLinks = dao.getAllShortlyLinks()
        assert(shortLinks.contains(link))
        dao.deleteByCode(wrongCode)
        val languages = dao.getAllShortlyLinks()
        assert(languages.contains(link))
    }

    /**
     * check total count and get all the records if both are same then test pass
     */
    @Test
    fun totalCountTestSuccess() = runBlocking {
        val shortLinks = dao.getAllShortlyLinks()
        val totalCount = dao.getAllShortlyLinksCount()
        assert(shortLinks.size == totalCount)
    }
}
