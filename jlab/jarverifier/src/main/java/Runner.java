import com.sun.deploy.security.JarVerifier;
import sun.security.x509.X509CertImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateEncodingException;

public class Runner {
    public static void main(String[] args) throws IOException, CertificateEncodingException {
//        JarVerifier jarVerifier = JarVerifier.create(
//                new URL("file:///Users/f/tmp/lls/cert/app-debug.apk"),
//                null,
//                new File("/Users/f/tmp/lls/cert/app-debug.apk"),
//                new File("/Users/f/tmp/lls/cert")
//        );
//        JarVerifier jarVerifier = JarVerifier.create(
//                new URL("file:///Users/f/tmp/lls/cert/liulishuo.apk"),
//                null,
//                new File("/Users/f/tmp/lls/cert/liulishuo.apk"),
//                new File("/Users/f/tmp/lls/cert")
//        );
        JarVerifier jarVerifier = JarVerifier.create(
                new URL("file:///Users/f/tmp/inke/cert/inke5.1.10.apk"),
                null,
                new File("/Users/f/tmp/inke/cert/inke5.1.10.apk"),
                new File("/Users/f/tmp/inke/cert")
        );

        jarVerifier.validate(null);
        X509CertImpl cert = (X509CertImpl) jarVerifier.getSignerCerts().get(0);
        byte[] sigs = cert.getEncodedInternal();

        int n = 0;
        System.out.println("{");
        for (byte b : sigs) {
            n++;
            System.out.print(b);
            System.out.print(",");
            if (n % 50 == 0) System.out.println("");
        }
        System.out.println("}");
        System.out.println("");
        System.out.println(n);

    }
}
