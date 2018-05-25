package jp.john.satoh.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * eurekaから取得するデータ.
 * 取得できるデータは eureka/apps/{applicationId} のもの
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class NextServerInfo {
    @JsonProperty("app")
    public String app;
    @JsonProperty("instanceId")
    public String instanceId;
    @JsonProperty("hostName")
    public String hostName;
    @JsonProperty("ipAddr")
    public String ipAddress;
    @JsonProperty("port")
    public Port port;
    @JsonProperty("securePort")
    public Port securePort;

    public static class Port {
        @JsonProperty("$")
        public int value;
        @JsonProperty("@enabled")
        public String enabled;
    }

    /**
     * eurekaから取得できた情報からurlを作る
     * @return
     */
    public String buildNextServerHostUrl() {
        return "http://" + this.hostName + ":" + this.port.value + "/";
    }
}
