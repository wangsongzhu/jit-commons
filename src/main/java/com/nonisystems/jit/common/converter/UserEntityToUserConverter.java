package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.Permission;
import com.nonisystems.jit.common.dto.Role;
import com.nonisystems.jit.common.dto.User;
import com.nonisystems.jit.domain.entity.PermissionEntity;
import com.nonisystems.jit.domain.entity.RoleEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class UserEntityToUserConverter {

    public User convert(UserEntity userEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);

        Set<RoleEntity> roleEntities = userEntity.getRoles();
        if (roleEntities != null && !roleEntities.isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (RoleEntity roleEntity : roleEntities) {
                Role role = new Role();
                role.setName(roleEntity.getName());

                Set<PermissionEntity> permissionEntities = roleEntity.getPermissions();
                if (permissionEntities != null && !permissionEntities.isEmpty()) {
                    Set<Permission> permissions = new HashSet<>();
                    for (PermissionEntity permissionEntity : permissionEntities) {
                        Permission permission = new Permission();
                        permission.setName(permissionEntity.getName());
                        permissions.add(permission);
                    }
                    role.setPermissions(permissions);
                }

                roles.add(role);
            }
            user.setRoles(roles);
        }

        return user;
    }
}
