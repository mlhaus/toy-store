package edu.kirkwood.toystore.model;

import edu.kirkwood.shared.Validators;

public class Address {
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;

    public Address() {
    }

    public Address(String address, String city, String state, String zip, String country) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        // Requires any text
        if(address == null || address.strip().length() == 0) {
            throw new IllegalArgumentException("Street address is required");
        }
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        // Requires any text
        if(city == null || city.strip().length() == 0) {
            throw new IllegalArgumentException("City is required");
        }
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        // If set, capitalize the state for consistency
        if (state != null) {
            state = state.toUpperCase();
        }
        // Requires valid state only if the country is the United States
        if(isUnitedStates()) {
            if (state == null || !Validators.isValidState(state)) {
                throw new IllegalArgumentException("State is not valid");
            }
        }
        this.state = state;
    }

    public boolean isUnitedStates() {
        return country != null && (country.equals("US") || country.equals("USA"));
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        // Requires valid zip
        if(zip == null || !Validators.isValidZip(zip)) {
            throw new IllegalArgumentException("Zip is not valid");
        }
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        // If set, capitalize the country for consistency
        if (country != null) {
            country = country.toUpperCase();
        }
        // Requires valid country
        if(country == null || !Validators.isValidCountry(country)) {
            throw new IllegalArgumentException("Country is not valid");
        }
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
