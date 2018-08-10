import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

	private ST<String, Integer> teamId;

	private int n;

	private String[] idName;

	private int[] wins;

	private int[] losses;

	private int[] remaining;

	private int[][] against;

	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {
		In in = new In(filename);
		n = in.readInt();
		teamId = new ST<String, Integer>();
		idName = new String[n];
		wins = new int[n];
		losses = new int[n];
		remaining = new int[n];
		against = new int[n][n];
		for (int i = 0; i < n; i += 1) {
			idName[i] = in.readString();
			wins[i] = in.readInt();
			losses[i] = in.readInt();
			remaining[i] = in.readInt();
			for (int j = 0; j < n; j += 1) {
				against[i][j] = in.readInt();
			}
			teamId.put(idName[i], i);
		}
	}

	// number of teams
	public int numberOfTeams() {
		return n;
	}

	// all teams
	public Iterable<String> teams() {
		return teamId.keys();
	}

	private void checkValid(String team) {
		if (team == null || !teamId.contains(team)) {
			throw new java.lang.IllegalArgumentException();
		}
	}

	// number of wins for given team
	public int wins(String team) {
		checkValid(team);
		return wins[teamId.get(team)];
	}

	// number of losses for given team
	public int losses(String team) {
		checkValid(team);
		return losses[teamId.get(team)];
	}

	// number of remaining games for given team
	public int remaining(String team) {
		checkValid(team);
		return remaining[teamId.get(team)];
	}

	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
		checkValid(team1);
		checkValid(team2);
		return against[teamId.get(team1)][teamId.get(team2)];
	}

	private int transfer(int i, int id) {
		if (i < id) {
			return i;
		} else {
			return i + 1;
		}
	}

	private FlowNetwork genNetwork(String team) {
		int id = teamId.get(team);
		int cnt = n - 1;
		int V = (cnt * (cnt - 1)) / 2 + cnt + 2;
		FlowNetwork network = new FlowNetwork(V);
		int s = V - 2;
		int t = V - 1;
		int temp = cnt;
		for (int i = 0; i < cnt; i += 1) {
			int x = transfer(i, id);
			int capacity = wins[id] + remaining[id] - wins[x];
			FlowEdge flowedge = new FlowEdge(i, t, capacity);
			network.addEdge(flowedge);
			for (int j = i + 1; j < cnt; j += 1) {
				network.addEdge(new FlowEdge(s, temp, against[transfer(i, id)][transfer(j, id)]));
				network.addEdge(new FlowEdge(temp, i, Double.POSITIVE_INFINITY));
				network.addEdge(new FlowEdge(temp, j, Double.POSITIVE_INFINITY));
				temp += 1;
			}
		}
		return network;
	}

	// is given team eliminated
	public boolean isEliminated(String team) {
		checkValid(team);
		int id = teamId.get(team);
		int maxwin = wins[id] + remaining[id];
		for (int i = 0; i < n; i += 1) {
			if (wins[i] > maxwin) {
				return true;
			}
		}
		int V = ((n - 1) * (n - 2)) / 2 + n + 1;
		FlowNetwork network = genNetwork(team);
		FordFulkerson temp = new FordFulkerson(network, V - 2, V - 1);
		for (FlowEdge e : network.adj(V - 2)) {
			if (e.capacity() != e.flow()) {
				return true;
			}
		}
		return false;
	}

	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team) {
		checkValid(team);
		int id = teamId.get(team);
		int cnt = n - 1;
		int V = (cnt * (cnt - 1)) / 2 + cnt + 2;
		Queue<String> certificate = new Queue<String>();
		boolean isEliminated = false;
		for (int i = 0; i < n; i += 1) {
			if (wins[id] + remaining[id] < wins[i]) {
				isEliminated = true;
				certificate.enqueue(idName[i]);
			}
		}
		if (isEliminated) {
			return certificate;
		}
		FlowNetwork network = genNetwork(team);
		FordFulkerson temp = new FordFulkerson(network, V - 2, V - 1);
		for (int i = 0; i < cnt; i += 1) {
			if (temp.inCut(i)) {
				isEliminated = true;
				certificate.enqueue(idName[transfer(i, id)]);
			}
		}
		if (isEliminated) {
			return certificate;
		}
		return null;
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
   		for (String team : division.teams()) {
        	if (division.isEliminated(team)) {
            	StdOut.print(team + " is eliminated by the subset R = { ");
            	for (String t : division.certificateOfElimination(team)) {
                	StdOut.print(t + " ");
            	}
            	StdOut.println("}");
        	}
        	else {
            	StdOut.println(team + " is not eliminated");
        	}
    	}
	}

}