package com.muyer.controller;

import com.muyer.common.utils.DataMap;
import com.muyer.common.utils.JsonResult;
import com.muyer.common.utils.StringUtil;
import com.muyer.common.utils.TransCodingUtil;
import com.muyer.model.CodeType;
import com.muyer.service.ArticleService;
import com.muyer.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Describe: 分类
 */
@RestController
@Slf4j
public class CategoriesController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;

    /**
     * 获得所有分类名以及每个分类名的文章数目
     * @return
     */
    @GetMapping(value = "/findCategoriesNameAndArticleNum", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String findCategoriesNameAndArticleNum(){
        try {
            DataMap data = categoryService.findCategoriesNameAndArticleNum();
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Find categories name and article num exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 分页获得该分类下的文章
     * @return
     */
    @GetMapping(value = "/getCategoryArticle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getCategoryArticle(@RequestParam("category") String category,
                                     @RequestParam("rows") int rows,
                                     @RequestParam("pageNum") int pageNum){
        try {
            if(!category.equals(StringUtil.BLANK)){
                category = TransCodingUtil.unicodeToString(category);
            }
            DataMap data = articleService.findArticleByCategory(category, rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get category [{}] article exception", category, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


}
