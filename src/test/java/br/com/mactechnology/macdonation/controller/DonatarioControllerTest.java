package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoDonatario;
import br.com.mactechnology.macdonation.dto.DtoLogin;
import br.com.mactechnology.macdonation.model.Endereco;
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

import java.time.LocalDate;

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
public class DonatarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario admin;
    private String token;
    private Long donatarioIdCriado;

    @BeforeAll
    public void initialize() throws Exception {
        Usuario admin = new Usuario("João Alfredo", "705.649.500-17", "joao@gmail.com", "43D47E0599DEFAA36E267A9AEC54A5A5205AF3893501A18CFCF13B579C382ABB");
        admin.setAutorizado(true);
        admin.setAdmin(true);

        this.admin = usuarioRepository.save(admin);

        DtoLogin dtoLogin = new DtoLogin("joao@gmail.com", "abcd1020");
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

    @AfterAll
    public void finalization() {
        usuarioRepository.deleteById(admin.getId());
    }

    @Test
    @Order(1)
    public void createTest() throws Exception {
        DtoDonatario dtoDonatario = new DtoDonatario();
        dtoDonatario.setNome("Ricardo");
        dtoDonatario.setCpf("084.567.050-63");
        dtoDonatario.setDataNascimento(LocalDate.of(1990, 8, 11));
        dtoDonatario.setCelular("(11) 99736-6549");
        dtoDonatario.setEndereco(
                new Endereco("04434-140","SP","São Paulo","Morro Grande","Rua Manfredo","27","")
        );

        MvcResult result = mockMvc.perform(post("/donatario")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoDonatario)))
                .andExpect(status().isCreated())
                .andReturn();

        String valuesDonatario = result.getResponse().getContentAsString()
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "");

        this.donatarioIdCriado = Long.parseLong(valuesDonatario.substring(valuesDonatario.indexOf(":")+1, valuesDonatario.indexOf(",")));
    }

    @Test
    @Order(2)
    public void readTest() throws Exception {
        mockMvc.perform(get("/donatario")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":" + this.donatarioIdCriado + ",\"nome\":\"Ricardo\",\"cpf\":\"084.567.050-63\",\"dataNascimento\":\"1990-08-11\",\"celular\":\"(11) 99736-6549\",\"endereco\":{\"id\":" + this.donatarioIdCriado + ",\"cep\":\"04434-140\",\"uf\":\"SP\",\"cidade\":\"SÃ£o Paulo\",\"bairro\":\"Morro Grande\",\"logradouro\":\"Rua Manfredo\",\"numero\":\"27\",\"complemento\":\"\"}}]"));
    }

    @Test
    @Order(3)
    public void readByIdTest() throws Exception {
        mockMvc.perform(get("/donatario/" + this.donatarioIdCriado)
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + this.donatarioIdCriado + ",\"nome\":\"Ricardo\",\"cpf\":\"084.567.050-63\",\"dataNascimento\":\"1990-08-11\",\"celular\":\"(11) 99736-6549\",\"endereco\":{\"id\":" + this.donatarioIdCriado + ",\"cep\":\"04434-140\",\"uf\":\"SP\",\"cidade\":\"SÃ£o Paulo\",\"bairro\":\"Morro Grande\",\"logradouro\":\"Rua Manfredo\",\"numero\":\"27\",\"complemento\":\"\"}}"));
    }

    @Test
    @Order(4)
    public void updateTest() throws Exception {
        DtoDonatario dtoDonatario = new DtoDonatario();
        dtoDonatario.setNome("Ricardo Shimauti");
        dtoDonatario.setCpf("084.567.050-63");
        dtoDonatario.setDataNascimento(LocalDate.of(1990, 8, 11));
        dtoDonatario.setCelular("(11) 99736-6549");
        dtoDonatario.setEndereco(
                new Endereco("04434-140","SP","São Paulo","Morro Grande","Rua Manfredo","27","")
        );

        dtoDonatario.getEndereco().setId(this.donatarioIdCriado);
        mockMvc.perform(put("/donatario/" + this.donatarioIdCriado)
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoDonatario)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + this.donatarioIdCriado + ",\"nome\":\"Ricardo Shimauti\",\"cpf\":\"084.567.050-63\",\"dataNascimento\":\"1990-08-11\",\"celular\":\"(11) 99736-6549\",\"endereco\":{\"id\":" + this.donatarioIdCriado + ",\"cep\":\"04434-140\",\"uf\":\"SP\",\"cidade\":\"SÃ£o Paulo\",\"bairro\":\"Morro Grande\",\"logradouro\":\"Rua Manfredo\",\"numero\":\"27\",\"complemento\":\"\"}}"));
    }

    @Test
    @Order(5)
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/donatario/" + this.donatarioIdCriado)
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void readByIdAfterDeleteTest() throws Exception {
        mockMvc.perform(get("/donatario/" + this.donatarioIdCriado)
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("DonatÃ¡rio nÃ£o encontrado."));
    }
}