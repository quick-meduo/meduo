package org.quick.meduo.admin.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quick.meduo.admin.mapper.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Autowired
    ServiceMapper serviceMapper;

    @Test
    public void TestInsert() {
        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setName("监控服务");
        serviceModel.setDescription("监控服务");
        serviceMapper.insert(serviceModel);
    }

    @Test
    public void TestQ() {
        ServiceModel serviceModel = serviceMapper.selectById("ae40e2f1b6fad33fa7af5635bc2f1cbb");
        serviceModel.getFeatureGroupModels();
    }
}
