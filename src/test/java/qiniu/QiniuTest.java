package qiniu;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;
import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Date: 14-8-27 2014
 * <p>Versioin: 1.0
 */
public class QiniuTest {
    public static final String ACCESS_KEY = "Ebylv_mE1CqaXQoZEI0iet8Det6nlpPenL0CH9Eg";
    public static final String SECRET_KEY = "M97Ect21ZnolvI5D9avBgvaWztzEMoR0ggJu1dM5";

    public static final String PHOTO_BUCKET = "gift-photo-space";
    public static final String VIDEO_BUCKET = "gift-video-space";

    @Test
    public void testShowResource() {
        Mac mac = new Mac(ACCESS_KEY,SECRET_KEY);

        RSFClient client = new RSFClient(mac);
        String marker = "";

        List<ListItem> all = new ArrayList<ListItem>();

        ListPrefixRet ret = null;

        while (true) {
            ret = client.listPrifix(PHOTO_BUCKET,"","",100);
            marker = ret.marker;

            all.addAll(ret.results);

            if (!ret.ok()) {
                break;
            }
        }

        if (ret.exception.getClass() != RSFEofException.class) {

        }

        all.forEach(item -> System.out.println(item.toString()));
    }

    public void testUploadResource() throws AuthException, JSONException {
        Mac mac = new Mac(ACCESS_KEY,SECRET_KEY);

        PutPolicy put = new PutPolicy(PHOTO_BUCKET);
        String uptoken = put.token(mac);

        PutExtra extra = new PutExtra();
        String key = "";
        String localFile = "";
        PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
    }
}
