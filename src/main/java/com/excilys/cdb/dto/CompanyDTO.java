package com.excilys.cdb.dto;

import com.excilys.cdb.model.Company;

/**
 * CompanyDTO class.
 *
 * @author excilys
 */
public class CompanyDTO {
    private String id;
    private String name;

    /**
     * CompanyDTO constructor using a company as a template.
     *
     * @param company
     *            company
     */
    public CompanyDTO(final Company company) {
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

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

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
        final CompanyDTO other = (CompanyDTO) obj;
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
