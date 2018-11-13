package camel.enigma.model;

import camel.enigma.exception.ScramblerSettingException;
import camel.enigma.util.ScrambleResult;
import camel.enigma.util.Util;

public abstract class ScramblerWheel extends Scrambler {

    private int offset;
    char offsetAsChar;
    private final boolean staticc;

    ScramblerWheel(String alphabetString, String wiringString, boolean staticc, ScramblerType scramblerType)
        throws ScramblerSettingException {
        super(alphabetString, wiringString, scramblerType);
        offset = 0;
        offsetAsChar = alphabet[offset];
        this.staticc = staticc;
    }

    protected abstract boolean isNotchEngaged();

    boolean click() {
        boolean result = false;
        if (!staticc) {
            result = isNotchEngaged();
            offset = (offset == alphabet.length - 1) ? 0 : offset + 1;
            offsetAsChar = alphabet[offset];
        }
        return result;
    }

    @Override
    ScrambleResult scramble(ScrambleResult input) {
        int inputPos = input.getResult();
        int wrappedOffsetPos = (inputPos + offset) % alphabet.length;
        char wiringInput = alphabet[wrappedOffsetPos];
        int forwardLink = forwardLinks[wrappedOffsetPos];
        char wiringOutput = alphabet[forwardLink];
        int outputPos = (forwardLink - offset + alphabet.length) % alphabet.length;
        return input.putResult(outputPos, wiringInput, wiringOutput, wiringOutput, type.getName(), offset, offsetAsChar);
    }

    @Override
    ScrambleResult reverseScramble(ScrambleResult input) {
        int inputPos = input.getResult();
        int wrappedOffsetPos = (inputPos + offset) % alphabet.length;
        char wiringInput = alphabet[wrappedOffsetPos];
        int reverseLink = reverseLinks[wrappedOffsetPos];
        char wiringOutput = alphabet[reverseLink];
        int outputPos = (reverseLink - offset + alphabet.length) % alphabet.length;
        return input.putResult(outputPos, wiringInput, wiringOutput, wiringOutput, type.getName(), offset, offsetAsChar);
    }

    @Override
    void setWiring(String alphabetString, String wiringString) {
        this.forwardLinks = new int[alphabetString.length()];
        this.reverseLinks = new int[alphabetString.length()];
        char[] alphabetArray = alphabetString.toCharArray();
        char[] wiringArray = wiringString.toCharArray();
        for (int i = 0, alphabetArrayLength = alphabetArray.length; i < alphabetArrayLength; i++) {
            char target = wiringArray[i];
            char source = alphabetArray[i];
            int outputAddress = alphabetString.indexOf(target);
            int inputAddress = wiringString.indexOf(source);
            this.forwardLinks[i] = outputAddress;
            this.reverseLinks[i] = inputAddress;
        }

    }

    public void setOffset(char offset) {
        this.offsetAsChar = offset;
        int index = Util.indexOf(alphabet, offset);
        if (index != -1) {
            this.offset = index;
        } else {
            throw new IllegalArgumentException("invalid offset!");
        }
    }
}
