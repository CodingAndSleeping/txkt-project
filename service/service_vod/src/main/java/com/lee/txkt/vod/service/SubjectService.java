package com.lee.txkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.txkt.model.vod.Subject;

import java.util.List;

public interface SubjectService extends IService<Subject> {
    List<Subject> findChildSubject(Long id);

}
