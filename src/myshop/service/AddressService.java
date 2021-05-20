package myshop.service;

import myshop.entity.Address;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 20:27
 */
public interface AddressService {
    List<Address> findAddressByUid(int uid) throws SQLException;


    void updateAddress(String name, String phone, String detail,String aid) throws SQLException;

    void deleteAddress(String aid) throws SQLException;

    void addAddress(String name, String phone, String detail, int state, int uid) throws SQLException;

    void updateAddressStateByAid(int state, int aid) throws SQLException;

    Address findDefaultAddress(int uid) throws SQLException;
}
