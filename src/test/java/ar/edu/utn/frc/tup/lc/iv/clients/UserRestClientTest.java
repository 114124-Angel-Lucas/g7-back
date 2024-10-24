package ar.edu.utn.frc.tup.lc.iv.clients;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserRestClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private UserRestClient userRestClient;
    @Test
    void getUserById() {
        UserDto userDto = new UserDto(1L , "LMaldonado");

        when(restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data/1" , UserDto.class)).thenReturn(ResponseEntity.ok(userDto));

        UserDto userDtoResult = userRestClient.getUserById(1L).getBody();

        Assertions.assertEquals(userDtoResult.getUserName() , userDto.getUserName());
        Assertions.assertEquals(userDtoResult.getId() , userDto.getId());
    }

    @Test
    void getUserByIdNotFoundExceptionWhenBodyIsNull() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUserName("LMaldonado");

        when(restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data/" + userDto.getId(), UserDto.class))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userRestClient.getUserById(1L);
        });

        Assertions.assertEquals("El usuario con el id " + userDto.getId() + " no existe", exception.getMessage());
    }

    @Test
    void getUserByIdThrowsHttpClientErrorException() {
        // Configuramos el mock para lanzar la excepción cuando se llama al método
        when(restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data/1", UserDto.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        HttpClientErrorException exception = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            userRestClient.getUserById(1L);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
    @Test
    void getUserByIdThrowsEntityNotFoundException() {
        when(restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data/11", UserDto.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userRestClient.getUserById(11L);
        });

        Assertions.assertEquals("El usuario con el id 11 no existe", exception.getMessage());
    }
    @Test
    void getAllUsers() {

        UserDto[] userDtos = {
                new UserDto(1L, "LMaldonado"),
                new UserDto(2L, "AMartinez")
        };

        when(restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data", UserDto[].class))
                .thenReturn(ResponseEntity.ok(userDtos));

        List<UserDto> result = userRestClient.getAllUsers();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("LMaldonado", result.get(0).getUserName());
        Assertions.assertEquals("AMartinez", result.get(1).getUserName());
    }

    @Test
    void getAllUsersThrowsExceptionWhenNoUsersFound() {
        when(restTemplate.getForEntity("https://retoolapi.dev/1iZtKu/data", UserDto[].class))
                .thenReturn(ResponseEntity.ok(null));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userRestClient.getAllUsers();
        });

        Assertions.assertEquals("No existen usuarios", exception.getMessage());
    }
}