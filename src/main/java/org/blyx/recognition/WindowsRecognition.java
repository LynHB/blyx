package org.blyx.recognition;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 识别浏览器窗口
 *
 * @author 59921
 */
public class WindowsRecognition implements Recognition {

    public static void main(String[] args) {
        Recognition recognition = new WindowsRecognition();
        recognition.recognitie();
    }

    @Override
    public WinDef.HWND recognitie() {
        try {
            // 直接使用JNA预定义的User32接口
            User32 user32 = User32.INSTANCE;

            // 查找Chrome窗口
            AtomicReference<WinDef.HWND> chromeWindow = new AtomicReference<>();
            user32.EnumWindows((hWnd, arg1) -> {
                char[] windowText = new char[512];
                user32.GetWindowText(hWnd, windowText, 512);
                String wText = com.sun.jna.Native.toString(windowText);

                if (!wText.isEmpty() && wText.contains("Chrome")) {
                    chromeWindow.set(hWnd);
                    return false; // 停止枚举
                }
                return true;
            }, null);

            if (chromeWindow.get() != null) {
                // 激活窗口
                user32.SetForegroundWindow(chromeWindow.get());

                // 获取窗口位置
                WinDef.RECT rect = new WinDef.RECT();
                user32.GetWindowRect(chromeWindow.get(), rect);
                int x = rect.left;
                int y = rect.top;
                int width = rect.right - rect.left;
                int height = rect.bottom - rect.top;
                // 设置窗口大小
//                User32Ext.INSTANCE.SetWindowPos(
//                        chromeWindow.get(),
//                        User32Ext.HWND_TOP,
//                        0, 0, 800, 600,
//                        User32Ext.SWP_NOMOVE | User32Ext.SWP_NOZORDER
//                );
                System.out.println("Chrome窗口位置: " + x + ", " + y + ", 尺寸: " + width + "x" + height);
            }
            return chromeWindow.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
