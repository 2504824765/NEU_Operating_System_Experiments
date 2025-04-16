package experiment3.third;

// Since 2025/4/15 by CZ
public class Command {
    private int pageID;
    private int unitNumber;
    private boolean isChanged;

    public Command(int pageID, int unitNumber, int isChanged) {
        this.pageID = pageID;
        this.unitNumber = unitNumber;
        this.isChanged = isChanged == 1;
    }

    @Override
    public String toString() {
        return "(" + pageID + ", " + unitNumber + " " + isChanged + ")";
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }
}
