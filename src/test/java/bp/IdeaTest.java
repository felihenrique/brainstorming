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
  Idea idea, idea2;
  User user1, user2, user3;
  Session session1;

  @Before
  public void setUp() {
    user1 = new User("fulano");
    user2 = new User("cicrano");
    user3 = new User("beltrano");

    session1 = new Session(user1, "Uma sessão");
    session1.addParticipant(user1);
    session1.addParticipant(user2);

    idea = new Idea(user1, "An idea");
    idea2 = new Idea(user2, "Idea 2");

    session1.nextPhase();

    session1.addIdea(idea);
    session1.addIdea(idea2);

    // Autor deve ser o mesmo passado na criação
    assertTrue(idea.author == user1);
    // A lista de votantes da ideia deve estar vazia
    assertTrue(idea.voters.isEmpty());

    // Os votos devem permanacer inalterados se não estiver na fase de votação
    idea.registerVote(user2);
    assertTrue(idea.voters.isEmpty());

    session1.nextPhase();

    // Um usuário não participante da sessão não pode votar
    idea.registerVote(user3);
    assertFalse(idea.voters.contains(user3));

    // Usuario dando voto
    idea.registerVote(user2);
    assertEquals(idea.voters.size(), 1);
    assertTrue(user2.votes == 1);

    // Usuario não pode votar mais que o limite (1)
    idea2.registerVote(user2);
    assertTrue(user2.votes == 1);
    assertFalse(idea2.voters.contains(user2));
  }

  /**
   * Esse caso de teste verifica o comportamento dos votos
   */
  @Test
  public void testVotes() {
    idea.registerVote(user1);
    // Usuario nao pode votar na propria ideia
    assertFalse(idea.voters.contains(user1));

    // Usuario 2 votando
    idea.registerVote(user2);
    assertTrue(idea.voters.contains(user2));

    // Usuario não pode votar mais de uma vez na mesma ideia
    idea.registerVote(user2);
    assertTrue(idea.countVotes() == 1);

    // Retirando votos
    idea.reclaimVote(user1);
    idea.reclaimVote(user2);
    assertTrue(idea.countVotes() == 0);
  }
}