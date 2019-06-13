package com.eigaas.imf;

import com.eigaas.imf.domain.Mission;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MissionTest {

    @Test
    public void testMission(){
        Mission v = Mission.builder().codename("test").build();
        v.setId(1L);
        assertTrue("id is 1L", 1L == v.getId());
        assertTrue("name is test", "test".equals(v.getCodename()));

        Mission v2 =  Mission.builder().codename("test2").build();
        v2.setId(2L);
        assertTrue("id is 2L", 2L == v2.getId());
        assertTrue("name is test2", "test2".equals(v2.getCodename()));
    }
}
