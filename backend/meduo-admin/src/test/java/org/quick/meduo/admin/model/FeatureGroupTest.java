package org.quick.meduo.admin.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quick.meduo.admin.mapper.FeatureGroupMapper;
import org.quick.meduo.admin.mapper.FeatureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeatureGroupTest {
    @Autowired
    FeatureGroupMapper groupMapper;

    @Test
    public void TestAddGroup(){
        FeatureGroupModel model = new FeatureGroupModel();
        model.setDescription("打印功能组");
        model.setName("打印服务功能");
        groupMapper.insert(model);
    }

    @Test
    public void TestQuery() {
       FeatureGroupModel model = groupMapper.selectById("1002");
       List<FeatureModel> list = model.getFeatures();
       for(FeatureModel f: list){
           System.out.println(f.getCode());
       }
    }

    @Test
    public void TestQuery1() {
        List<FeatureGroupModel> list = groupMapper.selectFeatureGroup("101");
        for(FeatureGroupModel f: list){
            System.out.println(f.getFeatures());
        }
    }
}
