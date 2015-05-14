package cz.bbmri.entity.temp;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class Money {

    private int youGaveMe;
    private int andIGiveYou;

    public Money(int youGaveMe, int andIGiveYou) {
        this.youGaveMe = youGaveMe;
        this.andIGiveYou = andIGiveYou;
    }

    public int getYouGaveMe() {
        return youGaveMe;
    }

    public void setYouGaveMe(int youGaveMe) {
        this.youGaveMe = youGaveMe;
    }

    public int getAndIGiveYou() {
        return andIGiveYou;
    }

    public void setAndIGiveYou(int andIGiveYou) {
        this.andIGiveYou = andIGiveYou;
    }
}
