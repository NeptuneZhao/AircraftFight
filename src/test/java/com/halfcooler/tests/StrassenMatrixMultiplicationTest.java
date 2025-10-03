package com.halfcooler.tests;

import com.halfcooler.utils.StrassenMatrixMultiplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class StrassenMatrixMultiplicationTest
{
	private static final StrassenMatrixMultiplication smm = new StrassenMatrixMultiplication();

	@BeforeEach
	void setUp()
	{

	}

	@AfterEach
	void tearDown()
	{

	}

	@Test
	void multiply()
	{
		StrassenMatrixMultiplication smm = new StrassenMatrixMultiplication();
		int[][] A = {{1, 2}, {3, 4}};
		int[][] B = {{5, 6}, {7, 8}};
		int[][] expected = {{19, 22}, {43, 50}};
		assertArrayEquals(expected, smm.multiply(A, B));
	}

	@Test
	void sub()
	{
		StrassenMatrixMultiplication smm = new StrassenMatrixMultiplication();
		int[][] A = {{5, 6}, {7, 8}};
		int[][] B = {{1, 2}, {3, 4}};
		int[][] expected = {{4, 4}, {4, 4}};
		assertArrayEquals(expected, smm.sub(A, B));
	}

	@Test
	void add()
	{
		int[][] A = {{1, 2}, {3, 4}};
		int[][] B = {{5, 6}, {7, 8}};
		int[][] expected = {{6, 8}, {10, 12}};
		assertArrayEquals(expected, smm.add(A, B));
	}

	@Test
	void split()
	{
		int[][] P = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
		int[][] C = new int[2][2];
		int row = 1, col = 1;
		int[][] expected = {{6, 7}, {10, 11}};
		smm.split(P, C, row, col);
		assertArrayEquals(expected, C);
	}

	@Test
	void join()
	{
		int[][] C = {{1, 2}, {3, 4}};
		int[][] P = new int[4][4];
		int row = 1, col = 1;
		int[][] expected = {{0, 0, 0, 0}, {0, 1, 2, 0}, {0, 3, 4, 0}, {0, 0, 0, 0}};
		smm.join(C, P, row, col);
		assertArrayEquals(expected, P);
	}
}