package ltf.monitor;

import android.util.Log;

/**
 * @author ltf
 * @since 17/4/3, 下午7:34
 */
public class SasMn implements  Target {
    @Override
    public String url() {
        return "http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/maximum-number-of-sinp-applications";
    }

    @Override
    public boolean verify(String response) {
        Log.e("l","mn");

        return response != null &&  response.hashCode() != -1682924287;
    }
}
