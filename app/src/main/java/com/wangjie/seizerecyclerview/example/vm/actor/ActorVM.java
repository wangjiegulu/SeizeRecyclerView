package com.wangjie.seizerecyclerview.example.vm.actor;

import com.wangjie.seizerecyclerview.example.vm.VM;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class ActorVM extends VM<String> {
    public static final int TYPE_ACTOR = 10;
    public static final int TYPE_ACTOR_A = 11;
    public static final int TYPE_ACTOR_B = 12;

    public ActorVM(String obj) {
        super(obj);
    }

    @Override
    public int getViewType() {
        return TYPE_ACTOR;
    }

    public int getActorViewType() {
        return TYPE_ACTOR_A;
    }
}
