package com.lee.txkt.vod.controller;

import com.lee.txkt.model.vod.Subject;
import com.lee.txkt.result.Result;
import com.lee.txkt.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "课程分类管理")
@RestController
@RequestMapping(value = "/admin/vod/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    //查询下一层课程分类
    //根据parent_id
    @ApiOperation("查询下一层的课程分类")
    @GetMapping("getChildSubject/{id}")
    public Result<?> getChildSubject(@PathVariable Long id) {
        List<Subject> list = subjectService.findChildSubject(id);
        return Result.ok(list);
    }

    @ApiOperation(value="导出")
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        subjectService.exportData(response);

    }


}
