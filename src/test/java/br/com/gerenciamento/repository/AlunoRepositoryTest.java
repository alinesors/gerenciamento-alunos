package br.com.gerenciamento.repository;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //Para resetar o banco de dados para cada teste individualmente, deixando assim eles independentes (fixture)
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void findByNomeContainingIgnoreCase(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        this.alunoRepository.save(aluno);

        Aluno alunoRetornado = this.alunoRepository.findByNomeContainingIgnoreCase("Aline").get(0);
    
        assertTrue(aluno.getNome().equals(alunoRetornado.getNome()) && aluno.getMatricula().equals(alunoRetornado.getMatricula()));
    }

    @Test
    public void findByStatusAtivo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        this.alunoRepository.save(aluno);

        Aluno alunoRetorno = this.alunoRepository.findByStatusAtivo().get(0);
    
        assertTrue(alunoRetorno.getStatus() == Status.ATIVO && alunoRetorno.getNome().equals("Aline"));
    }

    @Test
    public void findByStatusInativo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");

        this.alunoRepository.save(aluno);

        Aluno alunoRetorno = this.alunoRepository.findByStatusInativo().get(0);
    
        assertTrue(alunoRetorno.getStatus() == Status.INATIVO && alunoRetorno.getNome().equals("Aline"));
    }
    
    @Test
    public void buscarAlunoInexistentePeloNome(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        this.alunoRepository.save(aluno);

        int alunoRetornado = this.alunoRepository.findByNomeContainingIgnoreCase("Jeniffer").size();

        // Nenhum aluno foi encontrado, então o tamanho da lista deve ser 0
        assertTrue(alunoRetornado == 0);
        
    }
}
