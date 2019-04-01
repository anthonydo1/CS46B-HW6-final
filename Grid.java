package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;


	//
	// DON'T CHANGE THIS.
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}


	//
	// DON'T CHANGE THIS.
	//
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}


	//
	// DON'T CHANGE THIS.
	// Copy ctor. Duplicates its source. You'll call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}


	//
	// COMPLETE THIS
	//
	//
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Dont change
	// this grid. Build 9 new grids.
	// 
	//
	// Example: if this grid = 1........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//
	// Then the returned array list would contain:
	//
	// 11.......          12.......          13.......          14.......    and so on     19.......
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	//
	public ArrayList<Grid> next9Grids()
	{		
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;
		boolean done = false;
		// Find x,y of an empty cell.
		while (!done) {
			for (int i = 0; i < 9 && done == false; i++) {
				for (int j = 0; j < 9 && done == false; j++) {
					int cellValue = values[i][j];
					if (cellValue == 0) {
						xOfNextEmptyCell = j;
						yOfNextEmptyCell = i;
						done = true;
					}
				}
			}
		}
		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<Grid>();

		// Create 9 new grids as described in the comments above. Add them to grids.
		for (int i = 1; i <= 9; i++) {
			Grid newGrid = new Grid(this);
			newGrid.values[yOfNextEmptyCell][xOfNextEmptyCell] = i;
			grids.add(newGrid);
		}
		return grids;
	}


	//
	// COMPLETE THIS
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{
		// Check every row. If you find an illegal row, return false.
		// Check every column. If you find an illegal column, return false.
		
		// Check every block. If you find an illegal block, return false.
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (checkRowAndColumn(row, col, values[row][col]) == false) {
					return false;
				}
				
				if (checkSubgrid(row, col, values[row][col]) == false) {
					return false;
				}
				
			}
		}
		// All rows/cols/blocks are legal.
		return true;
	}


	public boolean checkRowAndColumn(int row, int col, int val) {
		for (int i = 0; i < 9; i++) {
			int cellValue = values[i][col];
			if (cellValue == val && i != row && cellValue != 0) {
				return false;
			}
		}
		for (int j = 0; j < 9; j++) {
			int cellValue = values[row][j];
			if (cellValue == val && j != col && cellValue != 0) {
				return false;
			}
		}
		return true;
	}


	public boolean checkSubgrid(int row, int col, int val) {
		int startrow = row - row % 3;
		int startcol = col - col % 3;

		for(int r = startrow; r < startrow + 3; r++) {
			for (int c = startcol; c < startcol + 3; c++) {
				int cellValue = values[r][c];
				if (val == cellValue && r != row && c != col && cellValue != 0) {
					return false;
				}
			}
		}
		return true;
	}

	
	//
	// COMPLETE THIS
	//
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull()
	{
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				int cellValue = values[i][j];
				if (cellValue < 1 || cellValue > 9) {
					return false;
				}
			}
		}
		return true;
	}


	//
	// COMPLETE THIS
	//
	// Returns true if x is a Grid and, for every (i,j), 
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x)
	{
		Grid that = (Grid)x;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (this.values[i][j] != that.values[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Grid grid = new Grid(TestGridSupplier.getSolution1());
		System.out.println(grid.isFull());
		System.out.println(grid.isLegal());
		Grid grid2 = new Grid(TestGridSupplier.getPuzzle1());
		ArrayList<Grid> grids = grid2.next9Grids();
		for (Grid grids2 : grids) {
			System.out.println(grids2.toString());
		}
	}
}
