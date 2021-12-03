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

@AllArgsConstructor
@RequestMapping("api/v1/privilege")
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class PrivilegeController {

  private final PrivilegeService privilegeService;

  @GetMapping
  public ResponseEntity<List<Privilege>> getAllPrivilege() {
    return new ResponseEntity<>(privilegeService.getAllPrivilege(), OK);
  }

  @PostMapping
  public ResponseEntity<Privilege> addNewPrivilege(@RequestBody final Privilege privilege) {
    return new ResponseEntity<>(privilegeService.addNewPrivilege(privilege), CREATED);
  }

  @DeleteMapping("/{idPrivilege}")
  public void deletePrivilegeById(@PathVariable("idPrivilege") final Long idPrivilege) {
    privilegeService.deletePrivilegeById(idPrivilege);
  }
}
