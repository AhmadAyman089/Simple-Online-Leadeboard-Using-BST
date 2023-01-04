package project1;

import java.util.ArrayList;
import java.util.List;

// Main class that uses the Leaderboard class
public class Main {
  public static void main(String[] args) {
    // Create a new Leaderboard instance
    Leaderboard leaderboard = new Leaderboard();

    // Add some scores to the leaderboard
    leaderboard.addScore("khairul", 75);
    leaderboard.addScore("danial", 50);
    leaderboard.addScore("izz", 25);
    leaderboard.addScore("haikal", 45);
    leaderboard.addScore("aisy", 60);
    leaderboard.addScore("ayman", 80);
    

    // Get the top 2 players
    List<String> topPlayers = leaderboard.getTopPlayers(2);

    // Print the names of the top players
    for (String playerName : topPlayers) {
      System.out.println(playerName);
    }
  }
}

// Class that represents a leaderboard entry with a player's name and score
class LeaderboardEntry {
  String playerName;
  int score;

  public LeaderboardEntry(String playerName, int score) {
    this.playerName = playerName;
    this.score = score;
  }
}

// Class that represents a leaderboard
class Leaderboard {
  // Inner class that represents a node in the binary search tree
  private class Node {
    LeaderboardEntry entry;
    Node left;
    Node right;

    public Node(LeaderboardEntry entry) {
      this.entry = entry;
    }
  }

  Node root;

  // Method that adds a new score to the leaderboard
  public void addScore(String playerName, int score) {
    // Create a new LeaderboardEntry object
    LeaderboardEntry entry = new LeaderboardEntry(playerName, score);
    // Insert the entry into the binary search tree
    root = addScore(root, entry);
  }

  // Recursive helper method for adding a score to the leaderboard
  private Node addScore(Node node, LeaderboardEntry entry) {
    if (node == null) {
      // Return a new node if the tree is empty
      return new Node(entry);
    }

    if (entry.score > node.entry.score) {
      // Add the entry to the right subtree if its score is greater than the current node's score
      node.right = addScore(node.right, entry);
    } else {
      // Add the entry to the left subtree if its score is less than or equal to the current node's score
      node.left = addScore(node.left, entry);
    }

    return node;
  }

  // Method that returns the top n players sorted in descending order of their scores
  public List<String> getTopPlayers(int n) {
    // Create a list to store the top players
    List<String> topPlayers = new ArrayList<>();
    // Traverse the tree in the reverse in-order traversal order and add the player names to the list
    getTopPlayers(root, n, topPlayers);
    return topPlayers;
  }

  // Recursive helper method for getting the top players
  private void getTopPlayers(Node node, int n, List<String> topPlayers) {
    if (node == null || topPlayers.size() == n) {
      // Return if the tree is empty or the list has reached the required size
      return;
    }

 // Traverse the right subtree
    getTopPlayers(node.right, n, topPlayers);

    if (topPlayers.size() < n) {
      // Add the player name to the list if the list is not full yet
      topPlayers.add(node.entry.playerName);
    }

    // Traverse the left subtree
    getTopPlayers(node.left, n, topPlayers);
    
  }
}


