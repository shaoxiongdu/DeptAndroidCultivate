package com.lixiang.sportsapp0831;

interface IGPSAidlInterface {

    /**
     * 获取今日运动距离
     * @return 单位：公里
     */
    double getDistanceByToday();

    /**
     * 重置今日运动步数
     */
    void resetDistance();
}
