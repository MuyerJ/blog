package com.muyer.service;


import com.muyer.common.utils.DataMap;

/**
 * Describe: 归档业务操作
 */
public interface ArchiveService {

    /**
     * 获得归档信息
     * @return
     */
    DataMap findArchiveNameAndArticleNum();

    /**
     * 添加归档日期
     * @param archiveName
     */
    void addArchiveName(String archiveName);

}
