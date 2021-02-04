package cn.bluetech.gragas.web.admin;

import cn.bluetech.gragas.json.admin.sys.SystemUploadResult;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.brframework.commonweb.json.JSONResult;
import com.brframework.commonweb.utils.ServletUtils;
import com.brframework.log.pojo.LogMessageRemoveConfigDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 系统模块
 * @author xu
 * @date 2020/12/24 15:50
 */
@RestController
@Api(tags = "系统模块")
public class AdminSystemController {


    @Value(value = "${fs.url-prefix:}")
    private String prefix;

    public static final String FILE_BASE = System.getProperty("user.home") + File.separatorChar + "fs";

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping("/fs/upload")
    public JSONResult<SystemUploadResult> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        HttpServletRequest request = ServletUtils.request();
        String fileKey = IdUtil.simpleUUID() +
                "/" + multipartFile.getOriginalFilename();

        File file = new File(FILE_BASE + "/" + fileKey);
        file.mkdirs();

        multipartFile.transferTo(file);

        SystemUploadResult result = new SystemUploadResult();

        String url = request.getScheme() +"://" + request.getServerName()
                + ":" +request.getServerPort()
                + request.getServletPath();

        result.setUrl(url.replace("/fs/upload", "") + prefix + "/fs/" + fileKey);

        return JSONResult.ok(result);
    }


    @ApiOperation(value = "文件下载", notes = "文件下载")
    @GetMapping("/fs/{prefix}/{fileName}")
    public void download(@PathVariable String prefix, @PathVariable String fileName)
            throws NotFoundException, IOException {
        File file = new File(FILE_BASE + "/" + prefix + "/" + fileName);
        if(!file.exists()){
            throw new NotFoundException("不存在该文件");
        }

        HttpServletResponse httpServletResponse = ServletUtils.response();
        //开始到处excel文件
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        httpServletResponse.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名
        httpServletResponse.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName));
        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[10240];
            int len;
            while ((len = inputStream.read(bytes)) > 0){
                outputStream.write(bytes, 0, len);
            }
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
