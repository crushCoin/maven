package com.runningfish.spmk.upload.init;
import org.springframework.stereotype.Component;

import com.runningfish.spmk.framework.plugin.AbstractInstallPlugin;



@Component("uploadInitPlugin")
public class InitPlugin extends AbstractInstallPlugin {

    @Override
    public void afterPropertiesSet() throws Exception {
    	installPlugin();
    }
}
