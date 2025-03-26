package com.myproject94.myerp.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject94.myerp.Application;
import com.myproject94.myerp.domain.dtos.CredenciaisDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc
public class LoginSteps {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CredenciaisDTO credenciais;
    private MvcResult resultado;
    private String token;


    @Dado("que o usuário possui credenciais válidas")
    public void usuario_com_credenciais_validas() {
        credenciais = new CredenciaisDTO("valdir@mail.com", "123");
    }

    @Quando("ele envia uma requisição para o endpoint de login")
    public void envia_requisicao_login() throws Exception {
        resultado = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credenciais)))
                .andReturn();

        // Log para depuração
        System.out.println("Status da resposta: " + resultado.getResponse().getStatus());
        System.out.println("Cabeçalhos de resposta: " + resultado.getResponse().getHeaderNames());
        token = resultado.getResponse().getHeader("Authorization");
        System.out.println("Token recebido: " + token);
    }

    @Então("o sistema retorna um token JWT válido")
    public void sistema_retorna_token_jwt() {
        // Verifica se o token retornado não é nulo
        assertNotNull(token, "O token JWT não pode ser nulo");

        // Aqui você pode adicionar validações extras, como verificar se o token começa com "Bearer "
        assertTrue(token.startsWith("Bearer "), "O token deve começar com 'Bearer '");
    }

}
