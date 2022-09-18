package com.desfate.acdcore.log

import android.app.Application
import android.os.Build
import com.elvishew.xlog.BuildConfig
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.flattener.ClassicFlattener
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.writer.SimpleWriter
import java.io.File

class LogDemandImpl: LogDemandInterface {
    override fun logInit(application: Application) {
        val file = File(
            application.externalCacheDir!!.absolutePath,
            "log"
        ).path
        val filePrinter: FilePrinter = FilePrinter.Builder(file)
            .backupStrategy(NeverBackupStrategy())
            .cleanStrategy(FileLastModifiedCleanStrategy(60 * 1000 * 24))
            .flattener(ClassicFlattener())
            .writer(object : SimpleWriter(){
                override fun onNewFileCreated(file: File?) {
                    super.onNewFileCreated(file)
                    val header = "\n>>>>>>>>>>>>>>>> File Header >>>>>>>>>>>>>>>>" +
                            "\nDevice Manufacturer: " + Build.MANUFACTURER +
                            "\nDevice Model       : " + Build.MODEL +
                            "\nAndroid Version    : " + Build.VERSION.RELEASE +
                            "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                            "\nApp VersionName    : " + BuildConfig.VERSION_NAME +
                            "\nApp VersionCode    : " + BuildConfig.VERSION_CODE +
                            "\n<<<<<<<<<<<<<<<< File Header <<<<<<<<<<<<<<<<\n\n";
                    appendLog(header);
                }
            })
            .build()
        XLog.init(LogLevel.ALL, AndroidPrinter(), filePrinter)
    }
}