package csrf.manager.redis.example.redis;

import java.io.IOException;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.protocol.SetArgs;

import csrf.manager.redis.example.csrf.CsrfTokenHolder;
import csrf.manager.redis.example.serialize.ObjectSerializeManager;

public class RedisDataManager {

	private final RedisClient redisClient;

	
	public RedisDataManager(RedisClient redisClient) {
		
		this.redisClient = redisClient;
	}
	
	public boolean put(String sessionId, CsrfTokenHolder csrfTokenHolder) {
		
		boolean recordSaveStatus = false;
		
		RedisConnection<String, String> redisConnection = openRedisConnection();
		
		if(redisConnection == null) {
			throw new RuntimeException("Redis connection is not alive!");
		}
		
		try {
			
			String csrfHolderAsText = ObjectSerializeManager.serialize(csrfTokenHolder);
			
			// Set timeout for key-value pair. 15 seconds!
			SetArgs setArgs = new SetArgs().ex(60);
			
			redisConnection.set(sessionId, csrfHolderAsText, setArgs);
			
			recordSaveStatus = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			redisConnection.close();
		}
		
		return recordSaveStatus;
	}
	
	public CsrfTokenHolder get(String sessionId) {
		
		RedisConnection<String, String> redisConnection = openRedisConnection();
		
		if(redisConnection == null) {
			throw new RuntimeException("Redis connection is not alive!");
		}
		
		
		CsrfTokenHolder csrfTokenHolder = new CsrfTokenHolder();
		
		try {
			
			String csrfHolderAsText = redisConnection.get(sessionId);
			
			if(csrfHolderAsText != null) {
				csrfTokenHolder = ObjectSerializeManager.deserialize(csrfHolderAsText);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			redisConnection.close();
		}
		
		return csrfTokenHolder;
	}
	
	
	private RedisConnection<String, String> openRedisConnection() {
		
		if(this.redisClient == null) {
			return null;
		}
		
		return this.redisClient.connect();
	}
	
}
