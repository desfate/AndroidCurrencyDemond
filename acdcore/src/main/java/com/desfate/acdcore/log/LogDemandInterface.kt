package com.desfate.acdcore.log

import android.app.Application

/**
 * 常见的日志需求
 *
 * 1.release版本需要记录日志
 * 2.通过读写文件实现落地
 * 3.手动/自动上传服务器
 *
 *
 */
interface LogDemandInterface {

    /**
     * 初始化日志模块
     */
    fun logInit(application: Application);



}