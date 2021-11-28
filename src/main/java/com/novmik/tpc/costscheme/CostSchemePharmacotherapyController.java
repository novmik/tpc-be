package com.novmik.tpc.costscheme;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RequestMapping("api/v1/costscheme")
@RestController
public class CostSchemePharmacotherapyController {

    private CostSchemePharmacotherapyService costSchemeService;

    @PostMapping
    public ResponseEntity<CostSchemePharmacotherapyResponse> getCostSchemePharmacotherapyByCodeScheme(@RequestBody CostSchemePharmacotherapyRequest costSchemeRequest) {
        return new ResponseEntity<>(costSchemeService.getCostSchemePharmacotherapy(costSchemeRequest), OK);
    }
}
