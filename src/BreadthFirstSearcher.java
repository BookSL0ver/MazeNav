import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// ...

		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		queue.add(new State(maze.getPlayerSquare(), null, 0, 0));
		int sizeFrontier = 1;

		while (!queue.isEmpty()) {
			// TODO return true if find a solution
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			State current = queue.pop();
			noOfNodesExpanded++;
			sizeFrontier--;
			explored[current.getX()][current.getY()] = true;
			if (current.getDepth() > maxDepthSearched) 
			{
				maxDepthSearched = current.getDepth();
				cost = current.getDepth();
			}
			if (current.isGoal(maze))
			{
				State cur = current;
				while (maze.getSquareValue(cur.getParent().getX(), cur.getParent().getY()) != 'S')
				{
					maze.setOneSquare(cur.getParent().getSquare(), '.');
					cur = cur.getParent();
				}
				return true;
			}
			ArrayList<State> expand = current.getSuccessors(explored, maze);
			// TODO update the maze if a solution found
			// use queue.pop() to pop the queue.
			// use queue.add(...) to add elements to queue
			for (int i = 0; i < expand.size(); i++)
			{
				Iterator<State> it = queue.iterator();
				boolean add = true;
				while(it.hasNext())
				{
					State temp = it.next();
					if(temp.getX() == expand.get(i).getX() && temp.getY() == expand.get(i).getY())
					{
						add = false;
					}
				}
				if (add)
				{
					queue.add(expand.get(i));
					sizeFrontier++;
				}
			}
			if (sizeFrontier > maxSizeOfFrontier)
			{
				maxSizeOfFrontier = sizeFrontier;
			}

		}

		// TODO return false if no solution
		return false;
	}
}
