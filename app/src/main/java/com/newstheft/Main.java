package com.newstheft;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage
{
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable
    {
        if (!lpparam.packageName.equals("com.google.android.apps.magazines"))
            return;

        Class<?> edition = XposedHelpers.findClass("com.google.apps.dots.android.newsstand.edition.Edition", lpparam.classLoader);

        XposedHelpers.findAndHookMethod("com.google.apps.dots.android.newsstand.edition.LibrarySnapshot", lpparam.classLoader, "isPurchased", edition, new XC_MethodReplacement()
        {
            @Override
            protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable
            {
                // Where the magic happens
                return true;
            }
        });

        XposedBridge.log("All your news are belong to us");
    }
}