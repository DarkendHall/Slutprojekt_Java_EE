package org.darkend.slutprojekt_java_ee.dto;

import org.modelmapper.ModelMapper;

public interface EntityToDtoConverter {

    void entityToDto(ModelMapper mapper);
}
