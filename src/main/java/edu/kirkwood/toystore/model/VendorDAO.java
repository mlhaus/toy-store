package edu.kirkwood.toystore.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static edu.kirkwood.shared.MySQL_Connect.getConnection;

public class VendorDAO {
    public static void main(String[] args) {
        Vendor vendor = new Vendor("1", "Test Vendor", new Address("123 Sample St", "Cedar Rapids", "IA", "55555", "USA"));
        addVendor(vendor);
    }

    // Assignment 9 method to get a List<Vendor>
    public static List<Vendor> getVendors() {
        // Instantiate a List<Vendor>
        // Connection
        // CallableStatement
        // ResultSet
        // Loop through resultSet, instantiating objects
        return null;
    }

    public static boolean addVendor(Vendor vendor) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_add_vendor_admin(?, ?, ?, ?, ?, ?, ?)}");
            statement.setString(1, vendor.getVend_id());
            statement.setString(2, vendor.getVend_name());
            statement.setString(3, vendor.getAddress().getAddress());
            statement.setString(4, vendor.getAddress().getCity());
            statement.setString(5, vendor.getAddress().getState());
            statement.setString(6, vendor.getAddress().getZip());
            statement.setString(7, vendor.getAddress().getCountry());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch(SQLException e) {
//            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateVendor(Vendor vendorOriginal, Vendor vendorNew) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_update_vendor_admin(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setString(1, vendorOriginal.getVend_id());
            statement.setString(2, vendorOriginal.getVend_name());
            statement.setString(3, vendorOriginal.getAddress().getAddress());
            statement.setString(4, vendorOriginal.getAddress().getCity());
            statement.setString(5, vendorOriginal.getAddress().getState());
            statement.setString(6, vendorOriginal.getAddress().getZip());
            statement.setString(7, vendorOriginal.getAddress().getCountry());
            statement.setString(8, vendorNew.getVend_id());
            statement.setString(9, vendorNew.getVend_name());
            statement.setString(10, vendorNew.getAddress().getAddress());
            statement.setString(11, vendorNew.getAddress().getCity());
            statement.setString(12, vendorNew.getAddress().getState());
            statement.setString(13, vendorNew.getAddress().getZip());
            statement.setString(14, vendorNew.getAddress().getCountry());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch(SQLException e) {
//            System.out.println(e.getMessage());
            return false;
        }
    }



    public static Vendor getVendor(String vend_id) {
        Vendor vendor = null;
        if(vend_id != null) {
            vend_id = vend_id.trim();
            try (Connection connection = getConnection()) {
                CallableStatement statement = connection.prepareCall("{CALL sp_get_vendor_by_id(?)}");
                statement.setString(1, vend_id);
                ResultSet resultSet = statement.executeQuery();
                // Use an if statement instead of a while loop when the SELECT query returns one record
                if (resultSet.next()) {
                    String vend_name = resultSet.getString("vend_name");
                    String vend_address = resultSet.getString("vend_address");
                    String vend_city = resultSet.getString("vend_city");
                    String vend_state = resultSet.getString("vend_state");
                    String vend_zip = resultSet.getString("vend_zip");
                    String vend_country = resultSet.getString("vend_country");
                    vendor = new Vendor(vend_id, vend_name, new Address(vend_address, vend_city, vend_state, vend_zip, vend_country));
                }
            } catch (SQLException e) {
//            System.out.println(e.getMessage()); // Uncomment in case null is always being returned
            }
        }
        return vendor;
    }
}
