package com.wangjie.seizerecyclerview.example.vm.comment;

import com.wangjie.seizerecyclerview.example.vm.VM;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class CommentVM extends VM<String> {
    public static final int TYPE_COMMENT = 20;
    public static final int TYPE_COMMENT_A = 21;
    public static final int TYPE_COMMENT_B = 22;

    public CommentVM(String obj) {
        super(obj);
    }

    @Override
    public int getViewType() {
        return TYPE_COMMENT;
    }

    public int getCommentViewType() {
        return TYPE_COMMENT_A;
    }
}
