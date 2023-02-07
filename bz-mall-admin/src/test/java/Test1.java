import com.baizhi.mall.AdminApplication;
import com.baizhi.mall.mapper.SmsHomeAdvertiseMapper;
import com.baizhi.mall.entity.SmsHomeAdvertise;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class Test1 {

    @Autowired
    private SmsHomeAdvertiseMapper mapper;

    @Test
    public void ma(){
        QueryWrapper wrapper = new QueryWrapper<Object>();
        Map<String, Object> map = new HashMap<>();
        map.put("type",1);
//        map.put("endTime",null);
        map.put("name",1);
        List<SmsHomeAdvertise> smsHomeAdvertises = mapper.selectByMap(map);
        smsHomeAdvertises.forEach(s -> System.out.println(s));
    }

}
