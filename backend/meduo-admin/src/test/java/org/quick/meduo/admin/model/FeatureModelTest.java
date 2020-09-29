package org.quick.meduo.admin.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quick.meduo.admin.mapper.FeatureMapper;
import org.quick.meduo.admin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeatureModelTest {
    @Autowired
    FeatureMapper featureMapper;

    @Test
    public void TestAddFeature(){
        FeatureModel model = new FeatureModel();
        model.setCode("sys:printer:allow");
        model.setGid("1002");
        model.setName("远程打印02");
        model.setDescription("功能测试验证");
        featureMapper.insert(model);
    }

    @Test
    public void TestQ(){
        List<FeatureModel> list = featureMapper.selectFeatures("1002");
        System.out.println(list);
    }
}
