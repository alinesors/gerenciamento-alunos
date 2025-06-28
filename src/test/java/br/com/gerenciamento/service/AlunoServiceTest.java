package br.com.gerenciamento.service;

import org.junit.Assert;
import static org.junit.Assert.assertThrows;
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
import jakarta.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //Para resetar o banco de dados para cada teste individualmente, deixando assim eles independentes (fixture)
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }


    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }


    @Test
    public void save(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("213415");

        int qtdAlunosInicial = this.serviceAluno.findAll().size();

        this.serviceAluno.save(aluno);

        int qtdAlunosFinal = this.serviceAluno.findAll().size();

        assertTrue(qtdAlunosInicial + 1 == qtdAlunosFinal);
    }

    @Test
    public void deleteById(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("213415");
        this.serviceAluno.save(aluno);

        int qtdAlunosInicial = this.serviceAluno.findAll().size();

        this.serviceAluno.deleteById(1L);

        int qtdAlunosFinal = this.serviceAluno.findAll().size();

        assertTrue(qtdAlunosInicial - 1 == qtdAlunosFinal);
    }


    @Test
    public void salvarSemCurso(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        assertThrows(ConstraintViolationException.class, () ->{
            serviceAluno.save(aluno);
        });
    }

    @Test
    public void salvarSemMatricula(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aline");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setMatricula("123456");

        assertThrows(ConstraintViolationException.class, () ->{
            serviceAluno.save(aluno);
        });
    }

    
}