package com.wangjie.seizerecyclerview.example.vm.comment;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class CommentAVM extends CommentVM{
    public CommentAVM(String obj) {
        super(obj);
    }

    @Override
    public int getCommentViewType() {
        return TYPE_COMMENT_A;
    }
}
