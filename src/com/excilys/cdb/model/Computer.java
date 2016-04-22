package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

    private Long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * default constructor for a computer
     */
    public Computer() {
        this.id = 0L;
        this.name = "";
        this.introduced = null;
        this.discontinued = null;
        this.company = new Company();
    }

    /**
     * constructor for a computer
     * 
     * @param id
     *            id
     * @param name
     *            name
     * @param introduced
     *            introduced date
     * @param discontinued
     *            discontinued date
     * @param company
     *            company (with id = 0 if no company)
     */
    public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }
    
    /**
     * Builder pattern for a computer
     * @author excilys
     */
    public static class ComputerBuilder {
        private Long id = 0L;
        private String name = "";
        private LocalDate introduced = null;
        private LocalDate discontinued = null;
        private Company company = new Company();

        public ComputerBuilder() {

        }

        public ComputerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ComputerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ComputerBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        public ComputerBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }
        
        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }
        
        public Computer build() {
            return new Computer(id, name, introduced, discontinued, company);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String toString() {

        String intro = introduced == null ? "null" : introduced.toString();
        String discon = discontinued == null ? "null" : discontinued.toString();

        return id + " " + name + " " + intro + " " + discon + " " + company.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (!company.equals(other.company))
            return false;
        if (discontinued == null) {
            if (other.discontinued != null)
                return false;
        } else if (!discontinued.equals(other.discontinued))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (introduced == null) {
            if (other.introduced != null)
                return false;
        } else if (!introduced.equals(other.introduced))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
