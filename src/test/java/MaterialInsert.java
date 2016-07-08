

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vanke.mhj.dao.BaseDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })

public class MaterialInsert {


    @Resource(name = "baseDao")
    private BaseDao baseDao;
    

    @Value("${weixinPic}")
    private String weixinPic;

    @Test
    public void insert() throws Exception {
    	System.out.println("23423");
    }
}
