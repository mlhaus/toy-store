package edu.kirkwood.toystore.model;

public class Vendor {
    private String vend_id;
    private String vend_name;
    private Address address;

    public Vendor() {
    }

    public Vendor(String vend_id, String vend_name, Address address) {
        this.vend_id = vend_id;
        this.vend_name = vend_name;
        this.address = address;
    }

    public String getVend_id() {
        return vend_id;
    }

    public void setVend_id(String vend_id) {
        // If set, capitalize the vendor ID for consistency
        if (vend_id != null) {
            vend_id = vend_id.toUpperCase();
        }
        // Requires any text
        if(vend_id == null || vend_id.strip().length() == 0) {
            throw new IllegalArgumentException("Vendor ID is required");
        }
        this.vend_id = vend_id;
    }

    public String getVend_name() {
        return vend_name;
    }

    public void setVend_name(String vend_name) {
        // If set, capitalize the first letter for consistency
//        if (vend_name != null) {
//            vend_name = vend_name.substring(0,1).toUpperCase() + vend_name.substring(1);
//        }
        // Requires any text
        if(vend_name == null || vend_name.strip().length() == 0) {
            throw new IllegalArgumentException("Name is required");
        }
        this.vend_name = vend_name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if(address == null) {
            throw new IllegalArgumentException("Vendor Address is required");
        }
        this.address = address;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vend_id='" + vend_id + '\'' +
                ", vend_name='" + vend_name + '\'' +
                ", address=" + address +
                '}';
    }
}
