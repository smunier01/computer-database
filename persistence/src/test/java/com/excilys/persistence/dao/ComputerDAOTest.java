package com.excilys.persistence.dao;

import com.excilys.core.model.Company;
import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ComputerDAOTest {

    @Autowired
    private ComputerDAO computerDAO;

    // -------------------------------------- Find tests ---------------------------------------------------------------
    @Test
    @Transactional
    public void testFindShouldHaveValidId() {


        Computer result = this.computerDAO.find(-1L);
        assertNull(result);

        result = this.computerDAO.find(0L);
        assertNull(result);

    }

    @Test
    public void findTest() {
        Computer comp = computerDAO.find(2L);
        assertEquals(comp.getName(), "CM-2a");
    }

    // -------------------------------------- Create tests -------------------------------------------------------------
    @Test
    @Transactional
    public void createTest() {
        Computer comp = new Computer.ComputerBuilder().name("createComputerTestDAO").build();
        Computer created = computerDAO.create(comp);
        assertNotNull(created);
        assertNotNull(created.getId());

        computerDAO.delete(created);
    }

    @Test
    @Transactional
    public void createEqualTest() {
        Computer comp = new Computer.ComputerBuilder().name("createEqualsComputerTestDAO").build();
        Computer created = computerDAO.create(comp);
        comp.setId(created.getId());
        assertEquals(comp,created);

        computerDAO.delete(created);
    }


    // -------------------------------------- Update tests -------------------------------------------------------------

    @Test
    @Transactional
    public void updateTest() {
        Computer computer = new Computer.ComputerBuilder().name("updateTestDAO").build();
        computer = computerDAO.create(computer);
        computer.setName("updatedTestDAO");
        computer.setIntroduced(LocalDate.of(2012,02,25));
        computer.setDiscontinued(LocalDate.now());
        computer.setCompany(new Company(3L,"RCA"));
        computerDAO.update(computer);
        Computer retrieve = computerDAO.find(computer.getId());
        assertEquals(retrieve, computer);

        computerDAO.delete(computer);
    }


    // -------------------------------------- Remove tests -------------------------------------------------------------


    @Test
    @Transactional
    public void removeTest() {
        Computer computer = new Computer.ComputerBuilder().name("removedTestDAO").build();
        Computer created = computerDAO.create(computer);
        Long id = created.getId();
        computerDAO.delete(created);
        Computer retrieve = computerDAO.find(id);
        assertNull(retrieve);
    }


    // -------------------------------------- Count tests --------------------------------------------------------------

    @Test
    @Transactional
    public void CountTest() {
        long countInitial = computerDAO.count();
        Computer comp = new Computer.ComputerBuilder().name("CountTestDAO").build();
        computerDAO.create(comp);
        long count = computerDAO.count();
        assertEquals(countInitial+1,count);

        computerDAO.delete(comp);
    }

    @Test @Ignore
    @Transactional
    public void CountTestFound() {
        PageParameters pageParameters = new PageParameters.Builder().search("count").build();
        long countInitial = computerDAO.count(pageParameters);
        Computer comp = new Computer.ComputerBuilder().name("count TestDAO1").build();
        computerDAO.create(comp);
        long count = computerDAO.count(pageParameters);
        assertEquals(countInitial+1,count);

        computerDAO.delete(comp);
    }

    @Test
    @Transactional
    public void CountTestNotFound() {
        PageParameters pageParameters = new PageParameters.Builder().search("hunt").build();
        long countInitial = computerDAO.count(pageParameters);
        Computer comp = new Computer.ComputerBuilder().name("Count TestDAO2").build();
        computerDAO.create(comp);
        long count = computerDAO.count(pageParameters);
        assertEquals(countInitial,count);

        computerDAO.delete(comp);
    }

    // -------------------------------------- Find all tests -----------------------------------------------------------
    @Test
    @Transactional
    public void findAllTestASC() {
        PageParameters pageParameters = new PageParameters.Builder().search("apple").direction(PageParameters.Direction.ASC).size(10).build();
        List<Computer> computers = computerDAO.findAll(pageParameters);
        assertTrue(computers.size() <= 10);
        for (int i =1; i < computers.size(); i++) {
            Computer comp = computers.get(i);
            Computer before = computers.get(i-1);
            boolean searchName = comp.getName().toLowerCase().contains("apple");
            assertTrue(searchName);
            int nameOrder = comp.getName().compareTo(before.getName());
            assertTrue(nameOrder >= 0);
        }
    }

    @Test
    @Transactional
    public void findAllTestSearchDESC() {
        PageParameters pageParameters = new PageParameters.Builder().search("apple").direction(PageParameters.Direction.DESC).size(100).build();
        List<Computer> computers = computerDAO.findAll(pageParameters);
        assertTrue(computers.size() <= 100);

        for (int i =1; i < computers.size(); i++) {
            Computer before = computers.get(i-1);
            Computer computer = computers.get(i);

            int nameOrder = computer.getName().compareTo(before.getName());
            assertTrue(nameOrder <= 0);

            boolean searchName = computer.getName().toLowerCase().contains("apple");
            assertTrue(searchName);
        }
    }

    @Test
    @Transactional
    public void findAllTestSearchOrder() {
        PageParameters pageParameters = new PageParameters.Builder().search("apple").order(PageParameters.Order.COMPANY_NAME).direction(PageParameters.Direction.DESC).size(10).build();
        List<Computer> computers = computerDAO.findAll(pageParameters);
        assertTrue(computers.size() <= 10);
        for (int i =1; i < computers.size(); i++) {

            Computer comp = computers.get(i);
            Computer before = computers.get(i-1);
            boolean searchName = comp.getName().toLowerCase().contains("apple");
            assertTrue(searchName);
            int nameOrder = comp.getName().compareTo(before.getName());
            assertTrue(nameOrder <= 0);
        }
    }

    @Test
    @Transactional
    public void findAllTestOrder() {
        PageParameters pageParameters = new PageParameters.Builder().order(PageParameters.Order.NAME).direction(PageParameters.Direction.DESC).size(100).build();
        List<Computer> computers = computerDAO.findAll(pageParameters);
        assertTrue(computers.size() <= 100);
        for (int i =1; i < computers.size(); i++) {
            Computer before = computers.get(i-1);
            Computer comp = computers.get(i);
            int nameOrder = comp.getName().toLowerCase().compareTo(before.getName().toLowerCase());
            assertTrue(nameOrder <= 0);
        }
    }
}