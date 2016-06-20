package ltf.namerank.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author ltf
 * @since 16/6/20, 上午10:00
 */
public class CharUtils {

    /**
     * 随机生成一个汉字
     */
    //在0x4e00---0x9fa5之间产生一个随机的字符
    public static char getRandomHanzi()
    {
        return (char)(0x4e00+(int)(Math.random()*(0x9fa5-0x4e00+1)));
    }

    /**
     * 获取指定长度随机简体中文
     * @param len int
     * @return String
     */
    public static String getRandomJianHan(int len)
    {
        String ret="";
        for(int i=0;i<len;i++){
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try
            {
                str = new String(b, "GBk"); //转成中文
            }
            catch (UnsupportedEncodingException ex)
            {
                ex.printStackTrace();
            }
            ret+=str;
        }
        return ret;
    }
}
