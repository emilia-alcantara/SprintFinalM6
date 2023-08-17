package cl.individual.sprintfinalm6

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import cl.individual.sprintfinalm6.data.local.PhoneDAO
import cl.individual.sprintfinalm6.data.local.PhoneDatabase
import cl.individual.sprintfinalm6.data.local.PhoneEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class PhoneRoomDatabaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var phoneDAO: PhoneDAO
    private lateinit var db: PhoneDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PhoneDatabase::class.java).build()
        phoneDAO = db.getPhoneDAO()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertPhones_empty() = runBlocking {
        // Given
        val phoneList = listOf<PhoneEntity>()

        // When
        phoneDAO.insertPhoneList(phoneList)

        // Then A
        val it = phoneDAO.getPhoneData().getOrAwaitValue()
        assertThat(it).isNotNull()
        assertThat(it).isEmpty()

        // Then B
        phoneDAO.getPhoneData().observeForever {
            assertThat(it).isNotNull()
            assertThat(it).isEmpty()
        }
    }

    @Test
    fun insertPhones_happyCase_1element() = runBlocking {
        // Given
        val phoneList = listOf(PhoneEntity(1, "name", 100, "img"))

        // When
        phoneDAO.insertPhoneList(phoneList)

        // Then
        phoneDAO.getPhoneData().observeForever {
            assertThat(it).isNotNull()
            assertThat(it).isNotEmpty()
            assertThat(it).hasSize(1)
        }
    }

    @Test
    fun insertPhones_happyCase_3elements() = runBlocking {
        // Given
        val phoneList =
            listOf(PhoneEntity(2, "name2", 200, "img2"),
                PhoneEntity(3, "name3", 300, "img3"),
                PhoneEntity(4, "name4", 400, "img4"))

        // When
        phoneDAO.insertPhoneList(phoneList)

        // Then
        phoneDAO.getPhoneData().observeForever {
            assertThat(it).isNotNull()
            assertThat(it).isNotEmpty()
            assertThat(it).hasSize(3)
        }
    }
}


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T

}