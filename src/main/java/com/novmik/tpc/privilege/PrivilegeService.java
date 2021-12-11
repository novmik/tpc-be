package com.novmik.tpc.privilege;

import static com.novmik.tpc.privilege.PrivilegeConstants.PRIVILEGE_EXIST;
import static com.novmik.tpc.privilege.PrivilegeConstants.PRIVILEGE_NOT_CORRECT;
import static com.novmik.tpc.privilege.PrivilegeConstants.PRIVILEGE_NOT_EXISTS;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * {@link Privilege} business interface layer.
 */
@AllArgsConstructor
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class PrivilegeService {

  /**
   * {@link PrivilegeRepository}.
   */
  private final PrivilegeRepository privilegeRepo;

  /**
   * Список {@link Privilege}.
   *
   * @return список {@link Privilege}
   */
  public List<Privilege> getAllPrivilege() {
    return privilegeRepo.findAll();
  }

  /**
   * Поиск {@link Privilege} по наименованию.
   *
   * @param privilegeName наименование {@link Privilege}
   * @return {@link Privilege}
   */
  public Optional<Privilege> findByPrivilegeName(final String privilegeName) {
    return privilegeRepo.findByName(privilegeName);
  }

  /**
   * Наличие {@link Privilege}.
   *
   * @param idPrivilege id {@link Privilege}
   * @return наличие
   */
  public boolean existById(final Long idPrivilege) {
    return privilegeRepo.existsById(idPrivilege);
  }

  /**
   * Сохранение {@link Privilege}.
   *
   * @param privilege {@link Privilege}
   * @return {@link Privilege}
   */
  protected Privilege save(final Privilege privilege) {
    return privilegeRepo.save(privilege);
  }

  /**
   * Добавление {@link Privilege}.
   *
   * @param privilege {@link Privilege} без id
   * @return {@link Privilege}
   * @throws BadRequestException если некорректные данные
   *                             если {@link Privilege} есть
   */
  protected Privilege addNewPrivilege(final Privilege privilege) {
    if (ObjectUtils.anyNull(
        privilege,
        privilege.getName()
    )) {
      throw new BadRequestException(PRIVILEGE_NOT_CORRECT);
    }
    if (findByPrivilegeName(privilege.getName()).isPresent()) {
      throw new BadRequestException(PRIVILEGE_EXIST + privilege.getName());
    }
    return save(privilege);
  }

  /**
   * Удаление {@link Privilege}.
   *
   * @param idPrivilege id {@link Privilege}
   * @throws BadRequestException если id не корректный
   * @throws NotFoundException   если {@link Privilege} не найден
   */
  protected void deletePrivilegeById(final Long idPrivilege) {
    if (idPrivilege == null || idPrivilege < 1) {
      throw new BadRequestException(PRIVILEGE_NOT_CORRECT);
    }
    if (!existById(idPrivilege)) {
      throw new NotFoundException(PRIVILEGE_NOT_EXISTS);
    }
    privilegeRepo.deleteById(idPrivilege);
  }
}
