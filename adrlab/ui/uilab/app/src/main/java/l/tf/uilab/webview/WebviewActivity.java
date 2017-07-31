package l.tf.uilab.webview;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import l.tf.uilab.R;

import java.io.*;

/**
 * @author ltf
 * @since 17/7/25, 下午4:22
 */
public class WebviewActivity extends Activity implements View.OnClickListener {

    WebView mWebView;
    Button mBtnGo;
    Button mBtnRefresh;
    EditText mEdtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        mWebView = (WebView) findViewById(R.id.web_view);
        mBtnGo = (Button) findViewById(R.id.webview_go);
        mBtnRefresh = (Button) findViewById(R.id.webview_refresh);
        mEdtUrl = (EditText) findViewById(R.id.webview_url);

        mWebView.getSettings().setJavaScriptEnabled(true);


        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl("http://10.2.45.241/");

        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });

        mBtnGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        mWebView.loadUrl("javascript:" + loadDockStr());

    }

    private String loadDockStr(){
        byte[] buf = new byte[1024];
        InputStream in = getResources().openRawResource(R.raw.dock);
        ByteArrayOutputStream bs = new ByteArrayOutputStream(1024);
        int i;
        try {
            while ((i = in.read(buf)) >= 0) {
                bs.write(buf, 0, i);
            }
        } catch (IOException e) {
        }
        return bs.toString();
    }
}
