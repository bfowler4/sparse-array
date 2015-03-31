public class MyMatrixElem implements MatrixElem {
	int row; // row index
	int col; // column index
	MyMatrixElem nextRow;
	MyMatrixElem nextCol;
	Object data;

	public MyMatrixElem(Object data, MyMatrixElem nextRow,
			MyMatrixElem nextCol, int row, int col) {
		this.row = row;
		this.col = col;
		this.nextRow = nextRow;
		this.nextCol = nextCol;
		this.data = data;
	}

	/**
	 * Returns the row index of the element.
	 */
	@Override
	public int rowIndex() {
		return row;
	}

	/**
	 * Returns the column index of the element.
	 */
	@Override
	public int columnIndex() {
		return col;
	}

	/**
	 * Returns the default value for the sparse array.
	 */
	@Override
	public Object value() {
		return data;
	}

	/**
	 * Returns the element in the next row, same column.
	 * 
	 * @return
	 */
	public MyMatrixElem getNextRow() {
		return nextRow;
	}

	/**
	 * Sets columns next row elem
	 * 
	 * @param nextRow
	 */
	public void setNextRow(MyMatrixElem nextRow) {
		this.nextRow = nextRow;
	}

	public MyMatrixElem getNextCol() {
		return nextCol;
	}

	public void setNextCol(MyMatrixElem nextCol) {
		this.nextCol = nextCol;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
