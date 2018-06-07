package bp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Session
 */
public class Session {

  User owner;
  String description;
  SessionPhase phase;
  int votingLimit = 3;
  List<Idea> ideas;
  List<User> participants;

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
    }
    return null;
  }


  public SessionPhase nextPhase() {
    phase = getNextPhase();
    return phase;
  }

  public SessionPhase getPhase() {
    return phase;
  }
  
  public boolean addIdea(Idea idea) {
    boolean contains = ideas.contains(idea);
    if(!contains) {
      idea.session = this;
      ideas.add(idea);
    }
    return contains;
  }

  public List<Idea> getIdeas() {
    return ideas;
  }

  public List<Idea> rankIdeas() {
    return ideas
            .stream()
            .sorted((a, b) -> a.countVotes() - b.countVotes())
            .collect(Collectors.toList());
  }

  public boolean addParticipant(User participant) {
    boolean contains = participants.contains(participant);
    if(!contains) {
      participants.add(participant);
    }
    return !contains;
  }

  public boolean removeParticipant(User participant) {
    boolean contains = participants.contains(participant)
    if(contains) {
      participants.remove(participant);
    }
    return contains;
  }

  public List<User> getParticipants() {
    return participants;
  }

}