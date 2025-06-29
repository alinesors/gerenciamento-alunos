package br.com.gerenciamento.controller;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //Para resetar o banco de dados para cada teste individualmente, deixando assim eles independentes (fixture)
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void inserirAluno() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        
        int qtdAlunoInicial = serviceAluno.findAll().size();
    
        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");

        //O método não deve lançar exceção ao inserir um aluno válido
        assertDoesNotThrow(() ->{
            this.alunoController.inserirAluno(aluno, bindingResult);
        });

        int qtdAlunosFinal = serviceAluno.findAll().size();

        // Verifica se a quantidade de alunos aumentou em 1 apos a inserção
        assertEquals(qtdAlunoInicial + 1, qtdAlunosFinal); 

        // Verifica se o aluno foi inserido corretamente com suas informações
        Aluno alunoRetornado = serviceAluno.findByNomeContainingIgnoreCase("Aline").get(0);
        assertEquals("123456" , alunoRetornado.getMatricula()); 
    }

    @Test
    public void listarAlunosAtivos() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        serviceAluno.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Jennifer");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");

        serviceAluno.save(aluno2);
        
        ModelAndView mv = alunoController.listaAlunosAtivos();

        Map<String,Object> map =  mv.getModel();
        List<Aluno> alunosAtivos = (List<Aluno>) map.get("alunosAtivos");

        // Deve retornar apenas o aluno ativo mesmo com dois alunos cadastrados
        assertEquals(1, alunosAtivos.size()); 

        // Verifica se o aluno ativo retornado é o correto
        assertTrue(alunosAtivos.get(0).getNome().equals("Aline"));
        assertTrue(alunosAtivos.get(0).getStatus() == Status.ATIVO);
        assertTrue(alunosAtivos.get(0).getMatricula().equals("123456"));

    }

    @Test
    public void listarAlunosInativos() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        serviceAluno.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Jennifer");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");

        serviceAluno.save(aluno2);
        
        ModelAndView mv = alunoController.listaAlunosInativos();

        Map<String,Object> map =  mv.getModel();
        List<Aluno> alunosAtivos = (List<Aluno>) map.get("alunosInativos");

        // Deve retornar apenas o aluno inativo mesmo com dois alunos cadastrados
        assertEquals(1, alunosAtivos.size()); 

        // Verifica se o aluno inativo retornado é o correto
        assertTrue(alunosAtivos.get(0).getNome().equals("Jennifer"));
        assertTrue(alunosAtivos.get(0).getStatus() == Status.INATIVO);
        assertTrue(alunosAtivos.get(0).getMatricula().equals("654321"));

    }

    @Test
    public void pesquisarAluno() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        serviceAluno.save(aluno);

        ModelAndView mv = alunoController.pesquisarAluno("Aline");

        // Verifica se o ModelAndView retornado não é nulo
        assertNotNull(mv);

        Map<String,Object> map =  mv.getModel();
        List<Aluno> retorno = (List<Aluno>) map.get("ListaDeAlunos");

        // Apenas um aluno se chama Aline, logo o tamanho da lista deve ser 1
        assertTrue(retorno.size() == 1);

        Aluno alunoProcurado = retorno.get(0);

        // Verifica se o aluno encontrado é o correto
        // Verifica se o aluno encontrado é o correto
        assertTrue(alunoProcurado.getNome().equals("Aline"));
        assertTrue(alunoProcurado.getStatus() == Status.ATIVO);
        assertTrue(alunoProcurado.getMatricula().equals("123456"));
        
    }
}
