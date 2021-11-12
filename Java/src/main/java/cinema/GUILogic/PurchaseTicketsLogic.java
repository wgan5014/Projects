package cinema.GUILogic;

public class PurchaseTicketsLogic {

    private int front;
    private int frontSeating;
    private int frontCapacity;
    private int middle;
    private int middleSeating;
    private int middleCapacity;
    private int back;
    private int backSeating;
    private int backCapacity;
//    public PurchaseTicketsLogic(int front, int middle, int back) {
//        this.front = front;
//        this.middle = middle;
//        this.back = back;
//        this.frontCapacity = front;
//        this.backCapacity = back;
//        this.middleCapacity = middle;
//    }

    public PurchaseTicketsLogic() {
        this.front = 10;
        this.middle = 15;
        this.back = 20;
        this.frontCapacity = front;
        this.backCapacity = back;
        this.middleCapacity = middle;
        this.frontSeating = frontCapacity;
        this.middleSeating = middleCapacity;
        this.backSeating = backCapacity;
    }

    public int done() {
        int ticketsBook = frontSeating-front+ middleSeating-middle+ backSeating-back;
        frontSeating = front;
        middleSeating = middle;
        backSeating = back;

        return ticketsBook;
    }

    public void reset() {
        front = frontSeating;
        middle = middleSeating;
        back = backSeating;
    }
    public int getFrontSeating() {
        return frontSeating;
    }
    public int getMiddleSeating() {
        return middleSeating;
    }
    public int getBackSeating() {
        return backSeating;
    }
    public int addFront() {
        if(front != frontCapacity) {
            front+=1;
        }
        return front;
    }
    public int addMiddle() {
        if(middle != middleCapacity) {
            middle+=1;
        }
        return middle;
    }
    public int addBack() {
        if(back != backCapacity) {
            back+=1;
        }
        return back;
    }

    public int subFront() {
        if(front != 0) {
            front-=1;
        }
        return front;
    }

    public int subBack() {
        if(back != 0) {
            back-=1;
        }
        return back;
    }

    public int subMiddle() {
        if(middle != 0) {
            middle-=1;
        }
        return middle;
    }

    public int getFront() {
        return front;
    }

    public int getMiddle() {
        return middle;
    }

    public int getBack() {
        return back;
    }

    public boolean disableMinus(String seat) {
        if(seat == "Front") {
            return front == frontCapacity;
        }
        else if(seat == "Middle") {
            return middle == middleCapacity;
        }

        else if(seat == "Back") {
            return back == backCapacity;
        }
        return false;
    }

    public boolean disableAdd() {
        return front == 0 || back == 0 || middle == 0;
    }
}
