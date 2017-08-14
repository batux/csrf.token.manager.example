package csrf.manager.redis.example.test;

import org.junit.Assert;
import org.junit.Test;

import com.lambdaworks.redis.RedisAsyncConnectionImpl;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;

import csrf.manager.redis.example.redis.RedisConnectionConfiguration;

public class RedisConnectionTest {

	@Test
	public void test() {
		
		RedisClient redisClient = new RedisClient(RedisConnectionConfiguration.HOST_NAME, RedisConnectionConfiguration.PORT_NO);
//		RedisAsyncConnectionImpl<K, V>
		RedisConnection<String, String> connection  = redisClient.connect();
		
		Assert.assertEquals("PONG", connection.ping());
	}

}
