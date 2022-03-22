package org.darkend.slutprojekt_java_ee.beans;

import org.darkend.slutprojekt_java_ee.dto.CommonDto;
import org.darkend.slutprojekt_java_ee.dto.DtoToEntityConverter;
import org.darkend.slutprojekt_java_ee.dto.EntityToDtoConverter;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
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
        Teacher.setUp(mapper);
        Principal.setUp(mapper);
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
            mapper.createTypeMap(StudentEntity.class, StudentDto.class)
                    .addMappings(
                            new PropertyMap<>() {
                                @Override
                                protected void configure() {
                                    using(ctx -> CommonDto.generateFullName(
                                            ((StudentEntity) ctx.getSource()).getFirstName(),
                                            ((StudentEntity) ctx.getSource()).getLastName()
                                    )).map(source, destination.getFullName());
                                }
                            });
        }

        public void dtoToEntity(ModelMapper mapper) {
            mapper.createTypeMap(StudentDto.class, StudentEntity.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> CommonDto.generateFirstName(((StudentDto) ctx.getSource()))).map(source,
                                    destination.getFirstName());
                            using(ctx -> CommonDto.generateLastName(((StudentDto) ctx.getSource()))).map(source,
                                    destination.getLastName());
                        }
                    });
        }
    }

    static class Teacher implements EntityToDtoConverter, DtoToEntityConverter {
        protected static void setUp(ModelMapper mapper) {
            var teacher = new Teacher();
            teacher.entityToDto(mapper);
            teacher.dtoToEntity(mapper);
        }

        @Override
        public void dtoToEntity(ModelMapper mapper) {
            mapper.createTypeMap(TeacherEntity.class, TeacherDto.class)
                    .addMappings(
                            new PropertyMap<>() {
                                @Override
                                protected void configure() {
                                    using(ctx -> CommonDto.generateFullName(
                                            ((TeacherEntity) ctx.getSource()).getFirstName(),
                                            ((TeacherEntity) ctx.getSource()).getLastName()
                                    )).map(source, destination.getFullName());
                                }
                            });
        }

        @Override
        public void entityToDto(ModelMapper mapper) {
            mapper.createTypeMap(TeacherDto.class, TeacherEntity.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> CommonDto.generateFirstName(((TeacherDto) ctx.getSource()))).map(source,
                                    destination.getFirstName());
                            using(ctx -> CommonDto.generateLastName(((TeacherDto) ctx.getSource()))).map(source,
                                    destination.getLastName());
                        }
                    });
        }
    }

    private static class Principal implements EntityToDtoConverter, DtoToEntityConverter {
        protected static void setUp(ModelMapper mapper) {
            var principal = new Principal();
            principal.entityToDto(mapper);
            principal.dtoToEntity(mapper);
        }

        @Override
        public void dtoToEntity(ModelMapper mapper) {
            mapper.createTypeMap(PrincipalEntity.class, PrincipalDto.class)
                    .addMappings(
                            new PropertyMap<>() {
                                @Override
                                protected void configure() {
                                    using(ctx -> CommonDto.generateFullName(
                                            ((PrincipalEntity) ctx.getSource()).getFirstName(),
                                            ((PrincipalEntity) ctx.getSource()).getLastName()
                                    )).map(source, destination.getFullName());
                                }
                            });
        }

        @Override
        public void entityToDto(ModelMapper mapper) {
            mapper.createTypeMap(PrincipalDto.class, PrincipalEntity.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> CommonDto.generateFirstName(((PrincipalDto) ctx.getSource()))).map(source,
                                    destination.getFirstName());
                            using(ctx -> CommonDto.generateLastName(((PrincipalDto) ctx.getSource()))).map(source,
                                    destination.getLastName());
                        }
                    });
        }
    }
}
