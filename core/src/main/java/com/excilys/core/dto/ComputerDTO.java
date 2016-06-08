package com.excilys.core.dto;

import com.excilys.core.model.Company;
import com.excilys.core.model.Computer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;


/**
 * DTO for the Computer Class.
 *
 * @author simon
 */
public class ComputerDTO {

    private String id;

    @NotNull
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String introduced;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String discontinued;

    private String companyId;

    private String companyName;

    public ComputerDTO() {

    }

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
     * @param computer computer
     */
    public ComputerDTO(final Computer computer) {
        this.id = computer.getId().toString();
        this.name = computer.getName();
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

        private ComputerDTO dto;

        public Builder() {
            dto = new ComputerDTO();
        }

        public Builder id(final String id) {
            dto.id = id;
            return this;
        }

        public Builder name(final String name) {
            dto.name = name;
            return this;
        }

        public Builder introduced(final String introduced) {
            dto.introduced = introduced;
            return this;
        }

        public Builder discontinued(final String discontinued) {
            dto.discontinued = discontinued;
            return this;
        }

        public Builder companyId(final String companyId) {
            dto.companyId = companyId;
            return this;
        }

        public Builder companyName(final String companyName) {
            dto.companyName = companyName;
            return this;
        }

        public ComputerDTO build() {
            return dto;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComputerDTO that = (ComputerDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (introduced != null ? !introduced.equals(that.introduced) : that.introduced != null) return false;
        if (discontinued != null ? !discontinued.equals(that.discontinued) : that.discontinued != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        return companyName != null ? companyName.equals(that.companyName) : that.companyName == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (introduced != null ? introduced.hashCode() : 0);
        result = 31 * result + (discontinued != null ? discontinued.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ComputerDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", introduced='" + introduced + '\'' +
                ", discontinued='" + discontinued + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
