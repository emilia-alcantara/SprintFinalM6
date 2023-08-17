package cl.individual.sprintfinalm6.data.remote

// data class que permite mapear la data de la api más facilmente
data class Phone(
    val id: Int,
    val name: String,
    val price: Int,
    val image: String
)