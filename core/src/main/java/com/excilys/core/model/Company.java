package com.excilys.core.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.SortableField;

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

    // list of the variables
    @Id
    @GeneratedValue
    private Long id;

    @SortableField
    @Field
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
     * @param id   id of the company
     * @param name name of the company
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (id != null ? !id.equals(company.id) : company.id != null) {
            return false;
        }
        return name != null ? name.equals(company.name) : company.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}