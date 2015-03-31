public class MySparseArray implements SparseArray {
	RowElem rows;
	RowElem cols;
	Object data;

	public MySparseArray(Object data) {
		this.data = data;
	}

	/**
	 * Value used to remove element from the sparse array or not add it at all.
	 */
	@Override
	public Object defaultValue() {
		return this.data;
	}

	/**
	 * Used to iterate through the rows of the sprase array.
	 * 
	 * @return - returns a row iterator to iterate through the rows
	 */
	@Override
	public RowIterator iterateRows() {
		RowIterator rowIterator = new MyRowIterator();
		return rowIterator;
	}

	/**
	 * Used to iterate through the columns of the sparse array.
	 * 
	 * @return - returns a column iterator to iterate through the columns.
	 */
	@Override
	public ColumnIterator iterateColumns() {
		ColumnIterator colIterator = new MyColIterator();
		return colIterator;
	}

	/**
	 * Returns the value stored in the element at the location specified by row
	 * and column or the default value if no such element exists.
	 */
	@Override
	public Object elementAt(int row, int col) {
		RowElem currRow = rows;
		while (currRow != null) {
			if (currRow.getIndex() == row) { // row exists
				RowElem currCol = cols;
				while (currCol != null) {
					if (currCol.getIndex() == col) { // row and col exists,
														// check for the element
						MyMatrixElem elem = currRow.getElem();
						while (elem != null) {
							if (elem.columnIndex() == col) // found elem
								return elem.value();
							elem = elem.getNextCol();
						}
					}
					currCol = currCol.getNext();
				}
			}
			currRow = currRow.getNext();
		}
		return data;
	}

	/**
	 * Returns the number of rows in the sparse array.
	 */
	public int rowSize() {
		int count = 0;
		RowElem curr = rows;
		while (curr != null) {
			count++;
			curr = curr.getNext();

		}
		return count;
	}

	/**
	 * Used to add elements to the sparse array. If value is equal to the
	 * default value, will remove the element from the sparse array.
	 */
	@Override
	public void setValue(int row, int col, Object value) {
		if (rows == null || cols == null) { // sparse array is empty
			if (value.equals(data)) {
			} else {
				MyMatrixElem elem = new MyMatrixElem(value, null, null, row,
						col);
				rows = new RowElem(elem, row);
				cols = new RowElem(elem, col);
			}
		} else {
			RowElem prevRow = null;
			RowElem currRow = rows;
			while (currRow != null && currRow.getIndex() <= row) { // looking to
																	// see if
																	// row
																	// exists
				if (currRow.getIndex() == row) { // found the row
					RowElem prevCol = null;
					RowElem currCol = cols;
					MyMatrixElem currRowElem = currRow.getElem();
					MyMatrixElem prevRowElem = null;
					while (currCol != null && currCol.getIndex() <= col) { // looking
																			// to
																			// see
																			// if
																			// col
																			// exists
						if (currCol.getIndex() == col) { // found the row and
															// col
							while (currRowElem != null
									&& currRowElem.columnIndex() <= col) {
								if (currRowElem.columnIndex() == col) { // element
																		// exists,
																		// update
																		// value
									if (value.equals(data)) { // need to remove
																// element
										if (prevRowElem == null) { // removing
																	// first
																	// element
																	// of the
																	// row
											if (currRowElem.getNextCol() == null) { // only
																					// element
																					// in
																					// that
																					// row,
																					// remove
																					// row
												if (currRow.getNext() == null
														&& prevRow == null) { // only
																				// element
																				// in
																				// the
																				// sparse
																				// array
													rows = null; // reset sparse
																	// array to
																	// empty
													cols = null;
													return;
												} else if (currRow.getNext() != null
														&& prevRow == null) { // removing
																				// first
																				// row,
																				// replace
																				// rows
													rows = currRow.getNext();
													currRow.setNext(null);
													MyMatrixElem currColElem = currCol
															.getElem();
													MyMatrixElem prevColElem = null;
													while (currColElem != null) { // see
																					// how
																					// row
																					// removal
																					// alters
																					// column
														if (currColElem
																.rowIndex() == row) { // found
																						// index
															if (prevColElem == null
																	&& currColElem
																			.getNextRow() != null) { // removing
																										// first
																										// element
																										// in
																										// col
																currCol.setElem(currColElem
																		.getNextRow());
																currColElem = null;
																return;
															} else if (prevColElem == null
																	&& currColElem
																			.getNextRow() == null) { // removing
																										// col
																if (prevCol == null
																		&& currCol
																				.getNext() != null) { // removing
																										// first
																										// col
																	cols = currCol
																			.getNext();
																	currCol = null;
																	return;
																} else if (prevCol != null
																		&& currCol
																				.getNext() != null) { // removing
																										// middle
																										// col
																	prevCol.setNext(currCol
																			.getNext());
																	currCol = null;
																	return;
																}
															} else { // removing
																		// middle
																		// element
																		// in
																		// col
																prevColElem
																		.setNextRow(currColElem
																				.getNextRow());
																currColElem = null;
																return;
															}
														}
														currColElem = currColElem
																.getNextRow();
														prevColElem = currColElem;
													}
												} else { // removing a middle
															// row
													prevRow.setNext(currRow
															.getNext());
													currRow = null;
													MyMatrixElem currColElem = currCol
															.getElem();
													MyMatrixElem prevColElem = null;
													while (currColElem != null) { // check
																					// to
																					// see
																					// how
																					// column
																					// needs
																					// to
																					// be
																					// altered
																					// after
																					// row
																					// deletion
														if (currColElem
																.rowIndex() == row) { // found
																						// index
															if (prevColElem == null
																	&& currColElem
																			.getNextRow() == null) { // only
																										// element
																										// in
																										// col,
																										// remove
																										// col
																if (prevCol == null
																		&& currCol
																				.getNext() != null) { // removing
																										// first
																										// col
																	cols = currCol
																			.getNext();
																	currCol = null;
																	return;
																} else if (prevCol != null
																		&& currCol
																				.getNext() != null) { // removing
																										// middle
																										// column
																	prevCol.setNext(currCol
																			.getNext());
																	currCol = null;
																	return;
																}
															} else if (prevColElem == null
																	&& currColElem
																			.getNextRow() != null) { // removing
																										// first
																										// element
																										// in
																										// col
																currCol.setElem(currColElem
																		.getNextRow());
																currColElem = null;
																return;
															} else { // removing
																		// middle
																		// element
																		// in
																		// col
																prevColElem
																		.setNextRow(currColElem
																				.getNextRow());
																currColElem = null;
																return;
															}
														}
														currColElem = currColElem
																.getNextRow();
														prevColElem = currColElem;
													}
												}
											} else { // removing first element
														// in the row, row is
														// not deleted
												currRow.setElem(currRowElem
														.getNextCol());
												currRowElem = null;
												MyMatrixElem currColElem = currCol
														.getElem();
												MyMatrixElem prevColElem = null;
												while (currColElem != null) { // see
																				// how
																				// removing
																				// element
																				// affects
																				// column
													if (currColElem.rowIndex() == row) {
														if (prevColElem == null
																&& currColElem
																		.getNextRow() == null) { // delete
																									// col
															if (prevCol == null
																	&& currCol
																			.getNext() == null) { // only
																									// col
																									// in
																									// array,
																									// reset
																									// array
																rows = null;
																cols = null;
																return;
															} else if (prevCol == null
																	&& currCol
																			.getNext() != null) { // removing
																									// first
																									// col
																cols = currCol
																		.getNext();
																currCol = null;
																return;
															} else { // removing
																		// middle
																		// column
																prevCol.setNext(currCol
																		.getNext());
																currCol = null;
																return;
															}
														} else if (prevColElem == null
																&& currColElem
																		.getNextRow() != null) { // removing
																									// first
																									// elem
																									// in
																									// col
															currCol.setElem(currColElem
																	.getNextRow());
															currColElem = null;
															return;
														} else { // removing
																	// middle
																	// element
																	// in col
															prevColElem
																	.setNextRow(currColElem
																			.getNextRow());
															currColElem = null;
															return;
														}
													}
													prevColElem = currColElem;
													currColElem = currColElem
															.getNextRow();
												}
											}
										} else { // removing element in middle
													// of row
											prevRowElem.setNextCol(currRowElem
													.getNextCol());
											currRowElem = null;
											MyMatrixElem currColElem = currCol
													.getElem();
											MyMatrixElem prevColElem = null;
											while (currColElem != null) {
												if (currColElem.rowIndex() == row) { // check
																						// to
																						// see
																						// how
																						// removal
																						// affects
																						// col
													if (prevColElem == null
															&& currColElem
																	.getNextRow() == null) { // remove
																								// col
														if (prevCol == null
																&& currCol
																		.getNext() != null) { // remove
																								// first
																								// col
															cols = currCol
																	.getNext();
															currCol = null;
															return;
														} else { // remove
																	// middle
																	// col
															prevCol.setNext(currCol
																	.getNext());
															currCol = null;
															return;
														}
													} else if (prevColElem == null
															&& currColElem
																	.getNextRow() != null) { // remove
																								// first
																								// elem
																								// in
																								// col
														currCol.setElem(currColElem
																.getNextRow());
														currColElem = null;
														return;
													} else { // removing middle
																// element in
																// col
														prevColElem
																.setNextRow(currColElem
																		.getNextRow());
														currColElem = null;
														return;
													}
												}
												prevColElem = currColElem;
												currColElem = currColElem
														.getNextRow();
											}
										}
									} else {
										currRowElem.setData(value);
										return;
									}
								}
								prevRowElem = currRowElem;
								currRowElem = currRowElem.getNextCol();
							}
							if (value.equals(data)) { // element doesn't exist
														// and data is default,
														// do nothing
								return;
							} else {
								// got to here means elem doesn't exist, add it
								// to the row BEFORE currRowElem/Col!
								MyMatrixElem currColElem = currCol.getElem();
								MyMatrixElem prevColElem = null;
								while (currColElem != null
										&& currColElem.rowIndex() <= row) {
									prevColElem = currColElem;
									currColElem = currColElem.getNextRow();
								}
								if (prevRowElem == null && prevColElem == null) { // adding
																					// to
																					// beginning
																					// of
																					// the
																					// row
																					// and
																					// col
									MyMatrixElem elem = new MyMatrixElem(value,
											currColElem, currRowElem, row, col);
									currCol.setElem(elem);
									currRow.setElem(elem);
									return;
								}
								if (prevRowElem == null && prevColElem != null) { // adding
																					// to
																					// beginning
																					// of
																					// row,
																					// not
																					// col
									MyMatrixElem elem = new MyMatrixElem(value,
											currColElem, currRowElem, row, col);
									currRow.setElem(elem);
									prevColElem.setNextRow(elem);
									return;
								}
								if (prevRowElem != null && prevColElem == null) { // adding
																					// to
																					// beginning
																					// of
																					// col,
																					// not
																					// row
									MyMatrixElem elem = new MyMatrixElem(value,
											currColElem, currRowElem, row, col);
									currCol.setElem(elem);
									prevRowElem.setNextCol(elem);
									return;
								}
								if (prevRowElem != null && prevColElem != null) {
									MyMatrixElem elem = new MyMatrixElem(value,
											currColElem, currRowElem, row, col);
									prevRowElem.setNextCol(elem);
									prevColElem.setNextRow(elem);
									return;
								}
							}
						}
						prevCol = currCol;
						currCol = currCol.getNext();
					}
					if (value.equals(data)) {
						return;
					} else {
						// Got to here, col does NOT exist, create column and
						// add elem to row and new col
						while (currRowElem != null
								&& currRowElem.columnIndex() <= col) {
							prevRowElem = currRowElem;
							currRowElem = currRowElem.getNextCol();
						}
						if (prevRowElem == null) { // adding to beginning of the
													// row
							if (prevCol == null) { // adding in front of first
													// col
								MyMatrixElem elem = new MyMatrixElem(value,
										null, currRowElem, row, col);
								currRow.setElem(elem);
								RowElem newCol = new RowElem(elem, col);
								newCol.setNext(cols);
								cols = newCol;
								return;
							} else { // adding col in middle of the list
								MyMatrixElem elem = new MyMatrixElem(value,
										null, currRowElem, row, col);
								currRow.setElem(elem);
								RowElem newCol = new RowElem(elem, col);
								newCol.setNext(currCol);
								prevCol.setNext(newCol);
								return;
							}
						} else { // adding to the middle of the row
							if (prevCol == null) { // adding in front of first
													// col
								MyMatrixElem elem = new MyMatrixElem(value,
										null, currRowElem, row, col);
								prevRowElem.setNextCol(elem);
								RowElem newCol = new RowElem(elem, col);
								newCol.setNext(currCol);
								cols = newCol;
								return;
							} else { // adding in middle of cols
								MyMatrixElem elem = new MyMatrixElem(value,
										null, currRowElem, row, col);
								prevRowElem.setNextCol(elem);
								RowElem newCol = new RowElem(elem, col);
								newCol.setNext(currCol);
								prevCol.setNext(newCol);
								return;
							}
						}
					}
				}
				prevRow = currRow;
				currRow = currRow.getNext();
			}
			if (value.equals(data))
				return;
			// Got here, row does NOT exist, check to see if col exists and
			// create new row and/or col
			RowElem currCol = cols;
			RowElem prevCol = null;
			while (currCol != null && currCol.getIndex() <= col) {
				if (currCol.getIndex() == col) { // col exists, create new row
													// and insert elem into
													// currCol
					MyMatrixElem currColElem = currCol.getElem();
					MyMatrixElem prevColElem = null;
					while (currColElem != null && currColElem.rowIndex() <= row) {
						prevColElem = currColElem;
						currColElem = currColElem.getNextRow();
					}
					if (prevColElem == null) { // adding to beginning of col
						if (prevRow == null) { // adding row in front of first
												// row
							MyMatrixElem elem = new MyMatrixElem(value,
									currColElem, null, row, col);
							currCol.setElem(elem);
							RowElem newRow = new RowElem(elem, row);
							newRow.setNext(rows);
							rows = newRow;
							return;
						} else { // adding row to middle of rows
							MyMatrixElem elem = new MyMatrixElem(value,
									currColElem, null, row, col);
							currCol.setElem(elem);
							RowElem newRow = new RowElem(elem, row);
							newRow.setNext(currRow);
							prevRow.setNext(newRow);
							return;
						}
					} else { // adding to middle of col
						if (prevRow == null) { // adding in front of first row
							MyMatrixElem elem = new MyMatrixElem(value,
									currColElem, null, row, col);
							prevColElem.setNextRow(elem);
							RowElem newRow = new RowElem(elem, row);
							newRow.setNext(currRow);
							rows = newRow;
							return;
						} else { // adding to middle of list
							MyMatrixElem elem = new MyMatrixElem(value,
									currColElem, null, row, col);
							prevColElem.setNextRow(elem);
							RowElem newRow = new RowElem(elem, row);
							newRow.setNext(currRow);
							prevRow.setNext(newRow);
							return;
						}
					}
				}
				prevCol = currCol;
				currCol = currCol.getNext();
			}
			// Got here, row and column do not exist, create both and insert
			// element
			MyMatrixElem elem = new MyMatrixElem(value, null, null, row, col);
			RowElem newRow = new RowElem(elem, row);
			RowElem newCol = new RowElem(elem, col);
			if (prevRow == null && prevCol == null) { // in front of row and
														// col, replace cols and
														// rows
				newRow.setNext(currRow);
				newCol.setNext(currCol);
				cols = newCol;
				rows = newRow;
				return;
			}
			if (prevRow == null && prevCol != null) { // beginning of rows,
														// middle of cols
				newRow.setNext(currRow);
				newCol.setNext(currCol);
				prevCol.setNext(newCol);
				rows = newRow;
				return;
			}
			if (prevRow != null && prevCol == null) { // beginning of cols,
														// middle of rows
				newRow.setNext(currRow);
				newCol.setNext(currCol);
				prevRow.setNext(newRow);
				cols = newCol;
				return;
			}
			if (prevRow != null && prevCol != null) { // middle of rows and cols
				newRow.setNext(currRow);
				newCol.setNext(currCol);
				prevRow.setNext(newRow);
				prevCol.setNext(newCol);
				return;
			}

		}

	}

	/**
	 * Used to iterator through the rows of the sparse array.
	 *
	 */
	private class MyRowIterator extends RowIterator {
		private RowElem currNode = rows;
		private boolean first = true;

		@Override
		public ElemIterator next() {
			if (first) {
				first = false;
				MyElemIterator elemIterator = new MyElemIterator(true, false,
						currNode);
				return elemIterator;
			} else {
				currNode = currNode.getNext();
				MyElemIterator elemIterator = new MyElemIterator(true, false,
						currNode);
				return elemIterator;
			}
		}

		@Override
		public boolean hasNext() {
			if (first) {
				if (currNode == null)
					return false;
				return true;
			}
			if (currNode.getNext() == null)
				return false;
			return true;
		}
	}

	private class MyColIterator extends ColumnIterator {
		private RowElem currNode = cols;
		private boolean first = true;

		@Override
		public ElemIterator next() {
			if (first) {
				first = false;
				MyElemIterator elemIterator = new MyElemIterator(false, true,
						currNode);
				return elemIterator;
			}
			currNode = currNode.getNext();
			MyElemIterator elemIterator = new MyElemIterator(false, true,
					currNode);
			return elemIterator;
		}

		@Override
		public boolean hasNext() {
			if (first) {
				if (currNode == null)
					return false;
				return true;
			}
			if (currNode.getNext() == null)
				return false;
			return true;
		}

	}

	private class MyElemIterator extends ElemIterator {
		boolean iteratingRow;
		boolean iteratingCol;
		RowElem rowElem;
		MyMatrixElem currElem;
		boolean first;

		private MyElemIterator(boolean iteratingRow, boolean iteratingCol,
				RowElem rowElem) {
			this.iteratingRow = iteratingRow;
			this.iteratingCol = iteratingCol;
			this.rowElem = rowElem;
			currElem = rowElem.getElem();
			first = true;
		}

		@Override
		public boolean iteratingRow() {
			return iteratingRow;
		}

		@Override
		public boolean iteratingCol() {
			return iteratingCol;
		}

		@Override
		public int nonIteratingIndex() {
			return rowElem.getIndex();
		}

		@Override
		public MatrixElem next() {
			if (first) {
				first = false;
				return currElem;
			} else {
				if (iteratingRow)
					currElem = currElem.getNextCol();
				else
					currElem = currElem.getNextRow();
				return currElem;
			}
		}

		@Override
		public boolean hasNext() {
			if (first) {
				if (currElem == null)
					return false;
				return true;
			}
			if (iteratingRow) {
				if (currElem.getNextCol() == null) {
					return false;
				}
				return true;
			}
			if (iteratingCol) {
				if (currElem.getNextRow() == null) {
					return false;
				}
				return true;
			}
			return false;
		}
	}
}
