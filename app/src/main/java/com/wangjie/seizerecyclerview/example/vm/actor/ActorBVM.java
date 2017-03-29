package com.wangjie.seizerecyclerview.example.vm.actor;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class ActorBVM extends ActorVM{
    public ActorBVM(String obj) {
        super(obj);
    }

    @Override
    public int getActorViewType() {
        return TYPE_ACTOR_B;
    }
}
