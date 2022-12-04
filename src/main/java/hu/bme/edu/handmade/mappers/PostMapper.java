package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Post;
import hu.bme.edu.handmade.web.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    @Mapping(target = "id", ignore = true)
    Post toProduct(PostDto postDto);
}
