package com.lee.txkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.txkt.model.vod.Subject;
import com.lee.txkt.vo.vod.SubjectEeVo;
import com.lee.txkt.vod.mapper.SubjectMapper;
import com.lee.txkt.vod.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public List<Subject> findChildSubject(Long id) {

        if (id == null) {
            List<Subject> subjectList = this.list(null );
            // 判断对象是否有子节点
            for (Subject subject : subjectList) {
                boolean hasChildren = this.hasChildren(subject.getId());
                subject.setHasChildren(hasChildren);
            }
            return subjectList;
        }
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Subject> subjectList = this.list(queryWrapper);
        // 判断对象是否有子节点
        for (Subject subject : subjectList) {
            boolean hasChildren = this.hasChildren(subject.getId());
            subject.setHasChildren(hasChildren);
        }
        return subjectList;
    }

    // 判断对象下面是否有子节点
    public boolean hasChildren(Long id) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        int count = this.count(queryWrapper);
        return count > 0;
    }


    /**
     * 课程分类导出
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<Subject> dictList = this.findChildSubject(null);
            List<SubjectEeVo> dictVoList = new ArrayList<>(dictList.size());
            for (Subject dict : dictList) {
                SubjectEeVo dictVo = new SubjectEeVo();
                BeanUtils.copyProperties(dict, dictVo);
                dictVoList.add(dictVo);
            }
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
