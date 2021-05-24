package com.qaprosoft.zafira.api;

import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.dashboard.widget.DeleteWidgetFromDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PostDashboardByEmailMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PostWidgetToDashboardMethod;
import com.qaprosoft.zafira.api.dashboard.widget.PutBatchOfWidgetsDashboardMethod;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.DashboardServiceImpl;
import com.qaprosoft.zafira.service.impl.WidgetServiceImpl;
import com.qaprosoft.zafira.util.CryptoUtil;
import com.qaprosoft.zafira.util.FileUtil;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DashboardWidgetTest extends ZafiraAPIBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger((ZafiraAPIBaseTest.class));
    private static WidgetServiceImpl widgetService = new WidgetServiceImpl();
    private int widgetTemplateId = widgetService.getAllWidgetTemplateIds().get(0);
    private final EmailManager EMAIL = new EmailManager(
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_USERNAME_KEY)),
            CryptoUtil.decrypt(R.TESTDATA.get(ConfigConstant.GMAIL_PASSWORD_KEY)));
    private static int widgetId;


    @AfterTest
    public void DeleteCreatedDashboards() {
        DashboardServiceImpl dashboardService = new DashboardServiceImpl();
        List<Integer> allDashboardsIds = dashboardService.gelAllDashboardsIds();
        for (int i = 6; i <= Collections.max(allDashboardsIds); ++i) {
            if (allDashboardsIds.contains(i)) {
                dashboardService.deleteDashboardById(i);
            }
        }
    }

    @AfterMethod
    public void testDeleteWidget() {
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        widgetService.deleteWidget(widgetId);
    }

    @Test
    public void testCreateWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName,widgetTemplateId).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = JsonPath.from(rs).get(JSONConstant.ID_KEY);
        PostWidgetToDashboardMethod postWidgetToDashboardMethod = new PostWidgetToDashboardMethod(rs, dashboardId);
        apiExecutor.expectStatus(postWidgetToDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postWidgetToDashboardMethod);
        apiExecutor.validateResponse(postWidgetToDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        LOGGER.info(widgetName);
    }

    @Test
    public void testUpdateBatchOfWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName,widgetTemplateId).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        PutBatchOfWidgetsDashboardMethod putBatchOfWidgetsDashboardMethod = new PutBatchOfWidgetsDashboardMethod(dashboardId, widgetId);
        apiExecutor.expectStatus(putBatchOfWidgetsDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putBatchOfWidgetsDashboardMethod);
        apiExecutor.validateResponse(putBatchOfWidgetsDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testDeleteWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName,widgetTemplateId).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        DeleteWidgetFromDashboardMethod deleteWidgetFromDashboardMethod = new DeleteWidgetFromDashboardMethod(dashboardId, widgetId);
        apiExecutor.expectStatus(deleteWidgetFromDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteWidgetFromDashboardMethod);
    }

    @Test
    public void testSendDashboardByEmailSmallPic() throws IOException {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName,widgetTemplateId).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        File uploadFile = new FileUtil().getFile(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY_PNG));
        String text = R.TESTDATA.get(ConfigConstant.EMAIL_KEY).replace("dashboardName", dashboardName);
        File emailFile = new FileUtil().getFile(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY_EMAIL));
        new FileUtil().createEmailFile(text);
        PostDashboardByEmailMethod postAssetMethod = new PostDashboardByEmailMethod(uploadFile, emailFile);
        apiExecutor.expectStatus(postAssetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postAssetMethod);
        verifyIfEmailWasDelivered(dashboardName);
        EMAIL.deleteMsg(dashboardName);
    }

    @Test
    public void testSendDashboardByEmailLargePic() throws IOException {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName,widgetTemplateId).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        File uploadFile =  new FileUtil().getFile(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY_PNG_LARGE));
        File emailFile = new FileUtil().getFile(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY_EMAIL));
        String text = R.TESTDATA.get(ConfigConstant.EMAIL_KEY).replace("dashboardName", dashboardName);
        new FileUtil().createEmailFile(text);
        PostDashboardByEmailMethod postAssetMethod = new PostDashboardByEmailMethod(uploadFile, emailFile);
        apiExecutor.expectStatus(postAssetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postAssetMethod);
        verifyIfEmailWasDelivered(dashboardName);
        EMAIL.deleteMsg(dashboardName);
    }

    private boolean verifyIfEmailWasDelivered(String dashboardName) {
        final int lastEmailIndex = 0;
        final int emailsCount = 1;
        LOGGER.info("Will get last email from inbox.");
        EMAIL.waitForEmailDelivered(new Date(), dashboardName); // decency from connection, wait a little bit
        EmailMsg email = EMAIL.getInbox(emailsCount)[lastEmailIndex];
        return email.getContent().contains(dashboardName);
    }

}


