package hk.hku.cs.shijian.vis.controller;

import hk.hku.cs.shijian.vis.entity.response.CommonResponse;
import hk.hku.cs.shijian.vis.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SimpleUploadController {

    private FileUploadService service;

    @Autowired
    public SimpleUploadController(FileUploadService service) {
        this.service = service;
    }

    @RequestMapping("/simple_upload")
    @CrossOrigin
    public CommonResponse simpleUpload(HttpServletRequest request,
                                       MultipartHttpServletRequest multiReq) {
        CommonResponse response = service.storeUploadedFile(request, multiReq, "");

        return response;
    }

}
