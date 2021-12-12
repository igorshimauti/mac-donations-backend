package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoLogin;
import br.com.mactechnology.macdonation.model.Usuario;
import br.com.mactechnology.macdonation.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario admin;
    private Long usuarioIdCriado;
    private String token;

    @BeforeAll
    public void initialize() {
        Usuario admin = new Usuario("Joaquim dos Reis", "516.037.130-34", "joaquim@gmail.com", "43D47E0599DEFAA36E267A9AEC54A5A5205AF3893501A18CFCF13B579C382ABB");
        admin.setAutorizado(true);
        admin.setAdmin(true);

        this.admin = usuarioRepository.save(admin);
    }

    @Test
    @Order(1)
    public void createTest() throws Exception {
        Usuario usuario = new Usuario("Frederico","607.955.240-05","frederico@teste.com","123456789");
        MvcResult result = mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andReturn();

        String valuesLogin = result.getResponse().getContentAsString()
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "");

        this.usuarioIdCriado = Long.parseLong(valuesLogin.substring(valuesLogin.indexOf(":")+1, valuesLogin.indexOf(",")));
    }

    @Test
    @Order(2)
    public void logarTest() throws Exception {
        DtoLogin dtoLogin = new DtoLogin("joaquim@gmail.com", "abcd1020");
        MvcResult resultLogin = mockMvc.perform(post("/usuario/logar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoLogin)))
                .andExpect(status().isOk())
                .andReturn();

        String[] valuesLogin = resultLogin.getResponse().getContentAsString()
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "")
                .split(",");

        for (String value : valuesLogin) {
            if (this.token == null) {
                this.token = value.substring(value.indexOf(":")+1);
            } else {
                this.token = this.token + " " + value.substring(value.indexOf(":")+1);
            }
        }
    }

    @Test
    @Order(3)
    public void authorizeTest() throws Exception {
        mockMvc.perform(put("/usuario/" + this.usuarioIdCriado + "/autorizar")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + this.usuarioIdCriado + ",\"nome\":\"Frederico\",\"cpf\":\"607.955.240-05\",\"email\":\"frederico@teste.com\",\"autorizado\":true,\"admin\":false}"));
    }

    @Test
    @Order(4)
    public void setAdminTest() throws Exception {
        mockMvc.perform(put("/usuario/" + this.usuarioIdCriado + "/setAdmin")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + this.usuarioIdCriado + ",\"nome\":\"Frederico\",\"cpf\":\"607.955.240-05\",\"email\":\"frederico@teste.com\",\"autorizado\":true,\"admin\":true}"));
    }

    @Test
    @Order(5)
    public void readTest() throws Exception {
        mockMvc.perform(get("/usuario")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":" + this.admin.getId() + ",\"nome\":\"Joaquim dos Reis\",\"cpf\":\"516.037.130-34\",\"email\":\"joaquim@gmail.com\",\"autorizado\":true,\"admin\":true},{\"id\":" + this.usuarioIdCriado + ",\"nome\":\"Frederico\",\"cpf\":\"607.955.240-05\",\"email\":\"frederico@teste.com\",\"autorizado\":true,\"admin\":true}]"));
    }

    @Test
    @Order(6)
    public void readByIdTest() throws Exception {
        mockMvc.perform(get("/usuario/" + this.admin.getId())
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + this.admin.getId() + ",\"nome\":\"Joaquim dos Reis\",\"cpf\":\"516.037.130-34\",\"email\":\"joaquim@gmail.com\",\"autorizado\":true,\"admin\":true}"));

        mockMvc.perform(get("/usuario/" + this.usuarioIdCriado)
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + this.usuarioIdCriado + ",\"nome\":\"Frederico\",\"cpf\":\"607.955.240-05\",\"email\":\"frederico@teste.com\",\"autorizado\":true,\"admin\":true}"));
    }

    @Test
    @Order(7)
    public void deleteTest()throws Exception {
        mockMvc.perform(delete("/usuario/" + this.usuarioIdCriado)
                        .header("Authorization", this.token)
                        /*.contentType(MediaType.APPLICATION_JSON)*/)
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    public void readByIdAfterDeleteTest() throws Exception {
        mockMvc.perform(get("/usuario/" + this.usuarioIdCriado)
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}