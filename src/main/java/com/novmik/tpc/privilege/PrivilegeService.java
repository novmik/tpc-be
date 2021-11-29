package com.novmik.tpc.privilege;

import com.novmik.tpc.exception.BadRequestException;
import com.novmik.tpc.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.novmik.tpc.privilege.PrivilegeConstant.*;

@AllArgsConstructor
@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public List<Privilege> getAllPrivilege() {
        return privilegeRepository.findAll();
    }

    public Optional<Privilege> findByPrivilegeName(String privilegeName) {
        return privilegeRepository.findByName(privilegeName);
    }

    public boolean existById(Long idPrivilege) {
        return privilegeRepository.existsById(idPrivilege);
    }

    Privilege save(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    Privilege addNewPrivilege(Privilege privilege) {
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

    void deletePrivilegeById(Long idPrivilege) {
        if (idPrivilege == null || idPrivilege <1){
            throw new BadRequestException(PRIVILEGE_NOT_CORRECT);
        }
        if (!existById(idPrivilege)) {
            throw new NotFoundException(PRIVILEGE_NOT_EXISTS);
        }
        privilegeRepository.deleteById(idPrivilege);
    }
}
