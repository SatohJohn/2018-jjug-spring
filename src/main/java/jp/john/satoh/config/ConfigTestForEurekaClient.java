package jp.john.satoh.config;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import java.io.IOException;

public class ConfigTestForEurekaClient {

    public static void main(String[] args) throws IOException {
        // eureka clientの作成
        ConfigurationManager.loadPropertiesFromResources("netflix.properties");

        EurekaInstanceConfig eurekaInstanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(eurekaInstanceConfig).get();
        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(eurekaInstanceConfig, instanceInfo);
        DefaultEurekaClientConfig eurekaClientConfig = new DefaultEurekaClientConfig();
        EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, eurekaClientConfig);

        InstanceInfo nextServerInfo = eurekaClient.getNextServerFromEureka("config-test", false);

        // eureka clientから取得したデータを利用してconfigからデータを取得する
        ConfigAccess.access("http://" + nextServerInfo.getHostName() + ":" + nextServerInfo.getPort()  + "/my-id/prod/master");

        eurekaClient.shutdown();
    }
}
