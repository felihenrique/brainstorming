package bp;

import java.util.ArrayList;
import java.util.List;

/**
 * Idea
 */
public class Idea {
  Session session;
  String description;
  User author;
  List<User> voters;

  public Idea(User author, String description) {
    this.author = author;
    this.description = description;
    this.voters = new ArrayList<>();
  }

  public void registerVote(User u) {
    if(!voters.contains(u) && u.votes < session.votingLimit) {
      voters.add(u);
      u.votes++;
    }
  }

  public void reclaimVote(User u) {
    if(voters.contains(u)) {
      voters.remove(u);
      u.votes--;
    }
  }

  public int countVotes() {
    return voters.size();
  }
  
}