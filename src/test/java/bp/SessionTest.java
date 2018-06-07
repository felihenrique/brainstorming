package bp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * SessionTest
 */
public class SessionTest {
  User[] users = {
    new User("Fulano"),
    new User("Cicrano"),
    new User("Beltrano")
  };
  Session session = new Session(users[0], "Brainstorm dos melhores");

  // @Before
  // public void setUp() {
  //   User u = new User("Fulano");
  //   this.session = new Session(u);    
  // }

  /**
   * Na criação de um objeto
   */
  @Test
  public void creation() {
  }

  @Test
  public void nextPhase() {
  }

  @Test
  public void addParticipant() {
  }
}