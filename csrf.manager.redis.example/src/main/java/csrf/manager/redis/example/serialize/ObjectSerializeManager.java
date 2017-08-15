package csrf.manager.redis.example.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class ObjectSerializeManager {

	public static <T extends Serializable> String serialize(T object) throws Exception {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();

		String serializedObjectAsText = new String(Base64.encode(byteArrayOutputStream.toByteArray()));
		
		return serializedObjectAsText;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(String serializedObjectAsText) throws ClassNotFoundException, IOException {
		
		byte[] bytes = org.springframework.security.crypto.codec.Base64.decode(serializedObjectAsText.getBytes());
		
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
	    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
	   
		T object = (T) objectInputStream.readObject();
		
	    return object;
	}

}
