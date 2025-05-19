package org.blyx.jna;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public interface User32Ext extends StdCallLibrary {
    User32Ext INSTANCE = Native.load("user32", User32Ext.class);

    // 设置窗口位置和大小
    boolean SetWindowPos(WinDef.HWND hWnd, WinDef.HWND hWndInsertAfter,
                         int X, int Y, int cx, int cy, int uFlags);

    // 窗口标志常量
    int SWP_NOMOVE = 0x0002;
    int SWP_NOZORDER = 0x0004;
    WinDef.HWND HWND_TOP = null;
}
