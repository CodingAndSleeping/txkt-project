package com.lee.txkt.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lee.txkt.model.vod.Teacher;
import com.lee.txkt.result.Result;
import com.lee.txkt.vo.vod.TeacherQueryVo;
import com.lee.txkt.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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


    @ApiOperation("条件分页查询")
    @PostMapping("findQuery/{page}/{pageSize}")
    public Result<?> findPage(@PathVariable long page, @PathVariable long pageSize, @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {

        System.out.println(teacherQueryVo);
        Page<Teacher> pageParam = new Page<>(page, pageSize);

        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();

        System.out.println(name);
        System.out.println(level);

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        wrapper.like(StringUtils.hasText(name), "name", name);
        wrapper.eq(level != null, "level", level);
        wrapper.ge(StringUtils.hasText(joinDateBegin), "join_Date", joinDateBegin);
        wrapper.le(StringUtils.hasText(joinDateEnd), "join_Date", joinDateEnd);

        Page<Teacher> teacherPage = teacherService.page(pageParam, wrapper);
        return Result.ok(teacherPage);

    }


    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result<?> saveTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.save(teacher);
        if (isSuccess) {
            return Result.ok().message("新增成功！");
        }
        return Result.fail().message("新增失败！");
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result<?> getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result<?> updateById(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        if (isSuccess) {
            return Result.ok().message("修改成功！");
        }
        return Result.fail().message("修改失败！");
    }


    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<?> batchRemove(@RequestBody List<Long> idList) {
        teacherService.removeByIds(idList);
        return Result.ok(null);
    }

}

