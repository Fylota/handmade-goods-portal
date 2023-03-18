package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Order;
import hu.bme.edu.handmade.models.OrderProduct;
import hu.bme.edu.handmade.web.dto.OrderItemDto;
import hu.bme.edu.handmade.web.dto.OrderProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "items", source = "products")
    OrderItemDto orderToOrderItemDto(Order order);

    @Mapping(target = "productId", source = "product.id")
    OrderProductDto orderProductToDto(OrderProduct op);

}
