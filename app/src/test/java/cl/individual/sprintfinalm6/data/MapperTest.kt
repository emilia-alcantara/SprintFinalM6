package cl.individual.sprintfinalm6.data

import cl.individual.sprintfinalm6.data.remote.Phone
import cl.individual.sprintfinalm6.data.remote.PhoneDetails
import org.junit.Assert.*

import org.junit.Test

class MapperTest {

    @Test
    fun transformToPhoneEntity() {
        // given
        val id = 1
        val name = "example name"
        val price = 100000
        val img = "www.example.com"

        val phone = Phone(id, name, price, img)

        // when
        val result = phone.transformToPhoneEntity()

        // then
        assertEquals(id, result.id)
        assertEquals(name, result.name)
        assertEquals(price, result.price)
        assertEquals(img, result.image)
    }

    @Test
    fun transformToPhoneDetailsEntity() {
        // given
        val id = 1
        val name = "name"
        val price = 100000
        val img = "www.example.com"
        val description = "this is a description"
        val lastPrice = 150000
        val credit = true

        val phoneDetails = PhoneDetails(id, name, price, img, description, lastPrice, credit)

        // when
        val result = phoneDetails.transformToPhoneDetailsEntity(id)

        // then
        assertEquals(id, result.id)
        assertEquals(name, result.name)
        assertEquals(price, result.price)
        assertEquals(img, result.image)
        assertEquals(description, result.description)
        assertEquals(lastPrice, result.lastPrice)
        assertEquals(credit, result.credit)
    }
}