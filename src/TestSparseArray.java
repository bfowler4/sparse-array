
public class TestSparseArray
{

    static int testData[][] = {{5,6,1},{5,10,2},{5,12,3}, {5, 4, 4}, {5, 11, 5},{5, 20, 6},
                               {1000, 1000, 7 }, {50000, 50000, 8}, {100000, 100000, 9},
                                {3, 6, 10},{10,6,11}, {20,6,12}, {1000, 3, 13 },
                                {1000, 5000, 14}, {1000, 500, 15}, {10000, 50000, 16},
                                {40, 50, 17}, {40, 60, 18}, {40, 90, 19}, {40, 70, 18},
                                {40, 100, 19}, {40, 200, 20}, {90, 90, 100}};
    
    
    
    
    public static boolean DeleteStressTest()
    {
        boolean passed = true;
        System.out.print("Delete Stress test");

        
        SparseArray testArray = new MySparseArray(0);
        
        for (int i = 0; i < 10000; i++)
        {
            for (int j = 0; j < 100; j++)
            {
                for (int k = 0; k < 100; k++)
                {
                    testArray.setValue(i, j*100 + k, i+j + k + 1);
                }
                for (int k = 0; k < 100; k++)
                {
                    testArray.setValue(i, j*100 + k, 0);
                }
            }
            if (i % 100 == 0)
            {
                System.out.print(".");
            }
        }
        System.out.println("");

        return passed;
    }
    
    public static boolean TestDefault()
    {
        boolean passed = true;
        System.out.println("Test Default Values");

        SparseArray defaultArray = new MySparseArray(new Integer(20));
        defaultArray.setValue(10, 10, 0);
        defaultArray.setValue(10, 20, 0);
        if (!defaultArray.elementAt(10, 10).equals(0))
        {
            System.out.println("Get / Set problem with value 0");
            passed = false;
        }
        if (!defaultArray.elementAt(5, 5).equals(20))
        {
            System.out.println("Default value not properly returned (Integer)");
            passed = false;

        }
        if (!defaultArray.elementAt(10, 15).equals(20))
        {
            System.out.println("Default value not properly returned (Integer)");
            passed = false;

        }
        if (!defaultArray.elementAt(25, 20).equals(20))
        {
            System.out.println("Default value not properly returned (Integer)");
            passed = false;

        }
        defaultArray = new MySparseArray("StringDefault");
        defaultArray.setValue(10, 10, "hey");
        defaultArray.setValue(10, 20, "there");
        if (!defaultArray.elementAt(10, 10).equals("hey"))
        {
            System.out.println("Get / Set problem with value 'hey'");
            passed = false;
        }
        if (!defaultArray.elementAt(10, 15).equals("StringDefault"))
        {
            System.out.println("Default value not properly returned (String)");
            passed = false;
        }


        if (passed)
        {   
            System.out.println("...Defualt Value passed");
        }
        else
        {
            System.out.println("Errors in Defualt Value");
        }
        
        return passed;
        
    }
    
    public static boolean TestIterators()
    {
        boolean passed = true;
        System.out.println("Testing Iterator");
   
        SparseArray iterTestArray = new MySparseArray(new Integer(0));

        
        for (int i = 0; i < 100; i++)
        {
           for (int j = 0; j < 100; j++)
           {
               iterTestArray.setValue(i,j,(i)*100 + j + 1);               
           }
        }
        
        RowIterator iterRow = iterTestArray.iterateRows();
        int row = 0;
        while (iterRow.hasNext())
        {
            ElemIterator elmIt = iterRow.next();
            int col = 0;
            while (elmIt.hasNext())
            {
                MatrixElem m = elmIt.next();
                if (m.rowIndex() != row || m.columnIndex() != col)
                {
                    System.out.println("Error in Row iterator:  expected (" + row + "," + col + "), got (" + m.rowIndex() + "," + m.columnIndex() + ")");                   
                }
                if (!m.value().equals(new Integer((m.rowIndex())*100+m.columnIndex() + 1)))
                {
                    System.out.println("Error in Elem iterator for (" + m.rowIndex() + "," + m.columnIndex() + ") " +
                                       "Expected " + Integer.toString((m.rowIndex() + 1)*100+m.columnIndex()) + ", got " +
                                       m.value());
                }
                col++;
            }
            row++;
        }
        
        ColumnIterator colIter = iterTestArray.iterateColumns();
        int col = 0;
        while (colIter.hasNext())
        {
            ElemIterator elmIt = colIter.next();
            row = 0;
            while (elmIt.hasNext())
            {
                MatrixElem m = elmIt.next();
                if (m.rowIndex() != row || m.columnIndex() != col)
                {
                    System.out.println("Error in iterator:  expected (" + row + "," + col + "), got (" + m.rowIndex() + "," + m.columnIndex() + ")");                   
                }
                if (!m.value().equals(new Integer((m.rowIndex())*100+m.columnIndex() + 1)))
                {
                    System.out.println("Error in iterator for (" + m.rowIndex() + "," + m.columnIndex() + ") " +
                                       "Expected " + Integer.toString((m.rowIndex() + 1)*100+m.columnIndex()) + ", got " +
                                       m.value());
                }
                row++;
            }
            col++;
        }

        for (int i = 20; i < 80; i++)
        {
            for (int j = 0; j < 100; j++)
            {
                
                iterTestArray.setValue(i, j, new Integer(0));

            }
        
        }
        
        
        iterRow = iterTestArray.iterateRows();
        
        int numRows = 0;
        while (iterRow.hasNext())
        {
            iterRow.next();
            numRows++;            
        }
        if (numRows != 40)
        {
            System.out.println("Something wrong with iterating rows.  Found " + numRows + " rows, was expecting 40.");
            passed = false;
        }
        
        if (passed)
        {   
            System.out.println("Test Iterators Passed");
        }
        else
        {
            System.out.println("Errors in Iterators");
        }
        return passed;
    }
    
    public static boolean TestSetGet(SparseArray testArray)
    {
        boolean testsPassed = true;
        System.out.println("Testing Get / Set");
        for (int i = 0; i < testData.length; i++)
        {
            testArray.setValue(testData[i][0], testData[i][1], testData[i][2]);
        }
        for (int i = 0; i < testData.length; i++)
        {
            if (!testArray.elementAt(testData[i][0],testData[i][1]).equals(new Integer(testData[i][2])))
            {
                System.out.println("Error at index " + testData[i][0] + ","  + testData[i][1]);
                System.out.println("   Should be " + testData[i][2] + ", is " + testArray.elementAt(testData[i][0],testData[i][1]));
                testsPassed = false;
            }
        }      
        
        if (testsPassed)
        {   
            System.out.println("...Get / Set passed");
        }
        else
        {
            System.out.println("Errors in Get / Set");
        }
        
        return testsPassed;
    }
    
    
    public static void main(String args[])
    {
 
        boolean passed = true;
        SparseArray testArray = new MySparseArray(new Integer(0));
        
        
        passed = passed && TestSetGet(testArray);
        passed = passed && TestDefault();
        passed = passed && TestIterators();
        passed = passed && DeleteStressTest();
        
        if (passed)
        {
            System.out.println("Passed All Tests");
            
        }
        else
        {
            System.out.println("Failed some test");
        }               
    }
    
}