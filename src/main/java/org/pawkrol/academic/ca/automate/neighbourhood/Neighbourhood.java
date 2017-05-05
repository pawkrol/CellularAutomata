package org.pawkrol.academic.ca.automate.neighbourhood;

import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;

import java.util.List;

/**
 * Created by pawkrol on 4/29/17.
 */
public abstract class Neighbourhood {
    public abstract List<Cell> neighbours(Grid grid, Cell cell);

    protected void addCellToList(List<Cell> list, Cell cell){
        if (cell != null){
            list.add(cell);
        }
    }
}
