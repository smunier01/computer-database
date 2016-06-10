package com.excilys.core.dto;

import com.excilys.core.model.Company;

/**
 * DTO for the Company Class.
 *
 * @author simon
 */
public class CompanyDTO {

    private String id;

    private String name;

    /**
     * Default constructor.
     */
    public CompanyDTO() {
    }

    /**
     * CompanyDTO constructor using a company as a template.
     *
     * @param company company
     */
    public CompanyDTO(Company company) {
        if (company != null) {
            this.id = company.getId() == null ? "" : company.getId().toString();
            this.name = company.getName();
        } else {
            this.id = "";
            this.name = "";
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

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
        CompanyDTO other = (CompanyDTO) obj;
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

    @Override
    public String toString() {
        return "CompanyDTO [id=" + this.id + ", name=" + this.name + "]";
    }

}
