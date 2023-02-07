package com.baizhi.mall.config.endpoint;

import com.baizhi.mall.config.MyDatabaseConnectionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id="dbcheck")
public class DatabaseConnectionEndpoint {

    @Autowired
    private MyDatabaseConnectionConfig databaseConnectionConfig;

    @ReadOperation
    public Map<String,Object> testDatabaseConnect(){
        HashMap<String, Object> map = new HashMap<>();
        Connection conn = null;
        try {
            Class.forName(databaseConnectionConfig.getDriverClassName());
            conn = DriverManager.getConnection(databaseConnectionConfig.getUrl(),
                    databaseConnectionConfig.getUsername(),
                    databaseConnectionConfig.getPassword());
            map.put("success",true);
            map.put("message","连接成功");
        } catch (Exception e) {
            map.put("success",false);
            map.put("message","连接失败");
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}
