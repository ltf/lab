package ltf.namerank.rank;

import java.util.function.Supplier;

/**
 * @author ltf
 * @since 7/9/16, 12:27 AM
 */
public class StrLogger implements RankLogger {

    private StringBuilder sb = new StringBuilder();

    @Override
    public void log(Supplier<String> supplier) {
        sb.append(supplier.get());
    }

    @Override
    public boolean skipCache() {
        return true;
    }

    public void clearLog(){
        sb.delete(0, sb.length());
    }

    public String getLog(){
        return sb.toString();
    }
}
