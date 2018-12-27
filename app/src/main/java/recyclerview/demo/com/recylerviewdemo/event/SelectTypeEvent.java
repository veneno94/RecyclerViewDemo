package recyclerview.demo.com.recylerviewdemo.event;

public class SelectTypeEvent {
    private int type;

    public SelectTypeEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
