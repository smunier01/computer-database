package com.excilys.core.conflict.doublon;

public class DoublonRules {

    // list of variables
    private double name;
    private double introduced;
    private double discontinued;
    private double companyName;

    /**
     * Default constructor
     */
    public DoublonRules() {
        this.name = 0.0;
        this.introduced = 0.0;
        this.discontinued = 0.0;
        this.companyName = 0.0;
    }
    public DoublonRules(double name, double introduced, double discontinued, double companyName) {
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyName = companyName;
    }

    // list of setters and getters
    public double getName() {
        return name;
    }
    public void setName(double name) {
        this.name = name;
    }
    public double getIntroduced() {
        return introduced;
    }
    public void setIntroduced(double introduced) {
        this.introduced = introduced;
    }
    public double getDiscontinued() {
        return discontinued;
    }
    public void setDiscontinued(double discontinued) {
        this.discontinued = discontinued;
    }
    public double getCompanyName() {
        return companyName;
    }
    public void setCompanyName(double companyName) {
        this.companyName = companyName;
    }

}
