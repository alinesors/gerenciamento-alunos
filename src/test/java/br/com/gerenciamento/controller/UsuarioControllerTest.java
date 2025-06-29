package br.com.gerenciamento.controller;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //Para resetar o banco de dados para cada teste individualmente, deixando assim eles independentes (fixture)
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void cadastrar() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        int qtdUsuarioInicial = usuarioRepository.findAll().size();

        //O método não deve lançar exceção ao inserir um usuario válido
        assertDoesNotThrow(() ->{
            this.usuarioController.cadastrar(usuario);
        });

        int qtdUsuariosFinal = usuarioRepository.findAll().size();

        // Verifica se a quantidade de alunos aumentou em 1 apos a inserção
        assertEquals(qtdUsuarioInicial + 1, qtdUsuariosFinal); 

        // Verifica se o aluno foi inserido corretamente com suas informações
        Usuario usuarioRetornado = usuarioRepository.findByEmail("Aline@gmail.com");
        
        assertTrue(usuarioRetornado.getUser().equals("Aline"));
        
    }

    @Test
    public void index() throws Exception{
        
        ModelAndView mv = usuarioController.index();

        assertNotNull(mv);

        Map<String, Object> map = mv.getModel();

        Aluno alunoIndexado = (Aluno) map.get("aluno");

        assertNotNull(alunoIndexado);
    }

    @Test
    public void login() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        usuarioController.cadastrar(usuario);

        HttpSession session =  Mockito.mock(HttpSession.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(usuario, "usuario");
        
        ModelAndView mv = usuarioController.login(usuario, bindingResult, session);

        //Verifica se o ModelAndView não é nulo
        assertNotNull(mv);

        Map<String,Object> map =  mv.getModel();
        Usuario usuarioLogado =  (Usuario) map.get("usuario");

        // Verifica se o usuário logado não é nulo
        assertNotNull(usuarioLogado);
    }

    @Test
    public void loginInvalido() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("Aline@gmail.com");
        usuario.setUser("Aline");
        usuario.setSenha("123456");

        usuarioController.cadastrar(usuario);

        BindingResult bindingResult = new BeanPropertyBindingResult(usuario, "usuario");
        HttpSession session =  Mockito.mock(HttpSession.class);
        
        //Modifica o usuário para simular um login inválido
        usuario.setSenha(null);

        // Verifica se o método lança uma exceção ao tentar logar com um usuário inválido
        assertThrows(NullPointerException.class, () -> {
            usuarioController.login(usuario, bindingResult, session);
        }); 
    }
}
