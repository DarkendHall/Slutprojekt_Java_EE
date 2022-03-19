package org.darkend.slutprojekt_java_ee.beans;

import org.darkend.slutprojekt_java_ee.dto.CommonDTO;
import org.darkend.slutprojekt_java_ee.dto.DtoToEntityConverter;
import org.darkend.slutprojekt_java_ee.dto.EntityToDtoConverter;
import org.darkend.slutprojekt_java_ee.dto.StudentDTO;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelMapperConfig {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        Student.setUp(mapper);
        return mapper;
    }

    public static ModelMapper getModelMapper() {
        return new ModelMapperConfig().modelMapper();
    }


    static class Student implements EntityToDtoConverter, DtoToEntityConverter {
        protected static void setUp(ModelMapper mapper) {
            var student = new Student();
            student.entityToDto(mapper);
            student.dtoToEntity(mapper);
        }

        public void entityToDto(ModelMapper mapper) {
            mapper.createTypeMap(StudentEntity.class, StudentDTO.class)
                    .addMappings(
                            new PropertyMap<>() {
                                @Override
                                protected void configure() {
                                    using(ctx -> CommonDTO.generateFullName(
                                            ((StudentEntity) ctx.getSource()).getFirstName(),
                                            ((StudentEntity) ctx.getSource()).getLastName()
                                    )).map(source, destination.getFullName());
                                }
                            });
        }

        public void dtoToEntity(ModelMapper mapper) {
            mapper.createTypeMap(StudentDTO.class, StudentEntity.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> CommonDTO.generateFirstName(((StudentDTO) ctx.getSource()))).map(source,
                                    destination.getFirstName());
                            using(ctx -> CommonDTO.generateLastName(((StudentDTO) ctx.getSource()))).map(source,
                                    destination.getLastName());
                        }
                    });
        }
    }
}
