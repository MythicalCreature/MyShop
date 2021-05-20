package myshop.dao.impl;

import myshop.dao.AddressDao;
import myshop.entity.Address;
import myshop.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 20:36
 */
public class AddressDaoImpl implements AddressDao {
    private QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public List<Address> selectAddressByUid(int uid) throws SQLException {
        String sql = "select a_id as aid, u_id as uid, a_name as name,a_phone as phone, " +
                "a_detail as detail, a_state as astate from address where u_id=? order by a_state desc";
        return queryRunner.query(sql,new BeanListHandler<Address>(Address.class),uid);
    }

    @Override
    public void updateAddress(String aid, String name, String phone, String detail) throws SQLException {
        String sql = "update address set a_name=?,a_phone=?,a_detail=? where a_id=?";
        queryRunner.update(sql,name,phone,detail,aid);
    }

    @Override
    public void deleteAddress(String aid) throws SQLException {
        String sql = "delete from address where a_id=?";
        queryRunner.update(sql,aid);
    }

    @Override
    public void insertAddress(int uid, String name, String phone, String detail, int state) throws SQLException {
        String sql = "insert into address (u_id,a_name,a_phone,a_detail,a_state) values (?,?,?,?,?)";
        queryRunner.update(sql,uid,name,phone,detail,state);
    }

    @Override
    public Address selectDefaultAddressByUid(int uid) throws SQLException {
        String sql = "select a_id as aid, u_id as uid, a_name as name,a_phone as phone, " +
                "a_detail as detail, a_state as astate from address where u_id=? and a_state=1";
        return queryRunner.query(sql,new BeanHandler<Address>(Address.class),uid);
    }

    @Override
    public void updateAddressStateByAid(int state, int aid) throws SQLException {
        String sql = "update address set a_state=? where a_id=?";
        queryRunner.update(sql,state,aid);
    }


}
