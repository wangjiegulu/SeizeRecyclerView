package com.wangjie.seizerecyclerview.example.multitype.vm.comment;

import com.wangjie.seizerecyclerview.example.vm.comment.CommentVM;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class CommentBVM extends CommentVM {
    public CommentBVM(String obj) {
        super(obj);
    }

    @Override
    public int getCommentViewType() {
        return TYPE_COMMENT_B;
    }
}
