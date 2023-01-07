package project1;

import java.util.ArrayList;
import java.util.List;
//Main class that uses the Leaderboard class
public class Main {
  public static void main(String[] args) {
    Leaderboard leaderboard = new Leaderboard();
 // Add some scores to the leaderboard
    leaderboard.insert("khairul", 75);
    leaderboard.insert("danial", 50);
    leaderboard.insert("izz", 25);
    leaderboard.insert("haikal", 45);
    leaderboard.insert("aisy", 60);
    leaderboard.insert("ayman", 80);
    // Get the top 3 players
    List<LeaderboardEntry> topPlayers = leaderboard.getTopPlayers(3);

    for (LeaderboardEntry player : topPlayers) {
      System.out.println(player.playerName + ": " + player.score);
    }
  }
}
// A class to represent a leaderboard entry (player name and score)

class LeaderboardEntry {
	  String playerName;
	  int score;

	  public LeaderboardEntry(String playerName, int score) {
	    this.playerName = playerName;
	    this.score = score;
	  }
	}

class Leaderboard {
	  // A class to represent a node in the binary search tree
	  private class Node {
	    LeaderboardEntry entry;
	    Node left;
	    Node right;

	    public Node(LeaderboardEntry entry) {
	      this.entry = entry;
	    }
	  }

	  Node root;

	  // Inserts a new player and their score into the tree
	  public void insert(String playerName, int score) {
	    LeaderboardEntry entry = new LeaderboardEntry(playerName, score);
	    root = insert(root, entry);
	  }

	  private Node insert(Node node, LeaderboardEntry entry) {
	    if (node == null) {
	      return new Node(entry);
	    }

	    if (entry.score < node.entry.score) {
	      node.left = insert(node.left, entry);
	    } else if (entry.score > node.entry.score) {
	      node.right = insert(node.right, entry);
	    } else {
	      // Update the score if the player already exists in the tree
	      node.entry.score = entry.score;
	    }

	    return node;
	  }

	  // Searches for a player in the tree and returns their score, or -1 if not found
	  public int search(String playerName) {
	    Node node = search(root, playerName);
	    return node == null ? -1 : node.entry.score;
	  }

	  private Node search(Node node, String playerName) {
	    if (node == null) {
	      return null;
	    }

	    if (playerName.equals(node.entry.playerName)) {
	      return node;
	    } else if (playerName.compareTo(node.entry.playerName) < 0) {
	      return search(node.left, playerName);
	    } else {
	      return search(node.right, playerName);
	    }
	  }

	  // Deletes a player from the tree
	  public void delete(String playerName) {
	    root = delete(root, playerName);
	  }

	  private Node delete(Node node, String playerName) {
	    if (node == null) {
	      return null;
	    }

	    if (playerName.equals(node.entry.playerName)) {
	      if (node.left == null && node.right == null) {
	        return null;
	      } else if (node.left == null) {
	        return node.right;
	      } else if (node.right == null) {
	        return node.left;
	      } else {
	        // Find the minimum value in the right subtree
	        Node minNode = findMin(node.right);
	        node.entry = minNode.entry;
	        node.right = delete(node.right, minNode.entry.playerName);
	        return node;
	      }
	    } else if (playerName.compareTo(node.entry.playerName) < 0) {
	      node.left = delete(node.left, playerName);
	      return node;
	    } else {
	      node.right = delete(node.right, playerName);
	      return node;
	    }
	  }

	  // Finds the node with the minimum value in a given
	  private Node findMin(Node node) {
		    while (node.left != null) {
		      node = node.left;
		    }
		    return node;
		  }

		  public List<LeaderboardEntry> getTopPlayers(int n) {
		    List<LeaderboardEntry> topPlayers = new ArrayList<>();
		    getTopPlayers(root, n, topPlayers);
		    return topPlayers;
		  }

		  private void getTopPlayers(Node node, int n, List<LeaderboardEntry> topPlayers) {
		    if (node == null || topPlayers.size() == n) {
		      return;
		    }

		    getTopPlayers(node.right, n, topPlayers);

		    if (topPlayers.size() < n) {
		      topPlayers.add(node.entry);
		    }

		    getTopPlayers(node.left, n, topPlayers);
		  }
		}

