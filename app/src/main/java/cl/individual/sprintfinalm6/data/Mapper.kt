package cl.individual.sprintfinalm6.data

import cl.individual.sprintfinalm6.data.local.PhoneDetailsEntity
import cl.individual.sprintfinalm6.data.local.PhoneEntity
import cl.individual.sprintfinalm6.data.remote.Phone
import cl.individual.sprintfinalm6.data.remote.PhoneDetails

fun Phone.transformToPhoneEntity(): PhoneEntity =
    PhoneEntity(this.id, this.name, this.price, this.image)

fun PhoneDetails.transformToPhoneDetailsEntity(id: Int): PhoneDetailsEntity = PhoneDetailsEntity(
    id,
    this.name,
    this.price,
    this.image,
    this.description,
    this.lastPrice,
    this.credit
)