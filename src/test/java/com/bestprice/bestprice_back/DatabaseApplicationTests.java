package com.bestprice.bestprice_back;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseApplicationTests {
    @Autowired
    private SqlSessionFactory factory ;

    @Test
    public void testByFactory() {
        try{
            System.out.println(">>>>>>>>>>>>>>>>>>>>factory");
            System.out.println( factory.toString());
            System.out.println(">>>>>>>>>>>>>>>>>>>>");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
