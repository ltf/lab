package ltf.namerank.rank.dictrank.support.dict;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.sun.istack.internal.NotNull;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.WordExistChecker;
import ltf.namerank.rank.dictrank.support.Cipin;
import ltf.namerank.utils.Rtc;

import java.util.*;

import static ltf.namerank.rank.RankItemHelper.addInfo;
import static ltf.namerank.rank.dictrank.support.Words.*;
import static ltf.namerank.utils.PathUtils.getRawHome;
import static ltf.namerank.utils.StrUtils.existsCount;

/**
 * @author ltf
 * @since 16/6/30, 下午3:30
 */
public class HanYuDaCidian extends MdxtDict implements WordExistChecker {
    @Override
    protected String getFileName() {
        return getRawHome() + "/mdx/汉语大词典简体精排.txt";
    }


    @Override
    public String getName() {
        return "汉语大词典";
    }

    @Override
    protected MdxtItem newItem(String key) {
        return new HanyuDacidianItem(key);
    }

    @Override
    public boolean exists(String word) {
        initItems();
        return itemsMap.containsKey(word);
    }

    private static class HanyuDacidianItem extends MdxtItem {

        private static final String prefix = ".&nbsp;`3`&nbsp;";
        private static final String suffix = "`7`";
        private String prefix2;
        private List<String> explains = new ArrayList<>();

        HanyuDacidianItem(String key) {
            super(key);
            //System.out.println(key);
            prefix2 = "`1`" + key + "`6`<br>";
        }

        @Override
        protected void addValue(String valueLine) {
            super.addValue(valueLine);
            if (valueLine == null)
                return;
            else if (valueLine.contains(prefix)) {
                int from = valueLine.indexOf(prefix) + prefix.length();
                int to = valueLine.indexOf(suffix, from);
                explains.add(valueLine.substring(from, to));
            } else if (valueLine.startsWith(prefix2)) {
                int from = valueLine.indexOf(prefix) + prefix.length();
                int to = valueLine.indexOf(suffix, from);
                explains.add(valueLine.substring(prefix2.length()));
            }
        }

        @Override
        protected void finishAdd() {
            super.finishAdd();
        }

        @Override
        protected boolean isValid() {
            return super.isValid();
        }

        @Override
        public double rank(@NotNull RankItem target) {
            Rtc.begin();
            String means = "";
            Set<String> words = new HashSet<>();
            for (String explain : explains) {
                means += explain;
                segment(explain, words);
            }

            double rk = target.getScore();
            double delta;
            for (String word : words) {
                delta = 0;
                double rate = Math.sqrt(Cipin.get(word));
                if (positiveSet.contains(word)) {
                    delta += 1 * rate;
                }
                if (negativeSet.contains(word)) {
                    delta -= 5 * rate;
                }
                if (butySet.contains(word)) {
                    delta += 5 * rate;
                }
                if (RankSettings.reportMode) addInfo(word).append(String.format("%.1f", delta));
                rk += delta;
            }

            if (RankSettings.reportMode) addInfo("\n");

            StringBuilder infoBuilder = null;
            if (RankSettings.reportMode) infoBuilder = new StringBuilder();
            double childRk = existsRank(means, positiveSet, infoBuilder);
            if (childRk > 0) {
                rk += childRk;
                if (RankSettings.reportMode) addInfo(String.format("P1x%.1f:%s; ", childRk, infoBuilder.toString()));
            }


            childRk = existsRank(means, negativeSet, infoBuilder) * (-5);
            if (childRk > 0) {
                rk += childRk * (-5);
                if (RankSettings.reportMode) addInfo(String.format("N5x%.1f:%s; ", childRk, infoBuilder.toString()));
            }

            childRk = existsRank(means, butySet, infoBuilder) * 5;
            if (childRk > 0) {
                rk += childRk * 5;
                if (RankSettings.reportMode) addInfo(String.format("B5x%.1f:%s; ", childRk, infoBuilder.toString()));
            }

            if (RankSettings.reportMode) addInfo("\n");
            if (RankSettings.reportMode) addInfo(means);
            if (RankSettings.reportMode) addInfo("\n");

            Rtc.end();
            return rk;
        }

