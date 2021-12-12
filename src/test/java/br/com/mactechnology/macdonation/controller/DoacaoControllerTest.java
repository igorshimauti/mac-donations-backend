package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoDoacao;
import br.com.mactechnology.macdonation.dto.DtoDonatario;
import br.com.mactechnology.macdonation.dto.DtoFamiliar;
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
import java.util.ArrayList;
import java.util.List;

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
public class DoacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario admin;
    private String token;
    private Long donatarioIdCriado;
    private List<Long> doacoesIdCriados = new ArrayList<Long>();

    @BeforeAll
    public void initialize() throws Exception {
        Usuario admin = new Usuario("Madruga", "025.488.100-97", "madruga@gmail.com", "43D47E0599DEFAA36E267A9AEC54A5A5205AF3893501A18CFCF13B579C382ABB");
        admin.setAutorizado(true);
        admin.setAdmin(true);

        this.admin = usuarioRepository.save(admin);

        DtoLogin dtoLogin = new DtoLogin("madruga@gmail.com", "abcd1020");
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

        DtoDonatario dtoDonatario = new DtoDonatario();
        dtoDonatario.setNome("Mauricio");
        dtoDonatario.setCpf("035.027.210-76");
        dtoDonatario.setDataNascimento(LocalDate.of(1990, 8, 11));
        dtoDonatario.setCelular("(11) 99526-8449");
        dtoDonatario.setEndereco(
                new Endereco("04434-140","SP","São Paulo","Morro Grande","Rua Manfredo","27","")
        );

        MvcResult result = mockMvc.perform(post("/donatario")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoDonatario)))
                .andExpect(status().isOk())
                .andReturn();

        String valuesDonatario = result.getResponse().getContentAsString()
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "");

        this.donatarioIdCriado = Long.parseLong(valuesDonatario.substring(valuesDonatario.indexOf(":")+1, valuesDonatario.indexOf(",")));
    }

    @AfterAll
    public void finalization() {
        usuarioRepository.deleteById(admin.getId());
    }

    @Test
    @Order(1)
    public void createTest() throws Exception {
        DtoDoacao dtoDoacao = new DtoDoacao();
        dtoDoacao.setData(LocalDate.now());
        dtoDoacao.setDescricao("cesta basica");

        MvcResult result = mockMvc.perform(post("/donatario/" + this.donatarioIdCriado + "/doacao")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoDoacao)))
                .andExpect(status().isOk())
                .andReturn();

        String valuesDoacao = result.getResponse().getContentAsString()
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "");

        doacoesIdCriados.add(Long.parseLong(valuesDoacao.substring(valuesDoacao.indexOf(":")+1, valuesDoacao.indexOf(","))));

        dtoDoacao.setData(LocalDate.now());
        dtoDoacao.setDescricao("kit higiene");

        result = mockMvc.perform(post("/donatario/" + this.donatarioIdCriado + "/doacao")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoDoacao)))
                .andExpect(status().isOk())
                .andReturn();

        valuesDoacao = result.getResponse().getContentAsString()
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "");

        doacoesIdCriados.add(Long.parseLong(valuesDoacao.substring(valuesDoacao.indexOf(":")+1, valuesDoacao.indexOf(","))));
    }

    @Test
    @Order(2)
    public void readTest() throws Exception {
        mockMvc.perform(get("/donatario/" + this.donatarioIdCriado + "/doacao")
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":" + doacoesIdCriados.get(0) + ",\"data\":\"" + LocalDate.now().toString() + "\",\"descricao\":\"cesta basica\"},{\"id\":" + doacoesIdCriados.get(1) +",\"data\":\"" + LocalDate.now().toString() + "\",\"descricao\":\"kit higiene\"}]"));
    }

    @Test
    @Order(3)
    public void readByIdTest() throws Exception {
        mockMvc.perform(get("/donatario/" + this.donatarioIdCriado + "/doacao/" + doacoesIdCriados.get(0))
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + doacoesIdCriados.get(0) + ",\"data\":\"" + LocalDate.now().toString() + "\",\"descricao\":\"cesta basica\"}"));

        mockMvc.perform(get("/donatario/" + this.donatarioIdCriado + "/doacao/" + doacoesIdCriados.get(1))
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + doacoesIdCriados.get(1) + ",\"data\":\"" + LocalDate.now().toString() + "\",\"descricao\":\"kit higiene\"}"));
    }

    @Test
    @Order(4)
    public void updateTest() throws Exception {
        DtoDoacao dtoDoacao = new DtoDoacao();
        dtoDoacao.setData(LocalDate.now());
        dtoDoacao.setDescricao("cesta basica + ovos + frango");
        mockMvc.perform(put("/donatario/" + this.donatarioIdCriado + "/doacao/" + doacoesIdCriados.get(0))
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoDoacao)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + doacoesIdCriados.get(0) + ",\"data\":\"" + LocalDate.now().toString() + "\",\"descricao\":\"cesta basica + ovos + frango\"}"));

        dtoDoacao.setData(LocalDate.now());
        dtoDoacao.setDescricao("kit higiene + fraldas");
        mockMvc.perform(put("/donatario/" + this.donatarioIdCriado + "/doacao/" + doacoesIdCriados.get(1))
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoDoacao)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":" + doacoesIdCriados.get(1) + ",\"data\":\"" + LocalDate.now().toString() + "\",\"descricao\":\"kit higiene + fraldas\"}"));
    }

    @Test
    @Order(5)
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/donatario/" + this.donatarioIdCriado + "/doacao/" + doacoesIdCriados.get(0))
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/donatario/" + this.donatarioIdCriado + "/doacao/" + doacoesIdCriados.get(1))
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void readByIdAfterDeleteTest() throws Exception {
        mockMvc.perform(get("/donatario/" + this.donatarioIdCriado + "/doacao/" + doacoesIdCriados.get(0))
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("DoaÃ§Ã£o nÃ£o encontrada."));

        mockMvc.perform(get("/donatario/" + this.donatarioIdCriado + "/doacao/" + doacoesIdCriados.get(1))
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("DoaÃ§Ã£o nÃ£o encontrada."));
    }

    @Test
    @Order(7)
    public void deleteDonatarioTest() throws Exception {
        mockMvc.perform(delete("/donatario/" + this.donatarioIdCriado)
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}