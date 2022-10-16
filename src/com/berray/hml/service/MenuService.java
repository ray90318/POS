package com.berray.hml.service;

import com.berray.hml.dao.MenuDAO;
import com.berray.hml.domain.Menu;

import java.util.List;

public class MenuService {
    MenuDAO menuDAO = new MenuDAO();
    //顯示菜單
    public List<Menu> getMenuList(){
        String sql = "select * from menu";
        return menuDAO.queryMulti(sql,Menu.class);
    }
    public Menu getMenu(int menuId){
        String sql = "select * from menu where id = ?";
         return menuDAO.quertSingle(sql,Menu.class,menuId);
    }
}
