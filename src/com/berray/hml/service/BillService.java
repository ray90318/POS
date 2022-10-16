package com.berray.hml.service;

import com.berray.hml.dao.BasicDAO;
import com.berray.hml.dao.BillDAO;
import com.berray.hml.domain.Bill;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BillService {
    BillDAO billDAO = new BillDAO();
    public int orderBill(int menuID, int nums,double money ,int diningTableId){
        String billId = UUID.randomUUID().toString();
        String sql = "insert into bill values(null,?,?,?,?,?,now(),'未付款')";
        int update = billDAO.Update(sql, billId,menuID, nums, money, diningTableId);
        return update;
    }
    public List<Bill> getBillList(int diningTableId){
        String sql = "select * from bill where diningTableId = ? and state = '未付款'";
        return billDAO.queryMulti(sql,Bill.class,diningTableId);
    }
    public double getBilltotal(int diningTableId){
        String sql = "select SUM(money) from bill where diningTableId = ? and state = '未付款'";
        double querycomm = (double)billDAO.querycomm(sql,diningTableId);
        return querycomm;
    }
    public Bill getBillSingle(int diningTableId){
        String sql = "select * from bill where diningTableId = ?";
        return billDAO.quertSingle(sql,Bill.class,diningTableId);
    }
    public boolean setBillState(String state,int diningTableId){
        String sql = "update bill set state = ? where diningTableId = ?";
        int update = billDAO.Update(sql,state ,diningTableId);
        return update > 0;
    }
}
