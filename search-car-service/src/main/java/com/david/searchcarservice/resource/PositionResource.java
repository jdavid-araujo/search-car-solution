package com.david.searchcarservice.resource;

import com.david.searchcarservice.exceptionhandler.Error;
import com.david.searchcarservice.modal.mapper.PositionMapper;
import com.david.searchcarservice.modal.PositionModal;
import com.david.searchcarservice.model.Poi;
import com.david.searchcarservice.model.Position;
import com.david.searchcarservice.model.Vehicle;
import com.david.searchcarservice.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Position", description = "Position management APIs")
@RestController
@RequestMapping(value = "/v1/posicoes")
public class PositionResource {

    private PositionService positionService;
    private PositionMapper positionMapper;

    public PositionResource(PositionService positionService, PositionMapper positionMapper) {
        this.positionService = positionService;
        this.positionMapper = positionMapper;
    }


    @Operation(summary = "Update an existing Position")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Position was updated"),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    private void update(@PathVariable Long id, @Valid @RequestBody PositionModal position) {
        Position entity = this.positionMapper.targetToSource(position);

        this.positionService.update(id, entity);
    }

    @Operation(summary = "Find  by id an existing Position")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Position was found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Position.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    private Position findById(@PathVariable Long id) {
        return this.positionService.findById(id);
    }

    @Operation(summary = "Get all Position")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Position",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Position.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    private List<Position> findAll() {
        return this.positionService.findAll();
    }

    @Operation(summary = "Delete a Position by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Position deleted successfully"),
            @ApiResponse(responseCode = "404",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    private void delete(@PathVariable Long id) {
        this.positionService.deleteById(id);
    }


}
