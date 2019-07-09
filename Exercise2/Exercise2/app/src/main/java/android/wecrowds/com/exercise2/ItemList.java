package android.wecrowds.com.exercise2;

public class ItemList {
    private int id;
    private String title;
    private int hotValue;

    public ItemList(int id, String title, int hotValue){
        this.id = id;
        this.title = title;
        this.hotValue = hotValue;
    }

    public int getHotValue() {
        return hotValue;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHotValue(int hotValue) {
        this.hotValue = hotValue;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
