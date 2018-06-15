package bp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Essa classe representa uma sessão de brainstorming, tendo funções para controle do fluxo, usuários e idéias.
 */
public class Session {

  User owner;
  String description;
  SessionPhase phase;
  int votingLimit = 1;
  List<Idea> ideas;
  List<User> participants;

    /**
     * Instancia uma nova sessão de brainstorming.
     * @param owner Dono da sessão.
     * @param description Descrição da sessão.
     */
  public Session(User owner, String description) {
    this.owner = owner;
    this.phase = SessionPhase.WELCOME;
    this.description = description;
    this.ideas = new ArrayList<>();
    this.participants = new ArrayList<>();
  }

  private SessionPhase getNextPhase() {
    switch (phase) {
      case WELCOME:
        return SessionPhase.BRAINSTORM;
      case BRAINSTORM:
        return SessionPhase.VOTING;
      case VOTING:
        return SessionPhase.RANK;
      case RANK:
        return SessionPhase.RANK;
    }
    return SessionPhase.RANK;
  }

    /**
     * Avança para a proxima fase da sessão
     * @return A próxima fase.
     */
  public SessionPhase nextPhase() {
    phase = getNextPhase();
    return phase;
  }

    /**
     * @return A fase em que se encontra a sessão.
     */
  public SessionPhase getPhase() {
    return phase;
  }

    /**
     *
     * @param idea Adiciona uma idéia na sessão.
     * @return Booleano indicando se a adição foi bem sucedida.
     */
  public boolean addIdea(Idea idea) {
    boolean notContains = !ideas.contains(idea);
    boolean inPhase = phase == SessionPhase.BRAINSTORM;
    boolean inSession = participants.contains(idea.author);
    if(notContains && inPhase && inSession) {
      idea.session = this;
      ideas.add(idea);
    }
    return notContains && inPhase && inSession;
  }

    /**
     * Retorna a lista de idéias presentes na sessão.
     * @return Uma lista de idéias.
     */
  public List<Idea> getIdeas() {
    return ideas;
  }

    /**
     * Retorna a lista de idéias ordenada decrescente de acordo com a quantidade de votos. Retorna
     * apenas as idéias com votos > 0
     * @return Uma lista de idéias.
     */
  public List<Idea> rankIdeas() {
    return ideas
            .stream()
            .filter(i -> !i.voters.isEmpty())
            .sorted((a, b) -> a.countVotes() - b.countVotes())
            .collect(Collectors.toList());
  }

    /**
     * Adiciona um participante na sessão
     * @param participant O participante a ser adicionado
     * @return Um booleano indicando se a adição foi bem sucedida.
     */
  public boolean addParticipant(User participant) {
    boolean inPhase = this.phase == SessionPhase.WELCOME;
    boolean notContains = !participants.contains(participant);
    if(notContains && inPhase) {
      participants.add(participant);
    }
    return notContains && inPhase;
  }

    /**
     * Remove um participante da sessão.
     * @param participant O participante a ser removido.
     * @return Um booleano indicando se a remoção foi bem sucedida.
     */
  public boolean removeParticipant(User participant) {
    boolean contains = participants.contains(participant);
    if(contains) {
      participants.remove(participant);
    }
    return contains;
  }

    /**
     * Retorna a lista de participantes da sessão.
     * @return Uma lista de usuários.
     */
  public List<User> getParticipants() {
    return participants;
  }

}