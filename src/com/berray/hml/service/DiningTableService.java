package com.berray.hml.service;

import com.berray.hml.dao.DiningTableDAO;
import com.berray.hml.domain.DiningTable;

import java.util.List;

public class DiningTableService {
    private DiningTableDAO diningTableDAO = new DiningTableDAO();
    //返回餐桌list
    public List<DiningTable> list(){
        String sql = "select id,state from diningTable";
        return diningTableDAO.queryMulti(sql,DiningTable.class);
    }
    //返回diningtalbe對象,若為null表示id不存在
    public DiningTable getDiningTable(int id){
        String sql = "select * from diningTable where id = ?";
        return diningTableDAO.quertSingle(sql,DiningTable.class,id);
    }
    //如果餐桌可以預訂,更新餐桌資訊
    public boolean orderDiningTable(int id,String orderName,String orderTel){
        String sql = "update diningTable set state = '已經預訂',orderName = ?,orderTel = ? where id = ?";
        int update = diningTableDAO.Update(sql, orderName, orderTel,id);
        return update > 0;
    }
    public boolean setState(int id){
        String sql = "update diningTable set state = '就餐中' where id = ?";
        int update = diningTableDAO.Update(sql,id);
        return update > 0;
    }
    public boolean setStateNull(int id){
        String sql = "update diningTable set state = '空',orderName = '',orderTel = '' where id = ?";
        int update = diningTableDAO.Update(sql,id);
        return update > 0;
    }

}
