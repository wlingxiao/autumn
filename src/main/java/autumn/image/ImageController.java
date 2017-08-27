package autumn.image;

import autumn.common.NetUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final static String BASE_URL = "https://pic2.zhimg.com/";

    @ResponseBody
    @RequestMapping("/{imageId}")
    public ResponseEntity<byte[]> loadImage(@PathVariable("imageId") String imageId) throws IOException {
        return ResponseEntity.ok(NetUtils.fetchImage(BASE_URL + imageId));
    }
}
