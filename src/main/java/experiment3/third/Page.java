package experiment3.third;
// Since 2025/4/15 by CZ

public class Page {
    private int pageID;
    private boolean isInMemory;
    private int memoryID;
    private int locationOfDisk;
    private boolean isChanged;

    Page(int pageID, int locationOfDisk) {
        this.pageID = pageID;
        this.locationOfDisk = locationOfDisk;
        this.isInMemory = false;
        this.memoryID = -1;
        this.isChanged = false;
    }

    public String toString() {
        return pageID + "\t\t" + isChanged + "\t\t" + memoryID + "\t\t" + locationOfDisk;
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

    public boolean isInMemory() {
        if (memoryID == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int getMemoryID() {
        return memoryID;
    }

    public void setMemoryID(int memoryID) {
        this.memoryID = memoryID;
    }

    public int getLocationOfDisk() {
        return locationOfDisk;
    }

    public void setLocationOfDisk(int locationOfDisk) {
        this.locationOfDisk = locationOfDisk;
    }
}
