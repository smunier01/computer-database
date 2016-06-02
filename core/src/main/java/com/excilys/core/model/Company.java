package com.excilys.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Company Class.
 *
 * @author simon
 */
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue
    private Long id;

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
    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
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
        int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
        result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    /**
     * auto-generated equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Company other = (Company) obj;
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
