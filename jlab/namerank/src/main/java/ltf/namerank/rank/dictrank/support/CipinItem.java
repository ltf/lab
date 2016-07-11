package ltf.namerank.rank.dictrank.support;

/**
 * @author ltf
 * @since 16/7/11, 下午4:20
 */
public class CipinItem {
    // 现代汉语常用词表词频
    public Integer gj;   // 词级

    // 兰卡斯特大学汉语词频
    public Integer xj;   // 词级
    public Integer xc;   // 词次

    // 人民日报词频
    public Integer bc;   // 词次
    public Integer bg;   // 结构词次

    public Integer getBg() {
        return bg;
    }

    public void setBg(Integer bg) {
        this.bg = bg;
    }

    public Integer getBc() {
        return bc;
    }

    public void setBc(Integer bc) {
        this.bc = bc;
    }

    public Integer getXc() {
        return xc;
    }

    public void setXc(Integer xc) {
        this.xc = xc;
    }

    public Integer getXj() {
        return xj;
    }

    public void setXj(Integer xj) {
        this.xj = xj;
    }

    public Integer getGj() {
        return gj;
    }

    public void setGj(Integer gj) {
        this.gj = gj;
    }
}
