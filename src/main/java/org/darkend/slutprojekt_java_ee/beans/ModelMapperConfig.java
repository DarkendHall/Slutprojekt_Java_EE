package org.darkend.slutprojekt_java_ee.beans;

import org.darkend.slutprojekt_java_ee.dto.CommonDto;
import org.darkend.slutprojekt_java_ee.dto.DtoToEntityConverter;
import org.darkend.slutprojekt_java_ee.dto.EntityToDtoConverter;
import org.darkend.slutprojekt_java_ee.dto.PrincipalDto;
import org.darkend.slutprojekt_java_ee.dto.StudentDto;
import org.darkend.slutprojekt_java_ee.dto.TeacherDto;
import org.darkend.slutprojekt_java_ee.dto.UserDtoIn;
import org.darkend.slutprojekt_java_ee.dto.UserDtoOut;
import org.darkend.slutprojekt_java_ee.entity.PrincipalEntity;
import org.darkend.slutprojekt_java_ee.entity.RoleEntity;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.darkend.slutprojekt_java_ee.entity.TeacherEntity;
import org.darkend.slutprojekt_java_ee.entity.UserEntity;
import org.darkend.slutprojekt_java_ee.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ModelMapperConfig {

    RoleRepository roleRepository;

    public ModelMapperConfig(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        new Student().setUp(mapper);
        new Teacher().setUp(mapper);
        new Principal().setUp(mapper);
        new User().setUp(mapper);
        return mapper;
    }

    public static ModelMapper getModelMapper(RoleRepository roleRepository) {
        return new ModelMapperConfig(roleRepository).modelMapper();
    }

    private static class Student implements EntityToDtoConverter, DtoToEntityConverter {
        protected void setUp(ModelMapper mapper) {
            var student = new Student();
            student.entityToDto(mapper);
            student.dtoToEntity(mapper);
        }

        public void entityToDto(ModelMapper mapper) {
            mapper.createTypeMap(StudentEntity.class, StudentDto.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> CommonDto.generateFullName(((StudentEntity) ctx.getSource()).getFirstName(),
                                    ((StudentEntity) ctx.getSource()).getLastName())).map(source,
                                    destination.getFullName());
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

    private static class Teacher implements EntityToDtoConverter, DtoToEntityConverter {
        protected void setUp(ModelMapper mapper) {
            var teacher = new Teacher();
            teacher.entityToDto(mapper);
            teacher.dtoToEntity(mapper);
        }

        @Override
        public void entityToDto(ModelMapper mapper) {
            mapper.createTypeMap(TeacherEntity.class, TeacherDto.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> CommonDto.generateFullName(((TeacherEntity) ctx.getSource()).getFirstName(),
                                    ((TeacherEntity) ctx.getSource()).getLastName())).map(source,
                                    destination.getFullName());
                        }
                    });
        }

        @Override
        public void dtoToEntity(ModelMapper mapper) {
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
        protected void setUp(ModelMapper mapper) {
            var principal = new Principal();
            principal.entityToDto(mapper);
            principal.dtoToEntity(mapper);
        }

        @Override
        public void entityToDto(ModelMapper mapper) {
            mapper.createTypeMap(PrincipalEntity.class, PrincipalDto.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> CommonDto.generateFullName(((PrincipalEntity) ctx.getSource()).getFirstName(),
                                    ((PrincipalEntity) ctx.getSource()).getLastName())).map(source,
                                    destination.getFullName());
                        }
                    });
        }

        @Override
        public void dtoToEntity(ModelMapper mapper) {
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

    private class User implements EntityToDtoConverter, DtoToEntityConverter {

        public void setUp(ModelMapper mapper) {
            var user = new User();
            user.entityToDto(mapper);
            user.dtoToEntity(mapper);
        }

        @Override
        public void entityToDto(ModelMapper mapper) {
            mapper.createTypeMap(UserEntity.class, UserDtoOut.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> convertToString(((UserEntity) ctx.getSource()).getRoles())).map(source,
                                    destination.getRoles());
                        }
                    });
        }

        private List<String> convertToString(List<RoleEntity> entities) {
            List<String> roles = new ArrayList<>();
            for (var entity : entities) {
                roles.add(entity.getRole());
            }
            return roles;
        }

        @Override
        public void dtoToEntity(ModelMapper mapper) {
            mapper.createTypeMap(UserDtoIn.class, UserEntity.class)
                    .addMappings(new PropertyMap<>() {
                        @Override
                        protected void configure() {
                            using(ctx -> (findRoles(ctx))).map(source,
                                    destination.getRoles());
                        }
                    });
        }

        private List<RoleEntity> findRoles(MappingContext<Object, Object> ctx) {
            var roleStrings = ((UserDtoIn) ctx.getSource()).getRoles();
            List<RoleEntity> roleEntities = new ArrayList<>();
            for (var role : roleStrings) {
                var foundRole = roleRepository.findByRole(role);
                if (foundRole == null)
                    throw new EntityNotFoundException(String.format("Couldn't find any role matching: %s", role));
                else
                    roleEntities.add(foundRole);
            }
            return roleEntities;
        }
    }
}
