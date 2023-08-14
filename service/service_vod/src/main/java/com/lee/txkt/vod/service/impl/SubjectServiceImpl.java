package com.lee.txkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.txkt.model.vod.Subject;
import com.lee.txkt.vod.mapper.SubjectMapper;
import com.lee.txkt.vod.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public List<Subject> findChildSubject(Long id) {
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
}
