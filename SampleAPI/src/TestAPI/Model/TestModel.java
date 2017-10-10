package TestAPI.Model;

public class TestModel {
	private String messageText;

	public TestModel()
	{
		
	}
	
	public void setMessageText(String message)
	{
		messageText = message;
	}
	
	public String getMessageText()
	{
		return messageText;
	}
	
	@Override
	public String toString()
	{
		return new StringBuffer(" Message : ").append(messageText).toString();
	}
}
