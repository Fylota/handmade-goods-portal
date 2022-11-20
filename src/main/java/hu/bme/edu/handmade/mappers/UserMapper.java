package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto userToUserDto(User user);
    User ToUser(UserDto userDto);
}
