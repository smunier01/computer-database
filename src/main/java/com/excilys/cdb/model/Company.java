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
        id = null;
        name = "";
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
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     */
    @Override
    public String toString() {
        return id + " " + name;
    }

    /**
     * auto-generated hashCode.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
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
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
