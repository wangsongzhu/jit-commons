package com.nonisystems.jit.common.converter;

import com.nonisystems.jit.common.dto.Permission;
import com.nonisystems.jit.common.dto.Role;
import com.nonisystems.jit.common.dto.Tag;
import com.nonisystems.jit.common.dto.User;
import com.nonisystems.jit.domain.entity.PermissionEntity;
import com.nonisystems.jit.domain.entity.RoleEntity;
import com.nonisystems.jit.domain.entity.TagEntity;
import com.nonisystems.jit.domain.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class UserEntityConverter {

    public User convert(UserEntity userEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);

        RoleEntity roleEntity = userEntity.getRole();
        if (roleEntity != null) {
            Role role = new Role();
            role.setName(roleEntity.getName());

//            Set<PermissionEntity> permissionEntities = roleEntity.getPermissions();
//            if (permissionEntities != null && !permissionEntities.isEmpty()) {
//                Set<Permission> permissions = new HashSet<>();
//                for (PermissionEntity permissionEntity : permissionEntities) {
//                    Permission permission = new Permission();
//                    permission.setName(permissionEntity.getName());
//                    permissions.add(permission);
//                }
//                role.setPermissions(permissions);
//            }

            user.setRole(role);
        }

        return user;
    }

    public Tag convert(TagEntity tagEntity) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagEntity, tag);
        return tag;
    }
}
