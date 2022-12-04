package si.fri.rso.narocilo.models.converters;

import si.fri.rso.narocilo.lib.NarociloMetadata;
import si.fri.rso.narocilo.models.entities.NarociloMetadataEntity;

public class NarociloMetadataConverter {

    public static NarociloMetadata toDto(NarociloMetadataEntity entity) {

        NarociloMetadata dto = new NarociloMetadata();
        dto.setId(entity.getId());
        dto.setClient(entity.getClient());
        dto.setProvider(entity.getProvider());
        dto.setContents(entity.getContents());
        dto.setCost(entity.getCost());
        return dto;

    }

    public static NarociloMetadataEntity toEntity(NarociloMetadata dto) {

        NarociloMetadataEntity entity = new NarociloMetadataEntity();
        entity.setId(dto.getId());
        entity.setClient(dto.getClient());
        entity.setProvider(dto.getProvider());
        entity.setContents(dto.getContents());
        entity.setCost(dto.getCost());
        return entity;

    }

}