        /**
         * same count words string
         */
        private static String[] countWords = new String[101];

        /**
         * rank content with exists count methods
         *
         * @param content     content to rank
         * @param words       keywords set
         * @param infoBuilder infoBuilder, is null will don't generate info
         */
        private static double existsRank(String content, Collection<String> words, StringBuilder infoBuilder) {
            Rtc.begin();
            double rk = 0;

            if (infoBuilder != null) {
                for (int i = 0; i < countWords.length; i++)
                    countWords[i] = "";

                infoBuilder.delete(0, infoBuilder.length());
            }

            for (String word : words) {
                int count = existsCount(content, word);
                if (count > 0) {
                    if (infoBuilder != null) {
                        if (countWords[count] != null)
                            countWords[count] += ",";
                        countWords[count] += word;
                    }
                    rk += Math.sqrt(count * Cipin.get(word));
                }
            }

            if (infoBuilder != null) {
                for (int i = countWords.length - 1; i > 0; i--) {
                    if (!"".equals(countWords[i])) {
                        infoBuilder.append(i).append(":").append(countWords[i]).append("; ");
                    }
                }
            }
            Rtc.end();
            return rk;
        }

        private void segment(String sentence, Collection<String> out) {
            //System.out.println("\t\t\t\t" + sentence);
            sentence = cleanLink(sentence);
            //System.out.println("\t\t\t\t" + sentence);
            pickTerms(StandardTokenizer.segment(sentence), out);
            //System.out.println("\t\t\t\t" + StandardTokenizer.segment(sentence));
        }

        private String cleanLink(String sentence) {
            if (sentence == null) return "";
            return sentence.replaceAll("<a href=\"entry://.{0,9}?/\">", "")
                    .replaceAll("参见“", "")
                    .replaceAll("通“", "")
                    .replaceAll("如：", "")
                    .replaceAll("</a>”", "");
        }

