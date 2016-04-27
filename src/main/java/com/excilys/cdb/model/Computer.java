package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

    private Long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * default constructor for a computer.
     */
    public Computer() {
        this.id = 0L;
        this.name = "";
        this.introduced = null;
        this.discontinued = null;
        this.company = new Company();
    }

    /**
     * constructor for a computer.
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
    public Computer(final Long id, final String name, final LocalDate introduced, final LocalDate discontinued,
            final Company company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    /**
     * Builder pattern for a computer.
     *
     * @author excilys
     */
    public static class ComputerBuilder {
        private Long id = 0L;
        private String name = "";
        private LocalDate introduced = null;
        private LocalDate discontinued = null;
        private Company company = new Company();

        /**
         * default constructor for the builder.
         */
        public ComputerBuilder() {

        }

        /**
         * id setter.
         *
         * @param id
         *            id of the computer
         * @return instance of the builder
         */
        public ComputerBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * name setter.
         *
         * @param name
         *            name of the computer
         * @return instance of the builder
         */
        public ComputerBuilder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * introduced date setter.
         *
         * @param introduced
         *            introduced date of the computer
         * @return instance of the builder
         */
        public ComputerBuilder introduced(final LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * discontinued date setter.
         *
         * @param discontinued
         *            discontinued date of the computer
         * @return instance of the builder
         */
        public ComputerBuilder discontinued(final LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * company setter.
         *
         * @param company
         *            company of the computer
         * @return instance of the builder
         */
        public ComputerBuilder company(final Company company) {
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

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public LocalDate getIntroduced() {
        return this.introduced;
    }

    public void setIntroduced(final LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return this.discontinued;
    }

    public void setDiscontinued(final LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }

    @Override
    public String toString() {

        final String intro = this.introduced == null ? "null" : this.introduced.toString();
        final String discon = this.discontinued == null ? "null" : this.discontinued.toString();

        return this.id + " " + this.name + " " + intro + " " + discon + " " + this.company.toString();
    }

    /**
     * auto-generated hashCode.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.company == null) ? 0 : this.company.hashCode());
        result = (prime * result) + ((this.discontinued == null) ? 0 : this.discontinued.hashCode());
        result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
        result = (prime * result) + ((this.introduced == null) ? 0 : this.introduced.hashCode());
        result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    /**
     * auto-generated equals.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Computer other = (Computer) obj;
        if (this.company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!this.company.equals(other.company)) {
            return false;
        }
        if (this.discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!this.discontinued.equals(other.discontinued)) {
            return false;
        }
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!this.introduced.equals(other.introduced)) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
