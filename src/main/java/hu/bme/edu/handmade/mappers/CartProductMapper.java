package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.CartProduct;
import hu.bme.edu.handmade.web.dto.CartProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartProductMapper {
    CartProductMapper INSTANCE = Mappers.getMapper(CartProductMapper.class);

    @Mapping(target = "id", ignore = true)
    CartProduct toCartProduct(CartProductDto cartProductDto);
}