        private void pickTerms(Collection<Term> terms, Collection<String> out) {
            for (Term term : terms) {
                switch (term.nature) {
                    //case bg:// 区别语素
                    //case mg:// 数语素
                    case nl:// 名词性惯用语
                        //case nx:// 字母专名
                        //case qg:// 量词语素
                        //case ud:// 助词
                        //case uj:// 助词
                        //case uz:// 着
                        //case ug:// 过
                        //case ul:// 连词
                        //case uv:// 连词
                        //case yg:// 语气语素
                        //case zg:// 状态词
                    case n:// 名词
                    case nr:// 人名
                    case nrj:// 日语人名
                    case nrf:// 音译人名
                    case nr1:// 复姓
                    case nr2:// 蒙古姓名
                    case ns:// 地名
                    case nsf:// 音译地名
                    case nt:// 机构团体名
                    case ntc:// 公司名
                    case ntcf:// 工厂
                    case ntcb:// 银行
                    case ntch:// 酒店宾馆
                    case nto:// 政府机构
                    case ntu:// 大学
                    case nts:// 中小学
                    case nth:// 医院
                    case nh:// 医药疾病等健康相关名词
                    case nhm:// 药品
                    case nhd:// 疾病
                        //case nn:// 工作相关名词
                        //case nnt:// 职务职称
                    case nnd:// 职业
                    case ng:// 名词性语素
                    case nf:// 食品，比如“薯片”
                    case ni:// 机构相关（不是独立机构名）
                    case nit:// 教育相关机构
                    case nic:// 下属机构
                        //case nis:// 机构后缀
                    case nm:// 物品名
                    case nmc:// 化学品名
                    case nb:// 生物名
                    case nba:// 动物名
                    case nbc:// 动物纲目
                    case nbp:// 植物名
                    case nz:// 其他专名
                    case g:// 学术词汇
                    case gm:// 数学相关词汇
                    case gp:// 物理相关词汇
                    case gc:// 化学相关词汇
                    case gb:// 生物相关词汇
                    case gbc:// 生物类别
                    case gg:// 地理地质相关词汇
                    case gi:// 计算机相关词汇
                    case j:// 简称略语
                    case i:// 成语
                    case l:// 习用语
                        //case t:// 时间词
                        //case tg:// 时间词性语素
                        //case s:// 处所词
                        //case f:// 方位词
                    case v:// 动词
                        //case vd:// 副动词
                    case vn:// 名动词
                        //case vshi:// 动词“是”
                        //case vyou:// 动词“有”
                        //case vf:// 趋向动词
                        //case vx:// 形式动词
                        //case vi:// 不及物动词（内动词）
                        //case vl:// 动词性惯用语
                        //case vg:// 动词性语素
                    case a:// 形容词
                    case ad:// 副形词
                    case an:// 名形词
                    case ag:// 形容词性语素
                    case al:// 形容词性惯用语
                    case b:// 区别词
                    case bl:// 区别词性惯用语
                        //case z:// 状态词
                        //case r:// 代词
                        //case rr:// 人称代词
                        //case rz:// 指示代词
                        //case rzt:// 时间指示代词
                        //case rzs:// 处所指示代词
                        //case rzv:// 谓词性指示代词
                        //case ry:// 疑问代词
                        //case ryt:// 时间疑问代词
                        //case rys:// 处所疑问代词
                        //case ryv:// 谓词性疑问代词
                        //case rg:// 代词性语素
                        //case Rg:// 古汉语代词性语素
                        //case m:// 数词
                        //case mq:// 数量词
                        //case Mg:// 甲乙丙丁之类的数词
                        //case q:// 量词
                        //case qv:// 动量词
                        //case qt:// 时量词
                    case d:// 副词
                    case dg:// 辄,俱,复之类的副词
                        //case dl:// 连语
                        //case p:// 介词
                        //case pba:// 介词“把”
                        //case pbei:// 介词“被”
                        //case c:// 连词
                        //case cc:// 并列连词
                        //case u:// 助词
                        //case uzhe:// 着
                        //case ule:// 了 喽
                        //case uguo:// 过
                        //case ude1:// 的 底
                        //case ude2:// 地
                        //case ude3:// 得
                        //case usuo:// 所
                        //case udeng:// 等 等等 云云
                        //case uyy:// 一样 一般 似的 般
                        //case udh:// 的话
                        //case uls:// 来讲 来说 而言 说来
                        //case uzhi:// 之
                        //case ulian:// 连 （“连小学生都会”）
                    case e:// 叹词
                    case y:// 语气词(delete yg)
                        //case o:// 拟声词
                        //case h:// 前缀
                        //case k:// 后缀
                        //case x:// 字符串
                        //case xx:// 非语素字
                        //case xu:// 网址URL
                        //case w:// 标点符号
                        //case wkz:// 左括号，全角：（ 〔  ［  ｛  《 【  〖 〈   半角：( [ {
                        //case wky:// 右括号，全角：） 〕  ］ ｝ 》  】 〗 〉 半角： ) ] { >
                        //case wyz:// 左引号，全角：“ ‘ 『
                        //case wyy:// 右引号，全角：” ’ 』
                        //case wj:// 句号，全角：。
                        //case ww:// 问号，全角：？ 半角：?
                        //case wt:// 叹号，全角：！ 半角：!
                        //case wd:// 逗号，全角：， 半角：,
                        //case wf:// 分号，全角：； 半角： ;
                        //case wn:// 顿号，全角：、
                        //case wm:// 冒号，全角：： 半角： :
                        //case ws:// 省略号，全角：……  …
                        //case wp:// 破折号，全角：——   －－   ——－   半角：---  ----
                        //case wb:// 百分号千分号，全角：％ ‰   半角：%
                        //case wh:// 单位符号，全角：￥ ＄ ￡  °  ℃  半角：$
                        out.add(term.word);
                        break;
                    default:

                }
            }
        }

    }
}
