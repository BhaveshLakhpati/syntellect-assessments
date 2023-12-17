import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Scanner;

class HousieTicketGenerator {
	private int[][] housieTicket;
	private Map<Integer, Map<String, Integer>> columnMapping;
	private int numOfRows, numOfColumns;

	public HousieTicketGenerator(final int numOfRows, final int numOfColumns) {
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;

		this.housieTicket = new int[numOfRows][numOfColumns];
		this.columnMapping = new HashMap<>();
		IntStream.range(0, this.numOfColumns).forEach(index -> this.columnMapping.put(index, new HashMap<>(2)));
	}

	private void generate() {
		Random columnRandom = new Random();

		for(int rowIndex = 0; rowIndex < this.numOfRows; rowIndex++) {
			int counter = 5, randomColumnIndex, columnValue;
			Map<String, Integer> valuesInColumn;
			while(counter > 0) {
				randomColumnIndex = columnRandom.nextInt(1, (this.numOfColumns + 1));

				valuesInColumn = this.columnMapping.get(randomColumnIndex - 1);

				if(valuesInColumn.size() < 2) {
					do {
						columnValue = (randomColumnIndex - 1) * 10 + columnRandom.nextInt(1, this.numOfColumns);
						// System.out.printf("COLUMN : %1$d, CONTAINS : %1$d\n", randomColumnIndex, columnValue);
					} while(valuesInColumn.containsValue(columnValue));

					valuesInColumn.put(((rowIndex + 1) + "x" + randomColumnIndex), columnValue);
				}
				counter--;
			}
		}

		Map<String, Integer> valuesMap = this.columnMapping.values().stream().flatMap(m -> m.entrySet().stream()).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

		Integer value;
		for(int rowIndex = 1; rowIndex <= this.numOfRows; rowIndex++) {
			for(int columnIndex = 1; columnIndex <= this.numOfColumns; columnIndex++) {
				value = valuesMap.get(rowIndex+"x"+columnIndex);

				if(value != null) {
					System.out.printf("|%1$d\t", value);
				} else {
					System.out.print("|\t");
				}
			}
			System.out.println();
		}
	}

	public static void main(final String[] args) {
		System.out.println("To exit, type 0");

		Scanner scanner = new Scanner(System.in);
		System.out.print("Number of rows : ");
		int numOfRows = scanner.nextInt();

		System.out.print("Number of columns : ");
		int numOfColumns = scanner.nextInt();

		if(numOfRows < 3 || numOfColumns < 3) {
			System.out.println("Min requirement of 3x3");
		} else {
			new HousieTicketGenerator(numOfRows, numOfColumns).generate();
		}		
	}
}