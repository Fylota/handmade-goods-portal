package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.web.dto.PostDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    Post toProduct(PostDto postDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostFromDto(PostDto dto, @MappingTarget Post entity);
}
