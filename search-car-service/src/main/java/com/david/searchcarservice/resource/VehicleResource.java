package com.david.searchcarservice.resource;

import com.david.searchcarservice.exceptionhandler.Error;
import com.david.searchcarservice.modal.*;
import com.david.searchcarservice.modal.mapper.PositionMapper;
import com.david.searchcarservice.modal.mapper.VeihicleMapper;
import com.david.searchcarservice.model.Position;
import com.david.searchcarservice.model.Vehicle;
import com.david.searchcarservice.service.VehicleService;
import com.david.searchcarservice.service.VeiculosPoiService;
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

@Tag(name = "Vehicle", description = "Vehicle management APIs")
@RestController
@RequestMapping("/v1/veiculos")
public class VehicleResource {

    private VehicleService vehicleService;

    private VeihicleMapper veihicleMapper;

    private PositionMapper positionMapper;

    private VeiculosPoiService veiculosPoiService;

    public VehicleResource(VehicleService vehicleService, VeihicleMapper veihicleMapper, PositionMapper positionMapper,VeiculosPoiService veiculosPoiService) {
        this.vehicleService = vehicleService;
        this.veihicleMapper = veihicleMapper;
        this.positionMapper = positionMapper;
        this.veiculosPoiService = veiculosPoiService;
    }

    @Operation(summary = "Create a new Vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Poi was created"),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    private void save(@Valid @RequestBody VehicleModal vehicleModal) {
        Vehicle entity = this.veihicleMapper.targetToSource(vehicleModal);

        this.vehicleService.save(entity);
    }

    @Operation(summary = "Update an existing Vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle was updated"),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    private void update(@PathVariable Long id, @Valid @RequestBody VehicleModal vehicleModal) {
        Vehicle entity = this.veihicleMapper.targetToSource(vehicleModal);

        this.vehicleService.update(id, entity);
    }

    @Operation(summary = "Create a new Vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle was created"),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{id}/posicoes")
    private void savePosition(@PathVariable(value = "id") Long id, @Valid @RequestBody PositionModal position) {
        Position entity = this.positionMapper.targetToSource(position);

        this.vehicleService.savePosition(id, entity);
    }

    @Operation(summary = "Find  by id an existing Vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle was found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    private Vehicle findById(@PathVariable Long id) {
        return this.vehicleService.findById(id);
    }

    @Operation(summary = "Find positions by  an existing Vehicle id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle was found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/posicoes")
    private Page<Position> findAllByVehicleId(@PathVariable(value = "id") Long id,
                                              @PageableDefault(size = 10, sort = "dataPosicao", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.vehicleService.findAllByVehicleId(id, pageable);
    }

    @Operation(summary = "Get all Vehicle by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Vehicle",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    private Page<Vehicle> search(@RequestParam(value = "placa", required = false) String placa,
                                 @PageableDefault(size = 10, sort = "placa", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.vehicleService.search(placa, pageable);
    }

    @Operation(summary = "Delete a Vehicle by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    private void delete(@PathVariable Long id) {
        this.vehicleService.deleteById(id);
    }

    @Operation(summary = "Search the period of time of a vehicle on a Poi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "It was able to get the result",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = VehiclePoiTimeModal.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500",  content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Error.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tempo-poi")
    public List<VehiclePoiTimeModal> search(@Valid @ModelAttribute VehicleDataSearchModal vehicleDataSearchModal) {
        return this.veiculosPoiService.search(vehicleDataSearchModal);
    }
}
