package l.tf.uilab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author ltf
 * @since 16/10/27, 下午1:51
 */
public class TestAidlAndService extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testaidlandservice_layout);


    }

    public void BtnBindServiceOnclick(View view) {
        ((Button) view).setText("hi");

        startService(new Intent());
        //bindService()

    }
}
