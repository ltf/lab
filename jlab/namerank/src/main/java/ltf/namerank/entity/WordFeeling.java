package ltf.namerank.entity;

/**
 * @author ltf
 * @since 16/6/29, 下午4:31
 */
public class WordFeeling {
    //词语
    private String word;

    //词性种类
    //词性种类一共分为7类，
    // 分别是名词（noun），动词（verb），形容词（adj），副词（adv），网络词语（nw），成语（idiom），介词短语（prep）
    private String property;

    //词义数
    private Integer meaningCount;

    //词义序号
    private Integer meaningId;

    //情感分类
    //    编号	情感大类	    情感类	    例词
    //    1	    乐	        快乐(PA)	    喜悦、欢喜、笑眯眯、欢天喜地
    //    2	    	        安心(PE)	    踏实、宽心、定心丸、问心无愧
    //    3	    好	        尊敬(PD)	    恭敬、敬爱、毕恭毕敬、肃然起敬
    //    4	    	        赞扬(PH)	    英俊、优秀、通情达理、实事求是
    //    5	    	        相信(PG)	    信任、信赖、可靠、毋庸置疑
    //    6	    	        喜爱(PB)	    倾慕、宝贝、一见钟情、爱不释手
    //    7	    	        祝愿(PK)	    渴望、保佑、福寿绵长、万寿无疆
    //    8	    怒	        愤怒(NA)	    气愤、恼火、大发雷霆、七窍生烟
    //    9	    哀	        悲伤(NB)	    忧伤、悲苦、心如刀割、悲痛欲绝
    //   10	    	        失望(NJ)	    憾事、绝望、灰心丧气、心灰意冷
    //   11	    	        疚(NH)	    内疚、忏悔、过意不去、问心有愧
    //   12	    	        思(PF)	    思念、相思、牵肠挂肚、朝思暮想
    //   13	    惧	        慌(NI)	    慌张、心慌、不知所措、手忙脚乱
    //   14	    	        恐惧(NC)	    胆怯、害怕、担惊受怕、胆颤心惊
    //   15	    	        羞(NG)	    害羞、害臊、面红耳赤、无地自容
    //   16	    恶	        烦闷(NE)	    憋闷、烦躁、心烦意乱、自寻烦恼
    //   17	    	        憎恶(ND)	    反感、可耻、恨之入骨、深恶痛绝
    //   18	    	        贬责(NN)	    呆板、虚荣、杂乱无章、心狠手辣
    //   19	    	        妒忌(NK)	    眼红、吃醋、醋坛子、嫉贤妒能
    //   20	    	        怀疑(NL)	    多心、生疑、将信将疑、疑神疑鬼
    //   21	    惊	        惊奇(PC)	    奇怪、奇迹、大吃一惊、瞠目结舌
    private String category;

    //情感强度
    // 情感强度分为1,3,5,7,9五档，9表示强度最大，1为强度最小。
    private Integer level;

    //情感极性
    // 0代表中性，1代表褒义，2代表贬义，3代表兼有褒贬两性。
    // 注：褒贬标注时，通过词本身和情感共同确定，所以有些情感在一些词中可能极性1，而其他的词中有可能极性为0。
    private Integer polar;

    //辅助情感分类
    private String subCategory;
    //辅助情感强度
    private Integer subLevel;
    //辅助情感极性
    private Integer subPolar;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Integer getMeaningCount() {
        return meaningCount;
    }

    public void setMeaningCount(Integer meaningCount) {
        this.meaningCount = meaningCount;
    }

    public Integer getMeaningId() {
        return meaningId;
    }

    public void setMeaningId(Integer meaningId) {
        this.meaningId = meaningId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPolar() {
        return polar;
    }

    public void setPolar(Integer polar) {
        this.polar = polar;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getSubLevel() {
        return subLevel;
    }

    public void setSubLevel(Integer subLevel) {
        this.subLevel = subLevel;
    }

    public Integer getSubPolar() {
        return subPolar;
    }

    public void setSubPolar(Integer subPolar) {
        this.subPolar = subPolar;
    }
}
