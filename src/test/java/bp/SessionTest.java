package bp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.List;

/**
 * Testes para a classe Sessão.
 */
public class SessionTest {
  private User[] users = {
    new User("Fulano"),
    new User("Cicrano"),
    new User("Beltrano")
  };
  private Idea[] ideas = {
          new Idea(users[0], "Ideia 1"),
          new Idea(users[1], "Ideia 2"),
          new Idea(users[2], "Ideia 3")
  };

  /**
   * Esse teste verifica se o estado do objeto é o esperado na inicialização.
   */
  @Test
  public void creation() {
    Session session = new Session(users[0], "Brainstorm dos melhores");

    // O titulo deve ser o mesmo passado na inicialização
    assertTrue(session.description.equals("Brainstorm dos melhores"));

    // O dono deve ser o mesmo passado na inicialização
    assertTrue(session.owner == users[0]);

    // Ela deve se encontrar na fase de acolhimento
    assertTrue(session.getPhase() == SessionPhase.WELCOME);

    // A lista de ideias deve estar vazia
    assertTrue(session.ideas.isEmpty());

    // A lista de participantes deve estar vazia
    assertTrue(session.participants.isEmpty());
  }

  /**
   * Esse teste verifica se sessão está seguindo o fluxo normal de fases ao avançar.
   */
  @Test
  public void nextPhase() {
    Session session2 = new Session(users[1], "Sessao 2");
    assertTrue(session2.getPhase() == SessionPhase.WELCOME);
    session2.nextPhase();
    assertTrue(session2.getPhase() == SessionPhase.BRAINSTORM);
    session2.nextPhase();
    assertTrue(session2.getPhase() == SessionPhase.VOTING);
    session2.nextPhase();
    assertTrue(session2.getPhase() == SessionPhase.RANK);
    session2.nextPhase();
    assertTrue(session2.getPhase() == SessionPhase.RANK);
  }

  /**
   * Esse caso de teste verifica o comportamento da sessão removendo e adicionando usuários
   */
  @Test
  public void addParticipant() {
    Session session1 = new Session(users[0], "Brainstorm dos melhores");
    Session session2 = new Session(users[0], "Brainstorm dos melhores");

    // Avançando para a fase de ideias
    session2.nextPhase();

    // Usuario não pode ser adicionado se não estiver na fase WELCOME
    assertFalse(session2.addParticipant(users[0]));

    // Usuario não pode ser adicionado se já estiver como participante
    assertTrue(session1.addParticipant(users[0]));
    assertFalse(session1.addParticipant(users[0]));

    // Tamanho da lista passa a ser 1
    assertTrue(session1.participants.size() == 1);

    // A lista de participantes é inalterada se ao remover o usuario nao for participante.
    assertFalse(session1.removeParticipant(users[1]));
    assertTrue(session1.participants.size() == 1);

    // Caso o usuario exista na sessão ele deixa de fazer parte e o tamanho e decrementado de 1
    assertTrue(session1.removeParticipant(users[0]));
    assertFalse(session1.participants.contains(users[0]));
  }

  /**
   * Esse caso de teste verifica o comportamento da sessão adicionando e removendo ideias.
   */
  @Test
  public void addIdea() {
    Session session1 = new Session(users[0], "Brainstorm dos melhores");

    // A lista de idéias permance inalterada se não tiver na fase de brainstorming
    assertFalse(session1.addIdea(ideas[0]));
    assertTrue(session1.ideas.isEmpty());

    // A lista de idéias permanece inalterada se o autor da idéia não for um dos participantes da sessão.
    assertTrue(session1.addParticipant(users[0]));
    assertTrue(session1.addParticipant(users[1]));
    session1.nextPhase();
    assertFalse(session1.addIdea(ideas[2]));
    assertFalse(session1.ideas.contains(ideas[2]));

    // Caso a ideia seja de um participante o processo ocorre normalmente
    assertTrue(session1.addIdea(ideas[0]));
    assertTrue(session1.addIdea(ideas[1]));
    assertTrue(session1.ideas.size() == 2);
  }

  /**
   * Esse caso de teste verifica se o rank está funcionando como esperado.
   */
  @Test
  public void testRank() {
    User user1 = new User("User1");
    User user2 = new User("User2");
    User user3 = new User("User3");
    Idea idea1 = new Idea(user1, "Idea1");
    Idea idea2 = new Idea(user2, "Idea2");
    Idea idea3 = new Idea(user3, "Idea3");

    Session session1 = new Session(users[0], "Brainstorm dos melhores");
    session1.addParticipant(user1);
    session1.addParticipant(user2);
    session1.addParticipant(user3);

    session1.nextPhase();

    session1.addIdea(idea1);
    session1.addIdea(idea2);
    session1.addIdea(idea3);
    session1.nextPhase();

    idea1.registerVote(user1);
    idea1.registerVote(user2);
    idea2.registerVote(user3);

    List<Idea> ranked = session1.rankIdeas();
    assertTrue(ranked.size() == 2);
    assertTrue(ranked.get(0).equals(idea1));
    assertTrue(ranked.get(1).equals(idea2));
    assertFalse(ranked.contains(idea3));
  }
}