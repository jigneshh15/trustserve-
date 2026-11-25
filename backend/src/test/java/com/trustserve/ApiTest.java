package com.trustserve;
import org.junit.jupiter.api.Test;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.test.web.servlet.MockMvc;import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:testdb","trustserve.demo-password=Testing12345!"}) @AutoConfigureMockMvc class ApiTest {
@Autowired MockMvc mvc;
@Test void publicServices()throws Exception{mvc.perform(get("/api/services")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(15));}
@Test void protectedBookings()throws Exception{mvc.perform(get("/api/bookings")).andExpect(status().isUnauthorized());}
@Test void csrfRequired()throws Exception{mvc.perform(post("/api/auth/login").contentType("application/json").content("{}")).andExpect(status().isForbidden());}
@Test void badCredentials()throws Exception{mvc.perform(post("/api/auth/login").with(csrf()).contentType("application/json").content("{\"email\":\"customer@trustserve.demo\",\"password\":\"wrong\"}")).andExpect(status().isBadRequest());}
@Test void loginWorks()throws Exception{mvc.perform(post("/api/auth/login").with(csrf()).contentType("application/json").content("{\"email\":\"customer@trustserve.demo\",\"password\":\"Testing12345!\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.password").doesNotExist());}
@Test void cannotRegisterAdmin()throws Exception{mvc.perform(post("/api/auth/register").with(csrf()).contentType("application/json").content("{\"name\":\"Test\",\"email\":\"test@example.com\",\"password\":\"Testing12345!\",\"role\":\"ADMIN\"}")).andExpect(status().isBadRequest());}
@Test void customerNotAdmin()throws Exception{mvc.perform(get("/api/admin/data").with(user("2").roles("CUSTOMER"))).andExpect(status().isForbidden());}
}
