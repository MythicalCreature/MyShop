package myshop.service.impl;

import myshop.dao.AddressDao;
import myshop.dao.impl.AddressDaoImpl;
import myshop.entity.Address;
import myshop.service.AddressService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 20:27
 */
public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao = new AddressDaoImpl();
    @Override
    public List<Address> findAddressByUid(int uid) throws SQLException {
        return addressDao.selectAddressByUid(uid);
    }

    @Override
    public void updateAddress(String name, String phone, String detail, String aid) throws SQLException {
        addressDao.updateAddress(aid,name,phone,detail);
    }

    @Override
    public void deleteAddress(String aid) throws SQLException {
        addressDao.deleteAddress(aid);
    }

    @Override
    public void addAddress(String name, String phone, String detail, int state, int uid) throws SQLException {
        addressDao.insertAddress(uid,name,phone,detail,state);
    }

    @Override
    public void updateAddressStateByAid(int state, int aid) throws SQLException {
        addressDao.updateAddressStateByAid(state,aid);
    }

    @Override
    public Address findDefaultAddress(int uid) throws SQLException {
        return addressDao.selectDefaultAddressByUid(uid);
    }
}
