package com.david.searchcarservice.resource;

import com.david.searchcarservice.exceptionhandler.Error;
import com.david.searchcarservice.modal.mapper.PoiMapper;
import com.david.searchcarservice.modal.PoiModal;
import com.david.searchcarservice.model.Poi;
import com.david.searchcarservice.model.Vehicle;
import com.david.searchcarservice.service.PoiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Poi", description = "Poi management APIs")
@RestController
@RequestMapping(value = "/v1/pois")
public class PoiResource {

    private PoiService poiService;
    private PoiMapper poiMapper;

    public PoiResource(PoiService poiService, PoiMapper poiMapper) {
        this.poiService = poiService;
        this.poiMapper = poiMapper;
    }

    @Operation(summary = "Create a new Poi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Poi was created"),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    private void save(@Valid @RequestBody PoiModal poi) {
        Poi entity = this.poiMapper.targetToSource(poi);

        this.poiService.save(entity);
    }

    @Operation(summary = "Update an existing Poi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Poi was updated"),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    private void update(@PathVariable Long id, @Valid @RequestBody PoiModal poi) {
        Poi entity = this.poiMapper.targetToSource(poi);

        this.poiService.update(id, entity);
    }

    @Operation(summary = "Find by id an existing Poi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Poi was found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Poi.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    private Poi findById(@PathVariable Long id) {
        Poi poi = this.poiService.findById(id);

        return poi;
    }

    @Operation(summary = "Get all Pois by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Pois",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    private Page<Poi> search(@RequestParam(value = "name", required = false) String name,
                             @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.poiService.search(name, pageable);
    }

    @Operation(summary = "Delete a Poi by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Poi deleted successfully"),
            @ApiResponse(responseCode = "404",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    private void delete(@PathVariable Long id) {
        this.poiService.deleteById(id);
    }
}
