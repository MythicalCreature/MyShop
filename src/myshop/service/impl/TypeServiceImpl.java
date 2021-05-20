package myshop.service.impl;

import myshop.dao.TypeDao;
import myshop.dao.impl.TypeDaoImpl;
import myshop.entity.Orders;
import myshop.entity.Page;
import myshop.entity.Type;
import myshop.service.TypeService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zzx
 * 2020/12/6 15:37
 */
public class TypeServiceImpl implements TypeService {
    private TypeDao typeDao = new TypeDaoImpl();
    @Override
    public List<Type> findAll() throws SQLException {
        return typeDao.selectAll();
    }

    @Override
    public Page<Type> findPage(int currentPage, int pageSize) throws SQLException {
        long count = typeDao.selectCount();
        List<Type>  typeList = typeDao.selectTypeByPage(currentPage,pageSize);
        return new Page<Type>(typeList,currentPage,pageSize,count);
    }

    @Override
    public void addType(String typename,String typeInfo) throws SQLException {
        typeDao.insertType(typename,typeInfo);
    }
}
