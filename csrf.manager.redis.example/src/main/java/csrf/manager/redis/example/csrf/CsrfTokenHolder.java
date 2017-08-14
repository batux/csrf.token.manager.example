package csrf.manager.redis.example.csrf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CsrfTokenHolder implements Serializable {

	private static final long serialVersionUID = 5523693312672525226L;

	private List<String> csrfTokenList;
	
	
	public CsrfTokenHolder() {
		this.csrfTokenList = new ArrayList<String>();
	}


	public List<String> getCsrfTokenList() {
		return csrfTokenList;
	}

	public void setCsrfTokenList(List<String> csrfTokenList) {
		this.csrfTokenList = csrfTokenList;
	}
	
}
