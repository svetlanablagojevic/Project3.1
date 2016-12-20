package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
/**
 * Created by GoranB on 2016-12-12.
 */
public class UserTest {

    private Board board;
    private User questioner;
    private User answerer;
    private User third;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        questioner= board.createUser("questioner");
        answerer= board.createUser("answerer");
        third= board.createUser("third");
    }


    //Write a test to ensure that the questioner’s reputation goes up by 5 points if their question is upvoted.
    @Test
    public void questionersReputationGoesUpBy5WhenTheirQuestionIsUpVoted() throws Exception {

        int initialQuestionerReputation = questioner.getReputation();

        Question question = questioner.askQuestion("What is a String?");
        third.upVote(question);

        assertEquals(initialQuestionerReputation + 5, questioner.getReputation());
    }
    //Write a test to assert that the answerer’s reputation goes up by 10 points if their answer is upvoted.
    @Test
    public void answererReputationGoesUpBy10WhenTheirQuestionIsUpVoted() throws Exception {

        int initialAnswererReputation =answerer.getReputation();

        Question question = questioner.askQuestion("What is a String?");
        Answer answer = answerer.answerQuestion(question, "It is a series of characters, strung together...");
        questioner.upVote(answer);

        assertEquals(initialAnswererReputation + 10, answerer.getReputation());
    }


    //Write a test that proves that having an answer accepted gives the answerer a 15 point reputation boost
    @Test
    public void answererReputationGoesUpBy15WhenTheirAnswerIsAccepted() throws Exception {

        int initialAnswererReputation =answerer.getReputation();

        Question question = questioner.askQuestion("What is a String?");
        Answer answer = answerer.answerQuestion(question, "It is a series of characters, strung together...");
         questioner.acceptAnswer(answer);

        assertEquals(initialAnswererReputation + 15, answerer.getReputation());

    }

    //Using a test, ensure that voting either up or down is not allowed on questions or answers by the original author, you know to avoid gaming the system.
    // Ensure the proper exceptions are being thrown.
    @Test
    public void whenQuestionerUpvotesForHimselfExceptionIsThrown() throws Exception {

        Question question = questioner.askQuestion("What is a String?");
        Answer answer = answerer.answerQuestion(question, "It is a series of characters, strung together...");
        expectedException.expect(VotingException.class);
        questioner.upVote(question);
    }
    // down voting
    @Test
    public void whenQuestionerDownvotesForHimselfExceptionIsThrown() throws Exception {

        Question question = questioner.askQuestion("What is a String?");
        Answer answer = answerer.answerQuestion(question, "It is a series of characters, strung together...");
        expectedException.expect(VotingException.class);
        questioner.downVote(question);
    }
    
    //Write a test to make sure that only the original questioner can accept an answer.

    @Test
    public void questionerAcceptingAnswerToHisQuestionSetsAnswerIsAccepted()
            throws Exception {

        Question question = questioner.askQuestion("question");
        Answer answer = answerer.answerQuestion(question,"answer");
        questioner.acceptAnswer(answer);

        assertTrue(answer.isAccepted());
    }


    @Test
    public void whenAnswererTriesToAcceptAnswerExceptionIsThrown()
            throws Exception {

        Question question = questioner.askQuestion("What is a String?");
        Answer answer = answerer.answerQuestion(question,"It is a series of characters, strung together...");
        expectedException.expect(AnswerAcceptanceException.class);
        answerer.acceptAnswer(answer);
    }

    //Reviewing the User.getReputation method may expose some code that is not requested to be tested in the Meets Project instructions.
    // Write the missing test.
    // Down-voting costs 1 point

    @Test
    public void answererReputationGoesDownBy1WhenTheirQuestionIsDownVoted() throws Exception {

        int initialAnswererReputation =answerer.getReputation();

        Question question = questioner.askQuestion("What is a String?");
        Answer answer = answerer.answerQuestion(question, "It is a series of characters, strung together...");
        questioner.downVote(answer);

        assertEquals(initialAnswererReputation - 1, answerer.getReputation());
    }
    //currently down-voting of questions affects nothing.
    @Test
    public void downvotingOfQuestionsAffectsNothing() throws Exception {

        int initialAnswererReputation =answerer.getReputation();

        Question question = questioner.askQuestion("What is a String?");
        third.downVote(question);

        assertEquals(initialAnswererReputation , questioner.getReputation());
    }

}