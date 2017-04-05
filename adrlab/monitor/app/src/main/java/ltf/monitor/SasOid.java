package ltf.monitor;

import android.util.Log;

/**
 * @author ltf
 * @since 17/4/3, 下午7:34
 */
public class SasOid implements  Target {
    private String mInitStr = null;
    @Override
    public String url() {
        return  "http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/applicants-international-skilled-workers/international-skilled-worker-occupations-in-demand";
    }

    @Override
    public boolean verify(String response) {
        if(response==null)  return false;
        if (mInitStr == null){
            mInitStr = response;
            return false;
        }
        return !mInitStr.equals(response);
        //Log.e("l","oid");
        //return response != null && response.hashCode() != 1084823173;
    }
}
