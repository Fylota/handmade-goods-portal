package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.CartProduct;
import hu.bme.edu.handmade.web.dto.CartProductDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartProductMapper {
    CartProductMapper INSTANCE = Mappers.getMapper(CartProductMapper.class);
    CartProduct toCartProduct(CartProductDto cartProductDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCartProductFromDto(CartProductDto dto, @MappingTarget CartProduct entity);
}
