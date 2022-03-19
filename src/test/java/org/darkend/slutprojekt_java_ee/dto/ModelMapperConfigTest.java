package org.darkend.slutprojekt_java_ee.dto;

import org.darkend.slutprojekt_java_ee.beans.ModelMapperConfig;
import org.darkend.slutprojekt_java_ee.entity.StudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.*;

class ModelMapperConfigTest {

    private final ModelMapper mapper = ModelMapperConfig.getModelMapper();

    @Nested
    class StudentTests {

        @Nested
        class StudentEntityToDTOTests {

            private StudentEntity entity;

            @BeforeEach
            public void setUp() {
                entity = new StudentEntity().setId(1L)
                        .setFirstName("First")
                        .setLastName("Last")
                        .setPhoneNumber("N/A")
                        .setEmail("email@email.com");

            }

            @Test
            void firstNameAndLastNameShouldBeCombinedToFullName() {
                var result = mapper.map(entity, StudentDTO.class);

                assertThat(result.getFullName()).isEqualTo("First Last");
            }


            @Test
            void fullMappingShouldBeTheEqualToManualDTO() {
                var dto = new StudentDTO().setId(1L)
                        .setFullName("First Last")
                        .setPhoneNumber("N/A")
                        .setEmail("email@email.com");

                var result = mapper.map(entity, StudentDTO.class);

                assertThat(result.getId()).isEqualTo(1L);
                assertThat(result.getFullName()).isEqualTo("First Last");
                assertThat(result.getPhoneNumber()).isEqualTo("N/A");
                assertThat(result.getEmail()).isEqualTo("email@email.com");
                assertThat(result).isEqualTo(dto);
            }

        }

        @Nested
        class StudentDTOToEntityTests {

            private StudentDTO dto;

            @BeforeEach
            public void setUp() {
                dto = new StudentDTO().setId(1L)
                        .setFullName("First Last")
                        .setPhoneNumber("N/A")
                        .setEmail("email@email.com");
            }

            @Test
            void fullNameShouldBeSplitIntoFirstNameAndLastName() {
                var result = mapper.map(dto, StudentEntity.class);

                assertThat(result.getFirstName() + " " + result.getLastName()).isEqualTo("First Last");
            }

            @Test
            void fullMappingShouldBeTheEqualToManualEntity() {
                StudentEntity entity = new StudentEntity().setId(1L)
                        .setFirstName("First")
                        .setLastName("Last")
                        .setPhoneNumber("N/A")
                        .setEmail("email@email.com");
                var result = mapper.map(dto, StudentEntity.class);

                assertThat(result.getId()).isEqualTo(1L);
                assertThat(result.getFirstName()).isEqualTo("First");
                assertThat(result.getLastName()).isEqualTo("Last");
                assertThat(result.getPhoneNumber()).isEqualTo("N/A");
                assertThat(result.getEmail()).isEqualTo("email@email.com");
                assertThat(result).isEqualTo(entity);
            }
        }
    }
}
