package test;

import org.junit.Assert;
import org.junit.Test;

import commands.MessageCommandIn;
import commands.QuestionAnswerHandler;


public class TestQuestionAnswerHandler {

	@Test
	public void testQuestionAnswerHandler() {
		QuestionAnswerHandler handler = new QuestionAnswerHandler("/start");
		
		handler.addQuestion("First");
		handler.addQuestion("Second");
		handler.addQuestion("Third");
		while(handler.isQuestionExists()) {
			MessageCommandIn message = new MessageCommandIn("answer",1234, (long) 1234,"TestTest", null);
			handler.addAnswer("answer");
			Assert.assertNotNull(handler.getNewQuestion(message));
		}
		Assert.assertNotNull(handler.getFullCommand());
	}

}
