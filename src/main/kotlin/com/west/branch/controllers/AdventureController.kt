package com.west.branch.controllers

import com.west.branch.models.Adventure
import com.west.branch.payload.request.CreateAdventureRequest
import com.west.branch.repositories.AdventureRepository
import com.west.branch.security.SecurityUtilities
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid


@RestController
@RequestMapping("api/adventure")
class AdventureController {
    @Autowired
    var adventureRepository: AdventureRepository? = null

    @Autowired
    var securityUtilities: SecurityUtilities? = null

    @Value("\${com.west.branch.adventure_page_size}")
    var pageSize: Int? = null

    @GetMapping("/")
    @ResponseBody
    @ApiImplicitParams(value = [
        ApiImplicitParam(
            name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)",
        ),
        ApiImplicitParam(
            name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page."
        ),
        ApiImplicitParam(
            name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). " +
                    "Default sort order is ascending. " +
                    "Multiple sort criteria are supported."
        )
    ])
    fun getAdventures(
        @ApiIgnore
        @PageableDefault(sort = ["createdAt"], direction = Sort.Direction.DESC, page = 0, size = 25)
        pageable: Pageable,
    ): Page<Adventure> {
        return adventureRepository!!.findAll(pageable)
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun createAdventure(@Valid @RequestBody createRequest: CreateAdventureRequest): Adventure {
        val currentUser = securityUtilities!!.getCurrentUser()

        val adventure = Adventure()
        adventure.name = createRequest.name
        adventure.owner = currentUser

        return adventureRepository!!.save(adventure)
    }
}