package theSorcerer.util;

public class ElementAmount {

    private final int heated;
    
    private final int chilled;

    public ElementAmount(
            int heated,
            int chilled
    ) {
        this.heated = heated;
        this.chilled = chilled;
    }

    public static ElementAmount empty() {
        return new ElementAmount(0, 0);
    }

    public boolean hasAmount() {
        return isHeated() || isChilled();
    }

    public boolean isHeated() {
        return this.heated > 0;
    }

    public boolean isChilled() {
        return this.chilled > 0;
    }

    public int getAmount() {
        return Math.max(this.heated, this.chilled);
    }

    public int getHeated() {
        return this.heated;
    }

    public int getChilled() {
        return this.chilled;
    }
}
