package com.qaprosoft.zafira.dataProvider;

import com.qaprosoft.zafira.constant.ConstantName;
import com.qaprosoft.zafira.constant.JSONConstant;
import org.testng.annotations.DataProvider;

public class CapabilitiesManagerDataProvider {

    @DataProvider(name = "devise-name")
    public static Object[][] getDeviseNameProvider() {
        return new Object[][]{
                {JSONConstant.DEVICE_NAME_SLOT, ConstantName.DEVICE_NAME_IN_SLOT},
                {JSONConstant.DEVICE_MODEL_ACT, ConstantName.DEVICE_MODEL_IN_ACT_CAPS}};
    }

    @DataProvider(name = "platform-version")
    public static Object[][] getPlatformVersion() {
        return new Object[][]{
                {JSONConstant.PLATFORM_VERSION_SLOT, ConstantName.PLATFORM_VERSION_IN_SLOT},
                {JSONConstant.VERSION_SLOT, ConstantName.VERSION_IN_SLOT},
                {JSONConstant.PLATFORM_VERSION_ACT, ConstantName.PLATFORM_VERSION_IN_ACT_CAPS}};
    }

    @DataProvider(name = "platform-name")
    public static Object[][] getPlatformName() {
        return new Object[][]{
                {JSONConstant.PLATFORM_NAME_SLOT, ConstantName.PLATFORM_NAME_IN_SLOT},
                {JSONConstant.PLATFORM_NAME_DESIRED, ConstantName.PLATFORM_NAME_IN_DESIRED},
                {JSONConstant.PLATFORM_NAME_ACT, ConstantName.PLATFORM_NAME_IN_ACT_CAPS},
                {JSONConstant.PLATFORM_ACT, ConstantName.PLATFORM_IN_ACT_CAPS}};
    }

    @DataProvider(name = "browser-version")
    public static Object[][] getBrowserVersion() {
        return new Object[][]{
                {JSONConstant.ACTUAL_CAPS_BROWSER_VERSION, ConstantName.BROWSER_VERSION_IN_ACTUAL_CAPABILITY},
                {JSONConstant.ACTUAL_CAPS_VERSION, ConstantName.ACTUAL_CAPS_VERSION}};
    }

    @DataProvider(name = "browser-name")
    public static Object[][] getBrowserName() {
        return new Object[][]{
                {JSONConstant.ACTUAL_CAPS_BROWSER_NAME, ConstantName.BROWSER_NAME_IN_ACTUAL_CAPABILITY},
                {JSONConstant.DESIRED_CAPS_BROWSER_NAME, ConstantName.BROWSER_NAME_IN_DESIRED_CAPABILITY}};
    }

    @DataProvider(name = "session-name")
    public static Object[][] getSessionName() {
        return new Object[][]{
                {JSONConstant.ACTUAL_CAPS_NAME, ConstantName.NAME_IN_ACTUAL_CAPABILITY},
                {JSONConstant.ACTUAL_CAPS_SESSION_NAME, ConstantName.SESSION_NAME_IN_ACTUAL_CAPABILITY},
                {JSONConstant.DESIRED_CAPS_NAME, ConstantName.NAME_IN_DESIRED_CAPABILITY},
                {JSONConstant.DESIRED_CAPS_SESSION_NAME, ConstantName.SESSION_NAME_IN_DESIRED_CAPABILITY}};
    }

    @DataProvider(name = "session-artifact-references")
    public static Object[][] getArtifactReferences() {
        return new Object[][]{
                {"Log", JSONConstant.LOG_LINK, JSONConstant.ENABLE_LOG},
                {"Video", JSONConstant.VIDEO_LINK, JSONConstant.ENABLE_VIDEO},
                {"Metadata", JSONConstant.METADATA_LINK, JSONConstant.ENABLE_METADATA}};
    }

    @DataProvider(name = "session-artifact-references-to-check-link")
    public static Object[][] getArtifactReferenceLink() {
        return new Object[][]{
                {"Log", JSONConstant.ENABLE_LOG, "/session.log"},
                {"Video", JSONConstant.ENABLE_VIDEO, "/video.mp4"},
                {"Metadata", JSONConstant.ENABLE_METADATA, "/metadata.json"}};
    }
}
