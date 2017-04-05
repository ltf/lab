package ltf.monitor;

import android.util.Log;

/**
 * @author ltf
 * @since 17/4/3, 下午7:34
 */
public class SasMn implements  Target {

    private String mInitStr = null;
    @Override
    public String url() {
        return "http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/maximum-number-of-sinp-applications";
    }

    @Override
    public boolean verify(String response) {
        if(response==null)  return false;
        if (mInitStr == null){
            mInitStr = response;
            return false;
        }
//        int iBegin = response.indexOf(">Express Entry<");
//        int iEnd = response.indexOf( "</tbody>",iBegin);
//        if (iEnd>iBegin){
//            String s = response.substring(iBegin, iEnd);
//            int x = response.hashCode();
//            //Log.e("",""+x);
//            return x != 1073054175;
//        }
        return !mInitStr.equals(response);
    }
}
