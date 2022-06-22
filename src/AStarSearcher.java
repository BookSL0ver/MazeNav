import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		// TODO initialize the root state and add
		// to frontier list
		// ...
		State t = new State(maze.getPlayerSquare(), null, 0, 0);
		StateFValuePair start = new StateFValuePair(t, f(distance(t), t.getDepth()));
		frontier.add(start);
		int sizeFrontier = 1;

		while (!frontier.isEmpty()) {
			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found
			StateFValuePair current = frontier.poll();
			noOfNodesExpanded++;
			sizeFrontier--;
			explored[current.getState().getX()][current.getState().getY()] = true;
			if (current.getState().getDepth() > maxDepthSearched)
			{
				maxDepthSearched = current.getState().getDepth();
				cost = current.getState().getDepth();
			}
			if (current.getState().isGoal(maze))
			{
				State cur = current.getState();
				while (maze.getSquareValue(cur.getParent().getX(), cur.getParent().getY()) != 'S')
				{
					maze.setOneSquare(cur.getParent().getSquare(), '.');
					cur = cur.getParent();
				}
				return true;
			}
			ArrayList<State> expand = current.getState().getSuccessors(explored, maze);
			ArrayList<StateFValuePair> test =  new ArrayList<StateFValuePair>();
			for (int i = 0; i < expand.size(); i++)
			{
				StateFValuePair temp = new StateFValuePair(expand.get(i), f(distance(expand.get(i)), expand.get(i).getDepth()));
				test.add(temp);
			}
			for (int x = 0; x < test.size(); x++)
			{
				Iterator<StateFValuePair> it = frontier.iterator();
				boolean add = true;
				while(it.hasNext())
				{
					StateFValuePair temp = it.next();
					if(temp.getState().getX() == test.get(x).getState().getX() && temp.getState().getY() == test.get(x).getState().getY())
					{
						if (temp.getState().getDepth() > test.get(x).getState().getDepth())
						{
							frontier.remove(temp);
						}
						else
						{
							add = false;
						}
					}
				}
				if (add)
				{
					frontier.add(test.get(x));
					sizeFrontier++;
				}
			}
			if (sizeFrontier > maxSizeOfFrontier)
			{
				maxSizeOfFrontier = sizeFrontier;
			}
			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
		}

		// TODO return false if no solution
		return false;
	}
	
	private double distance(State current)
	{
		return Math.sqrt(Math.pow(maze.getGoalSquare().X - current.getX(), 2) + Math.pow(maze.getGoalSquare().Y - current.getY(), 2));
	}
	
	private double f(double distance, int cost)
	{
		return distance + cost;
	}

}
