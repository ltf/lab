package ltf.namerank.utils;

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
}
