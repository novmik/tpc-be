package com.novmik.tpc.bsa;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RequestMapping("api/v1/bsa")
@RestController
public class BsaController {

    private final BsaService bsaService;

    @PostMapping
    public ResponseEntity<Map<String, Double>> allMethods(@RequestBody BsaRequest bsaRequest) {
        return new ResponseEntity<>(bsaService.allMethods(bsaRequest), OK);
    }
}
