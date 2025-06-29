package br.com.gerenciamento.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.model.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //Para resetar o banco de dados para cada teste individualmente, deixando assim eles independentes (fixture)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void findByEmail() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        this.usuarioRepository.save(usuario);

        Usuario usarioRetorno = usuarioRepository.findByEmail("Aline@gmail.com");

        assertTrue(usarioRetorno.getEmail().equals("Aline@gmail.com"));
        
    }

    @Test
    public void procurarUsuarioComEmailInexistente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        this.usuarioRepository.save(usuario);

        Usuario usuarioRetorno = usuarioRepository.findByEmail("Jennifer@gmail.com");

        assertNull(usuarioRetorno);
    }

    @Test
    public void buscarLogin(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        this.usuarioRepository.save(usuario);

        assertEquals("Aline@gmail.com", usuarioRepository.buscarLogin("Aline", "123456").getEmail());
    }

    @Test
    public void loguinComDadosFaltando(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        this.usuarioRepository.save(usuario);

        Usuario usuarioRetorno = this.usuarioRepository.buscarLogin("Aline", null);

        assertNull(usuarioRetorno); 
    }
}
