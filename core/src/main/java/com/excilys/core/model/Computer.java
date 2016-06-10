package com.excilys.core.model;

import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.persistence.Index;
import java.time.LocalDate;

import static com.excilys.core.model.QCompany.company;

@Entity
@Indexed
@Table(name = "computer")
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field(analyze = Analyze.NO)
    @SortableField
    private String name;

    @Field
    @SortableField
    private LocalDate introduced;

    @Field
    @SortableField
    private LocalDate discontinued;

    @IndexedEmbedded
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * default constructor for a computer.
     */
    public Computer() {
        this.id = null;
        this.name = "";
        this.introduced = null;
        this.discontinued = null;
        this.company = null;
    }

    /**
     * constructor for a computer.
     *
     * @param id           id
     * @param name         name
     * @param introduced   introduced date
     * @param discontinued discontinued date
     * @param company      company (with id = 0 if no company)
     */
    public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIntroduced() {
        return this.introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return this.discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Builder pattern for a computer.
     */
    public static class ComputerBuilder {
        private Long id = null;
        private String name = "";
        private LocalDate introduced = null;
        private LocalDate discontinued = null;
        private Company company = null;

        /**
         * default constructor for the builder.
         */
        public ComputerBuilder() {

        }

        /**
         * id setter.
         *
         * @param id id of the computer
         * @return instance of the builder
         */
        public ComputerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * name setter.
         *
         * @param name name of the computer
         * @return instance of the builder
         */
        public ComputerBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * introduced date setter.
         *
         * @param introduced introduced date of the computer
         * @return instance of the builder
         */
        public ComputerBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * discontinued date setter.
         *
         * @param discontinued discontinued date of the computer
         * @return instance of the builder
         */
        public ComputerBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * company setter.
         *
         * @param company company of the computer
         * @return instance of the builder
         */
        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * build the computer.
         *
         * @return instance of the computer created by the builder
         */
        public Computer build() {
            return new Computer(this.id, this.name, this.introduced, this.discontinued, this.company);
        }
    }

    @Override
    public String toString() {

        String intro = this.introduced == null ? "null" : this.introduced.toString();
        String discon = this.discontinued == null ? "null" : this.discontinued.toString();

        return this.id + " " + this.name + " " + intro + " " + discon + " " + (this.company != null ? this.company.toString() : "null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Computer computer = (Computer) o;

        if (id != null ? !id.equals(computer.id) : computer.id != null) return false;
        if (name != null ? !name.equals(computer.name) : computer.name != null) return false;
        if (introduced != null ? !introduced.equals(computer.introduced) : computer.introduced != null) return false;
        if (discontinued != null ? !discontinued.equals(computer.discontinued) : computer.discontinued != null)
            return false;
        return company != null ? company.equals(computer.company) : computer.company == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (introduced != null ? introduced.hashCode() : 0);
        result = 31 * result + (discontinued != null ? discontinued.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }
}
