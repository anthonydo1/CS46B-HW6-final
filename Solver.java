package sudoku;

import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
		
	// 
	// FINISH THIS.
	//
	// Standard backtracking recursive solver.
	//
	private void solveRecurse(Grid grid)
	{		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			// Abandon evaluation of this illegal board.
			return;
		}
		else if (eval == Evaluation.ACCEPT)
		{
			// A complete and legal solution. Add it to solutions.
			solutions.add(grid);
		}
		else
		{
			// Here if eval == Evaluation.CONTINUE. Generate all 9 possible next grids. Recursively 
			ArrayList<Grid> nineGrids = grid.next9Grids();
			for (int i = 0; i < 9; i++) {
				Evaluation evaluation = evaluate(nineGrids.get(i));
				if (evaluation == Evaluation.CONTINUE) {
					solveRecurse(nineGrids.get(i));
				} else if (evaluation == Evaluation.ACCEPT) {
					solutions.add(nineGrids.get(i));
				}
			}
			// call solveRecurse() on those grids.
		}
	}
	
	//
	// COMPLETE THIS
	//
	// Returns Evaluation.ABANDON if the grid is illegal. 
	// Returns ACCEPT if the grid is legal and complete.
	// Returns CONTINUE if the grid is legal and incomplete.
	//
	public Evaluation evaluate(Grid grid)
	{
		boolean legal = grid.isLegal();
		boolean full = grid.isFull();
		if (legal == false) {
			return Evaluation.ABANDON;
		} else if (legal == true && full == true) {
			return Evaluation.ACCEPT;
		} else {
			return Evaluation.CONTINUE;
		}

	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		Grid g = TestGridSupplier.getPuzzle3();		// or any other puzzle
		Solver solver = new Solver(g);
		System.out.println("Will solve\n" + g);
		solver.solve();
		System.out.println(solver.getSolutions());
		// Print out your solution, or test if it equals() the solution in TestGridSupplier.
	}
}
