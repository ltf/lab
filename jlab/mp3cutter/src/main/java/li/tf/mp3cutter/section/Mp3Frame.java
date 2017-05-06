package li.tf.mp3cutter.section;

/**
 * MPEG Audio Frame Section (Header+Data)
 *
 * @author ltf
 * @see "http://mpgedit.org/mpgedit/mpeg_format/mpeghdr.htm"
 * @since 17/5/5, 上午10:15
 */
public class Mp3Frame extends Section {

    private static final int[][] bitrateTable = {
            {-1, -1, -1, -1, -1},
            {32, 32, 32, 32, 8},
            {64, 48, 40, 48, 16},
            {96, 56, 48, 56, 24},
            {128, 64, 56, 64, 32},
            {160, 80, 64, 80, 40},
            {192, 96, 80, 96, 48},
            {224, 112, 96, 112, 56},
            {256, 128, 112, 128, 64},
            {288, 160, 128, 144, 80},
            {320, 192, 160, 160, 96},
            {352, 224, 192, 176, 112},
            {384, 256, 224, 192, 128},
            {416, 320, 256, 224, 144},
            {448, 384, 320, 256, 160},
            {-1, -1, -1, -1, -1}
    };
    private static final int[][] sampleRateTable = {
            {44100, 22050, 11025},
            {48000, 24000, 12000},
            {32000, 16000, 8000},
            {-1, -1, -1}
    };
    protected double timeLength;  // time length in second
    private int versionID;
    private int layerID;
    private boolean hasCrc;
    private int bitrateID;
    private int sampleRateID;
    private boolean padded;

    public double getTimeLength() {
        return timeLength;
    }

    @Override
    protected boolean parseHeadData(byte[] head) {
        if (((head[1] >> 5) & 0x7) == 0) return false; // check Frame sync last 3 bits

        versionID = ((head[1] >> 3) & 0x3);
        layerID = ((head[1] >> 1) & 0x3);
        hasCrc = (head[1] & 0x1) == 0;

        bitrateID = ((head[2] >> 4) & 0xF);
        sampleRateID = ((head[2] >> 2) & 0x3);
        padded = ((head[2] >> 1) & 0x1) == 1;

        int bitRate = getBitrate(bitrateID, versionID, layerID);
        int sampleRate = getSampleRate(sampleRateID, versionID);
        if (bitRate < 0 || sampleRate < 0) return false;

        timeLength = calculateTimeLength(layerID, sampleRate);
        length = calculateLength(bitRate, sampleRate, layerID, hasCrc, padded);
        return true;
    }


    /**
     * get bit rate, or -1 if not support or error
     */
    private int getBitrate(int bitrateID, int versionID, int layerID) {
        if (bitrateID == 0 || bitrateID == 0xF) return -1; //not support Bitrate index
        int vl = -1;
        if (versionID == 3) {  // V1
            switch (layerID) {
                case 1:
                    vl = 2;
                    break;
                case 2:
                    vl = 1;
                    break;
                case 3:
                    vl = 0;
                    break;
            }
        } else if (versionID == 2) {  // V2
            switch (layerID) {
                case 1:
                case 2:
                    vl = 4;
                    break;
                case 3:
                    vl = 3;
                    break;
            }
        }

        return vl < 0 ? -1 : bitrateTable[bitrateID][vl] * 1000;
    }

    /**
     * get sample rate, or -1 if not support or error
     */
    private int getSampleRate(int sampleRateID, int versionID) {
        int v = -1;
        switch (versionID) {
            case 0:
                v = 2;
                break;
            case 2:
                v = 1;
                break;
            case 3:
                v = 0;
                break;
        }
        return v < 0 ? -1 : sampleRateTable[sampleRateID][v];
    }

    /**
     * calculate Frame Section length, include the head data
     */
    private int calculateLength(int bitRate, int sampleRate, int layerID,
                                boolean hasCrc, boolean padded) {
        int len = 4; // 4byte header
        if (hasCrc) len += 2;  // CRC
        int padding = 0;
        if (padded) {
            switch (layerID) {
                case 1:
                case 2:
                    padding = 4;
                    break;
                case 3:
                    padding = 1;
                    break;
            }
        }

        switch (layerID) {
            case 1:
            case 2:
                len += (12 * bitRate / sampleRate + padding) * 4;
                break;
            case 3:
                len += 144 * bitRate / sampleRate + padding;
                break;
        }

        return len;
    }

    private double calculateTimeLength(int layerID, int sampleRate) {
        double len = 0;
        switch (layerID) {
            case 1:
            case 2:
                len = 1152.0 / sampleRate;
                break;
            case 3:
                len = 384.0 / sampleRate;
                break;
        }
        return len;
    }

    @Override
    protected byte[] getIdentifier() {
        return new byte[]{(byte) 0xff};
    }
}
