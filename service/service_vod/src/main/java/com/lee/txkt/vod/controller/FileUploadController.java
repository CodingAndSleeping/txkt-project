package com.lee.txkt.vod.controller;

import com.lee.txkt.result.Result;
import com.lee.txkt.vod.service.FileService;
import com.lee.txkt.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file")
@CrossOrigin
public class FileUploadController {
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public Result<?> upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        String uploadUrl = fileService.upload(file);
        return Result.ok(uploadUrl).message("文件上传成功");
    }
}