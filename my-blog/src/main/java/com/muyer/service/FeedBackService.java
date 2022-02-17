package com.muyer.service;

import com.muyer.common.utils.DataMap;
import com.muyer.model.FeedBack;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describe:反馈业务操作
 */
public interface FeedBackService {

    /**
     * 保存反馈信息
     * @param feedBack
     * @return
     */
    @Transactional
    void submitFeedback(FeedBack feedBack);

    /**
     * 获得所有的反馈
     * @return
     */
    DataMap getAllFeedback(int rows, int pageNum);

}
