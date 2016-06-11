package ltf.namerank.entity;


import javax.persistence.*;

/**
 * @author ltf
 * @since 5/27/16, 10:29 PM
 */
@Entity
@Table(name = "dict_bm8", indexes = {@Index(name = "idx_dict_bm8_kword",columnList = "kword")})
public class Hanzi {
    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getTraditional() {
        return traditional;
    }

    public void setTraditional(String traditional) {
        this.traditional = traditional;
    }

    public String getStrokes() {
        return strokes;
    }

    public void setStrokes(String strokes) {
        this.strokes = strokes;
    }

    public String getWuxing() {
        return wuxing;
    }

    public void setWuxing(String wuxing) {
        this.wuxing = wuxing;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getKword() {
        return kword;
    }

    public void setKword(String kword) {
        this.kword = kword;
    }

    public String getHtmid() {
        return htmid;
    }

    public void setHtmid(String htmid) {
        this.htmid = htmid;
    }

    public String getLuckyornot() {
        return luckyornot;
    }

    public void setLuckyornot(String luckyornot) {
        this.luckyornot = luckyornot;
    }

    @Column(columnDefinition = "nvarchar(12)")
    private String kword;

    @Id
    @Column(columnDefinition = "nvarchar(12)")
    private String htmid;

    @Column(columnDefinition = "nvarchar(36)")
    private String spell;

    @Column(columnDefinition = "nvarchar(12)")
    private String traditional;

    @Column(columnDefinition = "nvarchar(12)")
    private String strokes;

    @Column(columnDefinition = "nvarchar(12)")
    private String wuxing;

    @Column(columnDefinition = "nvarchar(12)")
    private String luckyornot;

    @Column(columnDefinition = "nvarchar(12)")
    private String comment;

    @Column(columnDefinition = "nvarchar(65535)")
    private String info;

    @Override
    public String toString() {
        return kword + "\t" +
                htmid + "\t" +
                spell + "\t" +
                traditional + "\t" +
                strokes + "\t" +
                wuxing + "\t" +
                luckyornot + "\t" +
                comment + "\t" +
                info + "\t";
    }
}
