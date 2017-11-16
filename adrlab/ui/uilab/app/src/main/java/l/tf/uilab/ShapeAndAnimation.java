package l.tf.uilab;

import android.app.Activity;
import android.os.Bundle;

public class ShapeAndAnimation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_and_animation);
        findViewById(R.id.rounded_rectangle_bg_view).getBackground();
    }
}
