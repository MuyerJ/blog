package com.muyer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.muyer.common.utils.DataMap;
import com.muyer.mapper.ArticleLikesMapper;
import com.muyer.model.ArticleLikesRecord;
import com.muyer.service.ArticleLikesRecordService;
import com.muyer.service.ArticleService;
import com.muyer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Describe:
 */
@Service
@Slf4j
public class ArticleLikesRecordServiceImpl implements ArticleLikesRecordService {

    @Autowired
    ArticleLikesMapper articleLikesMapper;
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    @Override
    public boolean isLiked(long articleId, String username) {
        ArticleLikesRecord articleLikesRecord = articleLikesMapper.isLiked(articleId, userService.findIdByUsername(username));

        return articleLikesRecord != null;
    }

    @Override
    public void insertArticleLikesRecord(ArticleLikesRecord articleLikesRecord) {
        articleLikesMapper.save(articleLikesRecord);
    }

    @Override
    public void deleteArticleLikesRecordByArticleId(long articleId) {
        articleLikesMapper.deleteArticleLikesRecordByArticleId(articleId);
    }

    @Override
    public DataMap getArticleThumbsUp(int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();

        PageHelper.startPage(pageNum, rows);
        List<ArticleLikesRecord> likesRecords = articleLikesMapper.getArticleThumbsUp();
        PageInfo<ArticleLikesRecord> pageInfo = new PageInfo<>(likesRecords);
        JSONArray returnJsonArray = new JSONArray();
        JSONObject articleLikesJson;
        for(ArticleLikesRecord a : likesRecords){
            articleLikesJson = new JSONObject();
            articleLikesJson.put("id", a.getId());
            articleLikesJson.put("articleId", a.getArticleId());
            articleLikesJson.put("likeDate", a.getLikeDate());
            articleLikesJson.put("praisePeople", userService.findUsernameById(a.getLikerId()));
            articleLikesJson.put("articleTitle", articleService.findArticleTitleByArticleId(a.getArticleId()).get("articleTitle"));
            articleLikesJson.put("isRead", a.getIsRead());
            returnJsonArray.add(articleLikesJson);
        }
        returnJson.put("result", returnJsonArray);
        returnJson.put("msgIsNotReadNum",articleLikesMapper.countIsReadNum());

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJson.put("pageInfo",pageJson);

        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap readThisThumbsUp(int id) {
        articleLikesMapper.readThisThumbsUp(id);
//        stringRedisService.stringIncrement(StringUtil.ARTICLE_THUMBS_UP,-1);
//        int articleThumbsUp = (int) stringRedisService.get(StringUtil.ARTICLE_THUMBS_UP);
//        if(articleThumbsUp == 0){
//            stringRedisService.remove(StringUtil.ARTICLE_THUMBS_UP);
//        }
        return DataMap.success();
    }

    @Override
    public DataMap readAllThumbsUp() {
        articleLikesMapper.readAllThumbsUp();
//        stringRedisService.remove(StringUtil.ARTICLE_THUMBS_UP);
        return DataMap.success();
    }

}
