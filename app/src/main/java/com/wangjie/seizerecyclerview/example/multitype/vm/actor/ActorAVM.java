package com.wangjie.seizerecyclerview.example.multitype.vm.actor;

import com.wangjie.seizerecyclerview.example.vm.actor.ActorVM;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class ActorAVM extends ActorVM {
    public ActorAVM(String obj) {
        super(obj);
    }

    @Override
    public int getActorViewType() {
        return TYPE_ACTOR_A;
    }
}
