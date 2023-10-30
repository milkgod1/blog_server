package util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

import javax.annotation.PostConstruct;

public class SnowflakeUtil {
    private long workId;
    private final long datacenterId = 1;
    private final Snowflake snowflake = IdUtil.getSnowflake(workId, datacenterId);

    @PostConstruct
    private void init() {
        workId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
    }

    public synchronized Long generateId() {
        return snowflake.nextId();
    }
}
