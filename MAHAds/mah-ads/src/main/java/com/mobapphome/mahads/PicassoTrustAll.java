package com.mobapphome.mahads;
//
//import android.content.Context;
//import android.net.Uri;
//import android.util.Log;
//
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.picasso.OkHttpDownloader;
//import com.squareup.picasso.Picasso;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
///**
// * Created by settar on 12/17/16.
// */
//
//
//public class PicassoTrustAll {
//
//    private static Picasso mInstance = null;
//
//    private PicassoTrustAll(Context context) {
//        OkHttpClient client = new OkHttpClient();
//        client.setHostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String s, SSLSession sslSession) {
//                return true;
//            }
//        });
//        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//            @Override
//            public void checkClientTrusted(
//                    java.security.cert.X509Certificate[] x509Certificates,
//                    String s) throws java.security.cert.CertificateException {
//            }
//
//            @Override
//            public void checkServerTrusted(
//                    java.security.cert.X509Certificate[] x509Certificates,
//                    String s) throws java.security.cert.CertificateException {
//            }
//
//            @Override
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return new java.security.cert.X509Certificate[]{};
//            }
//        }};
//        try {
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            client.setSslSocketFactory(sc.getSocketFactory());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mInstance = new Picasso.Builder(context)
//                .downloader(new OkHttpDownloader(client))
//                .listener(new Picasso.Listener() {
//                    @Override
//                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                        Log.e("PICASSO", exception.getMessage());
//                    }
//                }).build();
//
//    }
//
//    public static Picasso getInstance(Context context) {
//        if (mInstance == null) {
//            new PicassoTrustAll(context);
//        }
//        return mInstance;
//    }
//}