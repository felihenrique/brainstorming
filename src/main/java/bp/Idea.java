package bp;

import java.util.ArrayList;
import java.util.List;

/**
 * Essa classe representa uma idéia que pode ser adicionada a uma sessão.
 */
public class Idea {
  Session session;
  String description;
  User author;
  List<User> voters;

  /**
   * Instancia uma idéia
   * @param author Autor da idéia
   * @param description Descrição da idéia
   */
  public Idea(User author, String description) {
    this.author = author;
    this.description = description;
    this.voters = new ArrayList<>();
  }

  /**
   * Registra o voto de um usuário na ideia
   * @param u Usuário votando
   */
  public void registerVote(User u) {
    boolean notContainsVote = !voters.contains(u);
    boolean notInLimitOfVotes = u.votes < session.votingLimit;
    boolean inPhase = session.phase == SessionPhase.VOTING;
    boolean notClaimIdea = u != author;
    boolean inSession = session.participants.contains(u);
    if(notContainsVote && notInLimitOfVotes && inPhase && notClaimIdea && inSession) {
      voters.add(u);
      u.votes++;
    }
  }

  /**
   * Retira o voto de um usuário na idéia.
   * @param u Usuário a retirar o voto.
   */
  public void reclaimVote(User u) {
    boolean containsVote = voters.contains(u);
    boolean inPhase = session.phase == SessionPhase.VOTING;
    if(containsVote && inPhase) {
      voters.remove(u);
      u.votes--;
    }
  }

  /**
   * @return Total de votos na idéia
   */
  public int countVotes() {
    return voters.size();
  }
  
}