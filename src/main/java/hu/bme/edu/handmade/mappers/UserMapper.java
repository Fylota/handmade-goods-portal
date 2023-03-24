package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.web.dto.user.UserDto;
import hu.bme.edu.handmade.web.dto.user.UserRegistrationDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto userToUserDto(User user);

    List<UserDto> usersToUserDtos(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDto dto, @MappingTarget User entity);

    @Mapping(target = "password", ignore = true)
    User toUserFromUserRegistrationDto(UserRegistrationDto dto);

    UserRegistrationDto userDtoToRegistrationDto(UserDto dto);

}
