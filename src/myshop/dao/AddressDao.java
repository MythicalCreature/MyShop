package myshop.dao;

import myshop.entity.Address;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 20:36
 */
public interface AddressDao {
    List<Address> selectAddressByUid(int uid) throws SQLException;

    void updateAddress(String aid, String name, String phone, String detail) throws SQLException;

    void deleteAddress(String aid) throws SQLException;

    void insertAddress(int uid, String name, String phone, String detail, int state) throws SQLException;

    Address selectDefaultAddressByUid(int uid) throws SQLException;

    void updateAddressStateByAid(int state, int aid) throws SQLException;
}
