package com.west.branch.payload.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateAdventureRequest (
    @field:NotBlank
    @field:Size(min = 3, max = 50)
    val name: String,
)
