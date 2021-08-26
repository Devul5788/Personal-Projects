package firebasedemo.devul.recyclerview;

public class MyClass {

    public String id, name;
    public int img;
    public double price;
    public float qty;
    public String Thumb;

    public MyClass() {
    }

    public MyClass(String id, String name, int img, double price, float qt, String thumb) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }
}
