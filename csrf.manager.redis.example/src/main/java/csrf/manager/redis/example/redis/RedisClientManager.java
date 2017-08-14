package csrf.manager.redis.example.redis;

import com.lambdaworks.redis.RedisClient;

public class RedisClientManager {

	private static RedisClient redisClient = null;
	
	public static RedisClient getRedisClient() {
		
		if(redisClient == null) {
			redisClient = new RedisClient(RedisConnectionConfiguration.HOST_NAME, RedisConnectionConfiguration.PORT_NO);
		}
		
		return redisClient;
	}
	
}
