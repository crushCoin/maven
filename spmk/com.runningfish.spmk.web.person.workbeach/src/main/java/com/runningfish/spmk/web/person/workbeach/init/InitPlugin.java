package com.runningfish.spmk.web.person.workbeach.init;
import org.springframework.stereotype.Component;

import com.runningfish.spmk.framework.plugin.AbstractInstallPlugin;



@Component("psworkInitPlugin")
public class InitPlugin extends AbstractInstallPlugin {

    @Override
    public void afterPropertiesSet() throws Exception {
    	installPlugin();
    }
}
