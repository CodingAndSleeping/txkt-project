package com.lee.txkt.vod.controller;


import com.lee.txkt.model.vod.Teacher;
import com.lee.txkt.result.Result;
import com.lee.txkt.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lee
 * @since 2023-07-29
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询所有讲师")
    @GetMapping("findAll")
    public Result<List<Teacher>> findAll() {
        return Result.ok(teacherService.list()).message("查询所有讲师成功！");
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result<?> removeTeacher(@ApiParam(name = "id", value = "ID", required = true) @PathVariable Long id) {
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess) {
            return Result.ok().message("删除成功！");
        }
        return Result.fail().message("删除失败！");
    }

}

