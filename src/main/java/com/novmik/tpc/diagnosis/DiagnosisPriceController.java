package com.novmik.tpc.diagnosis;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RequestMapping("api/v1")
@RestController
public class DiagnosisPriceController {

    private final DiagnosisPriceService diagnosisPriceService;

    @GetMapping("/{idSubject}/dp")
    public ResponseEntity<List<DiagnosisPrice>> getDiagnosisPriceList(@PathVariable("idSubject") final Long idSubject) {
        return new ResponseEntity<>(diagnosisPriceService.getDiagnosisPriceList(idSubject), OK);
    }
}
