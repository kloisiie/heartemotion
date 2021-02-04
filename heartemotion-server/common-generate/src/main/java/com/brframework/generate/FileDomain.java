package com.brframework.generate;

/**
 * @Author: ljr
 * @Date: 2019/10/21 15:20
 * @Description:
 */
public class FileDomain {

    /*
     id : Long
     createDate : DateTime
     //收藏的用户
     memeberId : Long
     //收藏类型 1.模板 2.单品 3.品牌
     type : Integer
     //被收藏对象的id
     colId : Long
    */

    //对象名 -> interflow
    String doMain;

    //对象名 -> 帖子
    String doMainName;

    //对象内容
    String content;

    public String getDoMain() {
        return doMain;
    }

    public void setDoMain(String doMain) {
        this.doMain = doMain;
    }

    public String getDoMainName() {
        return doMainName;
    }

    public void setDoMainName(String doMainName) {
        this.doMainName = doMainName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
