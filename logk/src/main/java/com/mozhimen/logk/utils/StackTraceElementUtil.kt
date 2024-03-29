package com.mozhimen.logk.utils

import com.mozhimen.basick.utilk.java.lang.UtilKStackTraceElement

/**
 * @ClassName StackTraceElementUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/29
 * @Version 1.0
 */
object StackTraceElementUtil {
    /**
     * 使用最大深度裁剪堆栈跟踪.
     * @param callStack Array<StackTraceElement?> 原始堆栈跟踪
     * @param maxDepth Int 将被裁剪的真实堆栈跟踪的最大深度, O表示没有限制
     * @return Array<StackTraceElement?> 裁剪后的堆栈跟踪
     */
    @JvmStatic
    fun gets_ofCropped(callStack: Array<StackTraceElement?>, maxDepth: Int): Array<StackTraceElement?> {
        var realDepth = callStack.size
        if (maxDepth > 0)
            realDepth = maxDepth.coerceAtMost(realDepth)
        val realStack = arrayOfNulls<StackTraceElement>(realDepth)
        System.arraycopy(callStack, 0, realStack, 0, realDepth)
        return realStack
    }

    /**
     * 获取真正的堆栈跟踪，所有来自XLog库的元素都将被删除
     * 获取除忽略包名之外的堆栈信息
     * @param stackTrace Array<StackTraceElement?> 完整的堆栈跟踪
     * @return Array<StackTraceElement?> 真正的堆栈跟踪, 所有元素都来自system和library user
     */
    @JvmStatic
    @Throws(Exception::class)
    fun gets_ofReal(stackTrace: Array<StackTraceElement?>, ignorePackage: String?): Array<StackTraceElement?> {
        var ignoreDepth = 0
        val allDepth = stackTrace.size
        require(allDepth > 0) { "${UtilKStackTraceElement.TAG} stackTrace's size is 0" }
        var className: String
        for (i in allDepth - 1 downTo 0) {
            className = stackTrace[i]!!.className
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1
                break
            }
        }
        val realDepth = allDepth - ignoreDepth
        val realStack = arrayOfNulls<StackTraceElement>(realDepth)
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth)
        return realStack
    }

    /**
     * 获取真正的堆栈跟踪，然后用最大深度裁剪它。
     * @param stackTrace Array<StackTraceElement?> 完整的堆栈跟踪
     * @param maxDepth Int 将被裁剪的真实堆栈跟踪的最大深度，O表示没有限制
     * @return Array<StackTraceElement?> 裁剪后的真实堆栈跟踪
     */
    @JvmStatic
    @Throws(Exception::class)
    fun gets_ofRealCropped(stackTrace: Array<StackTraceElement?>, ignoredPackage: String, maxDepth: Int): Array<StackTraceElement?> =
        gets_ofCropped(gets_ofReal(stackTrace, ignoredPackage), maxDepth)
}