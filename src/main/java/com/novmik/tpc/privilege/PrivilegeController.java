package com.novmik.tpc.privilege;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link Privilege} control layer.
 * Доступ с 'ROLE_ADMIN'
 */
@AllArgsConstructor
@RequestMapping("api/v1/privilege")
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class PrivilegeController {

  /**
   * {@link PrivilegeService}.
   */
  private final PrivilegeService privilegeService;

  /**
   * Список {@link Privilege}.
   * Get-запрос "api/v1/privilege"
   *
   * @return список {@link Privilege}
   */
  @GetMapping
  public ResponseEntity<List<Privilege>> getAllPrivilege() {
    return new ResponseEntity<>(privilegeService.getAllPrivilege(), OK);
  }

  /**
   * Добавление {@link Privilege}.
   * Post-запрос "api/v1/privilege"
   *
   * @param privilege {@link Privilege} без id
   * @return {@link Privilege}
   */
  @PostMapping
  public ResponseEntity<Privilege> addNewPrivilege(@RequestBody final Privilege privilege) {
    return new ResponseEntity<>(privilegeService.addNewPrivilege(privilege), CREATED);
  }

  /**
   * Удаление {@link Privilege}.
   * Delete-запрос "api/v1/privilege/{idPrivilege}"
   *
   * @param idPrivilege id {@link Privilege}
   */
  @DeleteMapping("/{idPrivilege}")
  public void deletePrivilegeById(@PathVariable("idPrivilege") final Long idPrivilege) {
    privilegeService.deletePrivilegeById(idPrivilege);
  }
}
