package com.muzi.repairtime.richeditor.interfaces;


import com.muzi.repairtime.richeditor.ActionType;

/**
 * OnActionPerformListener
 * Created by even.wu on 17/8/17.
 */

public interface OnActionPerformListener {
    void onActionPerform(ActionType type, Object... values);
}
