package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.web.dto.ProductDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product toProduct(ProductDto productDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductDto dto, @MappingTarget Product entity);
}
