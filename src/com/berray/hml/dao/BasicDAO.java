package com.berray.hml.dao;

import com.berray.hml.utils.JDBCUtilsByDruid;
import com.mysql.cj.jdbc.JdbcConnection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
/*
    dao工具包
 */
public class BasicDAO<T> {
    QueryRunner queryRunner = new QueryRunner();
    public int Update(String sql,Object... parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            int update = queryRunner.update(connection, sql, parameters);
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    public List<T> queryMulti(String sql,Class<T> clazz,Object... parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            List<T> list =
                    queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    public T quertSingle(String sql,Class<T> clazz,Object...parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }

    public Object querycomm(String sql,Object...parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return queryRunner.query(connection,sql,new ScalarHandler<T>(),parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(null,null,connection);
        }
    }
}
