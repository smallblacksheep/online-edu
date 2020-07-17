package com.yy.onlineedu.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.onlineedu.mybatisplus.bean.Product;
import com.yy.onlineedu.mybatisplus.bean.User;
import com.yy.onlineedu.mybatisplus.mapper.ProductMapper;
import com.yy.onlineedu.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductMapper productMapper;

    @Test
    void testSelectList() {
        List<User> users =
                userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testInsert(){
        User user = new User();
        user.setName("巫总");
        user.setEmail("1456789@qq.com");
        user.setAge(18);

        userMapper.insert(user);
    }

    @Test
    public void testBatchSelectByIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4, 5));
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap(){
        Map map = new HashMap<>();
        map.put("age",18);
        map.put("name","汪总");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectPage(){
        Page<User> page = new Page<>(1, 5);
        Page<User> userPage = userMapper.selectPage(page, null);
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
        System.out.println(userPage.getRecords());
        System.out.println(userPage.getSize());
        System.out.println(userPage.hasNext());
        System.out.println(userPage.getTotal());
        System.out.println(userPage.isHitCount());
    }

    @Test
    public void selectPageMaps(){
        Page<Map<String,Object>> page = new Page<>(1,5);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name","age");
        Page<Map<String, Object>> mapPage = userMapper.selectMapsPage(page, queryWrapper);
        List<Map<String, Object>> list = mapPage.getRecords();
        list.forEach(System.out::println);
    }

    @Test
    public void testDeleteById(){
        userMapper.deleteById(1283792971350724609L);
    }

    @Test
    public void testDeleteByIds(){
        userMapper.deleteBatchIds(Arrays.asList(8,9,10));
    }

    @Test
    public void testDeleteByMap(){
        Map map = new HashMap<>();
        map.put("id",11);
        map.put("age",18);
        userMapper.deleteByMap(map);
    }

    @Test
    public void testwapper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",17).select("name","age","email").between("id",2,5).orderByDesc("id").orderByAsc("age");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }





    @Test
    public void testUpdateById(){
        User user = new User();
        user.setName("徐总");
        user.setEmail("351654684848@qq.com");
        user.setAge(19);
        user.setId(1L);
        userMapper.updateById(user);
    }

    @Test
    public void testConcurrentUpdate(){
        //1、小李
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());

        //2、小王
        Product p2 = productMapper.selectById(1L);
        System.out.println("小王取出的价格：" + p2.getPrice());

        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        productMapper.updateById(p1);

        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result = productMapper.updateById(p2);
        if(result == 0){//更新失败，重试
            //重新获取数据
            p2 = productMapper.selectById(1L);
            //更新
            p2.setPrice(p2.getPrice() - 30);
            productMapper.updateById(p2);
        }

        //最后的结果
        Product p3 = productMapper.selectById(1L);
        System.out.println("最后的结果：" + p3.getPrice());
    }

}
