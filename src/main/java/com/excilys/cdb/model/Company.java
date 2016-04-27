package com.excilys.cdb.model;

/**
 * Company Class.
 *
 * @author excilys
 */
public class Company {

    /**
     * id of the company.
     */
    private Long id;

    /**
     * name of the company.
     */
    private String name;

    /**
     * company default constructor.
     */
    public Company() {
        this.id = null;
        this.name = "";
    }

    /**
     * company constructor.
     *
     * @param id
     *            id of the company
     * @param name
     *            name of the company
     */
    public Company(final Long id, final String name) {
        this.id = id;
        this.name = name == null ? "" : name;
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

    /**
     *
     */
    @Override
    public String toString() {
        return this.id + " " + this.name;
    }

    /**
     * auto-generated hashCode.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
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
        final Company other = (Company) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
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
