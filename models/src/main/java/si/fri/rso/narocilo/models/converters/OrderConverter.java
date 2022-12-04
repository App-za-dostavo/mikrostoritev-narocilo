package si.fri.rso.narocilo.models.converters;

import si.fri.rso.narocilo.lib.Order;
import si.fri.rso.narocilo.models.entities.OrderEntity;

public class OrderConverter {

    public static Order toDto(OrderEntity entity) {

        Order dto = new Order();
        dto.setId(entity.getId());
        dto.setClient(entity.getClient());
        dto.setProvider(entity.getProvider());
        dto.setItems(entity.getItems());
        dto.setCost(entity.getCost());

        return dto;
    }

    public static OrderEntity toEntity(Order dto) {

        OrderEntity entity = new OrderEntity();
        entity.setId(dto.getId());
        entity.setClient(dto.getClient());
        entity.setProvider(dto.getProvider());
        entity.setItems(dto.getItems());
        entity.setCost(dto.getCost());

        return entity;
    }
}
