package ltf.namerank.rank.dictrank.meaning;

import ltf.namerank.rank.RankFilter;

import static ltf.namerank.rank.dictrank.support.Words.*;

/**
 * @author ltf
 * @since 7/14/16, 1:01 AM
 */
public class SameMeaningFilter implements RankFilter {


    @Override
    public boolean banned(String givenName) {

        return  false;
        //return isSame(givenName.charAt(0)+"", butySet)<0.92 ||isSame(givenName.charAt(1)+"", butySet)<0.92;

//        double max = 0;
//        double childRk;
//        childRk = isSame(givenName, butySet);
//        max = childRk > max ? childRk : max;
//        for(char c: givenName.toCharArray()){
//            childRk = isSame(c+"", butySet);
//            max = childRk > max ? childRk : max;
//        }
//
//        childRk = isSame(givenName, happySet);
//        max = childRk > max ? childRk : max;
//        for(char c: givenName.toCharArray()){
//            childRk = isSame(c+"", happySet);
//            max = childRk > max ? childRk : max;
//        }
//        return max<0.9;
    }
}
