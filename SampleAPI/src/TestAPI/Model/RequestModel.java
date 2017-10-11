package TestAPI.Model;

public class RequestModel {
	private String query;
	private String lang;
	private String sessionId;
	
	public void setQuery(String queryText)
	{
		query = queryText;
	}
	
	public String getQuery()
	{
		return query;
	}
	
	public void setLang(String language)
	{
		lang = language;
	}
	
	public String getLang()
	{
		return lang;
	}
	
	public void setSessionId(String sessionid)
	{
		sessionId = sessionid;
	}
	
	public String getSessionId()
	{
		return sessionId;
	}
}
