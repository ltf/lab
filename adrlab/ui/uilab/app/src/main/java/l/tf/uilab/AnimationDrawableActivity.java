package l.tf.uilab;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class AnimationDrawableActivity extends Activity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);


        mImageView = (ImageView) findViewById(R.id.list_animation_image_view);
        AnimationDrawable animaDrawable = (AnimationDrawable) mImageView.getDrawable();
        animaDrawable.start();
    }
}
