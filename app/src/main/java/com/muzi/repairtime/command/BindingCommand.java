package com.muzi.repairtime.command;

/**
 * 作者: lipeng
 * 时间: 2019/1/3
 * 邮箱: lipeng@moyi365.com
 * 功能: binding回调
 */
public class BindingCommand<T> {

    private BindingAction execute;
    private BindingConsumerAction<T> consumerAction;

    public BindingCommand(BindingAction execute) {
        this.execute = execute;
    }

    public BindingCommand(BindingConsumerAction<T> execute) {
        this.consumerAction = execute;
    }

    /**
     * 执行带泛型参数的命令
     *
     * @param parameter 泛型参数
     */
    public void execute(T parameter) {
        if (consumerAction != null) {
            consumerAction.call(parameter);
        }
    }


    /**
     * 执行BindingAction命令
     */
    public void execute() {
        if (execute != null) {
            execute.call();
        }
    }

}
