package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.web.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDto productToProductDto(Product product);
    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductDto productDto);
}
