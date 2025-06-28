package br.com.gerenciamento.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //Para resetar o banco de dados para cada teste individualmente, deixando assim eles independentes (fixture)
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

     @Test
    public void salvarUsuario() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        // Testa um caso que não deve gerar exceção por está tudo correto
        assertDoesNotThrow(() ->{
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }

    @Test
    public void salvarSemSenha() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");

        assertThrows(NullPointerException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }


    @Test
    public void loginComDadosErrados() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioComLoginErrado = serviceUsuario.loginUser("Jennifer", "888888");

        assertNull(usuarioComLoginErrado);
    }

    @Test
    public void salvarComEmailDuplicado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioDuplicado = new Usuario();

        usuarioDuplicado.setId(2L);
        usuarioDuplicado.setEmail("Aline@gmail.com");
        usuarioDuplicado.setUser("Alice");
        usuarioDuplicado.setSenha("345213");

        assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuarioDuplicado);
        });
    }
}
