package com.berray.hml.utils;

import java.sql.Connection;
import java.sql.SQLException;

//測試工具類
public class testutils {
    public static void main(String[] args) throws SQLException {
        System.out.println("輸入");
        String str = Utility.readString();
        System.out.println(str);
        Connection connection = JDBCUtilsByDruid.getConnection();
        System.out.println(connection);

    }
}
