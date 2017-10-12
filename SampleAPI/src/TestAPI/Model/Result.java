package TestAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result {
	public String source;
	public String resolvedQuery;
	public String speech;
	public String action;
	
	public Parameters parameters;
	public Metadata metadata;
	public String score;
	
	public class Parameters
	{
		public String date;
		public String recurrence;
		public String time;
	}
	
	public class Metadata
	{
		public String[] inputContexts;
		public String[] outputContexts;
		public String intentName;
		public String intentId;
		public String webhookUsed;
		public String webhookForSlotFillingUsed;
		public String[] contexts;
	}
}
