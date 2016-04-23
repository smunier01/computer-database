package com.excilys.cdb.computer;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerTest {

	@Test
	public void testBuilder() {

		Computer comp = (new Computer.ComputerBuilder())
				.id(1L)
				.build();
		
		Assert.assertNotNull(comp);
		Assert.assertEquals((Long)1L, comp.getId());

	}
	
	@Test
	public void testBuilder2() {
		LocalDate d1 = LocalDate.now().minusYears(1);
		LocalDate d2 = d1.plusYears(2);
		
		Company company = new Company(2L, "DefaultCompany");
		
		Computer comp = (new Computer.ComputerBuilder())
				.id(1L)
				.name("MyComputerName")
				.introduced(d1)
				.discontinued(d2)
				.company(company)
				.build();
		
		Assert.assertNotNull(comp);
		Assert.assertEquals((Long)1L, comp.getId());
		Assert.assertEquals("MyComputerName", comp.getName());
		Assert.assertEquals(d1, comp.getIntroduced());
		Assert.assertEquals(d2, comp.getDiscontinued());
		Assert.assertEquals(company, comp.getCompany());
	}
	
	@Test
	public void testEquals() {
		
		LocalDate d1 = LocalDate.now().minusYears(1);
		LocalDate d2 = d1.plusYears(2);
		Company company = new Company(2L, "DefaultCompany");
		
		Computer a = (new Computer.ComputerBuilder())
				.id(1L)
				.name("MyComputerName")
				.introduced(d1)
				.discontinued(d2)
				.company(company)
				.build();
		
		Computer b = (new Computer.ComputerBuilder())
				.id(1L)
				.name("MyComputerName")
				.introduced(d1)
				.discontinued(d2)
				.company(company)
				.build();
		
		Assert.assertEquals(a, b);
		
		b.setCompany(null);
		
		Assert.assertNotEquals(a, b);
	}

}
