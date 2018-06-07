package bp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * IdeaTest
 */
public class IdeaTest {
  Idea idea;
  User user1, user2;

  @Before
  public void setUp() {
    user1 = new User("fulano");
    user2 = new User("cicrano");
    this.idea = new Idea(user1, "An idea");
  }

  @Test
  public void countVotes() {
    assertTrue(idea.countVotes() == 0);
    idea.registerVote(user1);
    assertTrue(idea.countVotes() == 1);
    idea.registerVote(user1);
    assertTrue(idea.countVotes() == 1);
    idea.registerVote(user2);
    assertTrue(idea.countVotes() == 2);
    idea.reclaimVote(user1);
    idea.reclaimVote(user2);
    assertTrue(idea.countVotes() == 0);
  }

  @Test
  public void shouldConsiderVote() {
    idea.registerVote(user1);
    assertTrue(idea.voters.contains(user1));
    idea.reclaimVote(user1);
    assertFalse(idea.voters.contains(user1));
  }
  
}