package com.novmik.tpc.diagnosis;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * {@link DiagnosisPrice} control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1")
@RestController
public class DiagnosisPriceController {

  /**
   * {@link DiagnosisPriceService}.
   */
  private final DiagnosisPriceService dpService;

  /**
   * Список {@link DiagnosisPrice}.
   * Get-запрос "api/v1/{idSubject}/dp"
   *
   * @param idSubject id {@link com.novmik.tpc.subject.Subject}
   * @return список {@link DiagnosisPrice}
   */
  @GetMapping("/{idSubject}/dp")
  public ResponseEntity<List<DiagnosisPrice>> getDiagnosisPriceList(
      @PathVariable("idSubject") final Long idSubject) {
    return new ResponseEntity<>(dpService.getDiagnosisPriceList(idSubject), OK);
  }
}
