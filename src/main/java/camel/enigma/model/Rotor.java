package camel.enigma.model;

import camel.enigma.exception.ScramblerSettingException;
import camel.enigma.model.type.ScramblerType;
import camel.enigma.util.Util;

public class Rotor extends ScramblerWheel {

    public static final char[] DEFAULT_NOTCH = new char[]{'Q'};

    private int ringSetting;
    private char ringSettingAsChar;
    private char[] notch;

    public Rotor(
            String alphabetString,
            String wiringString,
            char[] notch,
            boolean staticc,
            ScramblerType scramblerType) throws ScramblerSettingException {

        super(alphabetString, wiringString, staticc, scramblerType);
        this.notch = notch;
    }

    // TODO update
//    void setRing(int ringSetting) {
//        for (int i = 0, wiringsLength = wirings.length; i < wiringsLength; i++) {
//            Wiring wiring = wirings[i];
//            int offsetPos = (i + ringSetting) % wiringsLength;
//            Wiring offsetWiring = wirings[offsetPos];
//            wiring.setTarget(offsetWiring.getTarget());
//        }
//    }

    public int getRingSetting() {
        return ringSetting;
    }

    public void setRingSetting(int ringSetting) {
        this.ringSetting = ringSetting;
    }

    public char getRingSettingAsChar() {
        return ringSettingAsChar;
    }

    public void setRingSettingAsChar(char ringSettingAsChar) {
        this.ringSettingAsChar = ringSettingAsChar;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean isNotchEngaged() {
        boolean result = false;
        if (notch != null) {
            result = Util.containsChar(notch, offsetAsChar);
        }
        return result;
    }

    public char[] getNotch() {
        return notch;
    }

    public void setNotch(char[] notch) {
        this.notch = notch;
    }
}
