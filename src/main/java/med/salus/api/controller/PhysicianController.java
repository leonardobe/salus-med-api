package med.salus.api.controller;

import jakarta.validation.Valid;
import med.salus.api.domain.entity.Physician;
import med.salus.api.dto.PhysicianInputDTO;
import med.salus.api.dto.PhysicianListDTO;
import med.salus.api.dto.PhysicianUpdateDTO;
import med.salus.api.service.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("physicians")
public class PhysicianController {

    @Autowired
    private PhysicianService service;

    @PostMapping()
    public Physician registration(@RequestBody @Valid PhysicianInputDTO dto) {

        return service.savePhysician(dto);
    }

    @GetMapping()
    public Page<PhysicianListDTO> listPhysicians(
            @PageableDefault(
                            size = 10,
                            sort = {"name"})
                    Pageable pp) {
        return service.listAllPhysicians(pp);
    }

    @PutMapping
    public void updatePhysician(@RequestBody @Valid PhysicianUpdateDTO dto) {
        service.saveUpdatePhysician(dto);
    }

    @DeleteMapping("/{id}")
    public void deletePhysician(@PathVariable Long id) {
        service.deactivatePhysician(id);
    }
}
