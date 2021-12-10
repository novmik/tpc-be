package com.novmik.tpc.bsa;

import static org.springframework.http.HttpStatus.OK;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ППТ control layer.
 */
@AllArgsConstructor
@RequestMapping("api/v1/bsa")
@RestController
public class BodySurfaceAreaController {

  /**
   * Коллекция ППТ.
   * Список ППТ согласено формулам расчёта
   *
   * @param bsaRequest Запрос ППТ {@link BodySurfaceAreaRequest}
   * @return коллекция ППТ
   */
  @PostMapping
  public ResponseEntity<Map<String, Double>> allMethods(
      @RequestBody final BodySurfaceAreaRequest bsaRequest) {
    return new ResponseEntity<>(BodySurfaceAreaService.allMethods(bsaRequest), OK);
  }
}
