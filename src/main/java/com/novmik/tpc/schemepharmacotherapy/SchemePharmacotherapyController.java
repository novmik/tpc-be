package com.novmik.tpc.schemepharmacotherapy;

import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/v1/codescheme")
@RestController
public class SchemePharmacotherapyController {

  private final SchemePharmacotherapyService schemePharmacotherapyService;

  @GetMapping("/{codeScheme}")
  public ResponseEntity<Optional<SchemePharmacotherapy>> getSchemePharmacotherapy(
      @PathVariable("codeScheme") final String codeScheme) {
    return new ResponseEntity<>(schemePharmacotherapyService.findByCodeScheme(codeScheme), OK);
  }
}
