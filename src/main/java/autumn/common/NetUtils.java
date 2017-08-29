package autumn.common;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public final class NetUtils {
    private final static OkHttpClient client = new OkHttpClient();

    public static byte[] fetchImage(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().bytes();
    }

}
