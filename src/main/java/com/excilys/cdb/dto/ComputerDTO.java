package com.excilys.cdb.dto;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * ComputerDTO class.
 *
 * @author excilys
 */
public class ComputerDTO {

    private String id;

    private String name;

    private String introduced;

    private String discontinued;

    private String companyId;

    private String companyName;

    public ComputerDTO(final String id, final String name, final String introduced, final String discontinued,
            final String companyId, final String companyName) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * ComputerDTO constructor using a computer as a template.
     *
     * @param computer
     *            computer
     */
    public ComputerDTO(final Computer computer) {
        this.id = computer.getId().toString();
        this.name = computer.getName().toString();
        this.introduced = computer.getIntroduced() == null ? "" : computer.getIntroduced().toString();
        this.discontinued = computer.getDiscontinued() == null ? "" : computer.getDiscontinued().toString();

        final Company company = computer.getCompany();

        if (company != null) {
            this.companyId = company.getId() == null ? "" : company.getId().toString();
            this.companyName = company.getName() == null ? "" : company.getName();
        } else {
            this.companyId = null;
            this.companyName = "";
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

    public String getIntroduced() {
        return this.introduced;
    }

    public void setIntroduced(final String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return this.discontinued;
    }

    public void setDiscontinued(final String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    /**
     * Builder pattern for a ComputerDTO.
     *
     * @author excilys
     */
    public static class Builder {
        private String id = null;
        private String name = null;
        private String introduced = null;
        private String discontinued = null;
        private String companyId = null;
        private String companyName = null;

        public Builder() {

        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder introduced(final String introduced) {
            this.introduced = introduced;
            return this;
        }

        public Builder discontinued(final String discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public Builder companyId(final String companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder companyName(final String companyName) {
            this.companyName = companyName;
            return this;
        }

        public ComputerDTO build() {
            return new ComputerDTO(this.id, this.name, this.introduced, this.discontinued, this.companyId,
                    this.companyName);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.companyId == null) ? 0 : this.companyId.hashCode());
        result = prime * result + ((this.companyName == null) ? 0 : this.companyName.hashCode());
        result = prime * result + ((this.discontinued == null) ? 0 : this.discontinued.hashCode());
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.introduced == null) ? 0 : this.introduced.hashCode());
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
        final ComputerDTO other = (ComputerDTO) obj;
        if (this.companyId == null) {
            if (other.companyId != null) {
                return false;
            }
        } else if (!this.companyId.equals(other.companyId)) {
            return false;
        }
        if (this.companyName == null) {
            if (other.companyName != null) {
                return false;
            }
        } else if (!this.companyName.equals(other.companyName)) {
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
