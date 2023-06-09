package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Role;
import hu.bme.edu.handmade.web.dto.user.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    List<RoleDto> toRoleDtos(Iterable<Role> roles);
}
