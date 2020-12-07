package com.qaprosoft.zafira.api;

import io.restassured.path.json.JsonPath;
import com.qaprosoft.apitools.validation.JsonCompareKeywords;
import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.api.dashboard.widget.*;
import com.qaprosoft.zafira.constant.ConfigConstant;
import com.qaprosoft.zafira.constant.JSONConstant;
import com.qaprosoft.zafira.domain.EmailMsg;
import com.qaprosoft.zafira.enums.HTTPStatusCodeType;
import com.qaprosoft.zafira.manager.EmailManager;
import com.qaprosoft.zafira.service.impl.DashboardServiceImpl;
import com.qaprosoft.zafira.service.impl.WidgetServiceImpl;
import com.qaprosoft.zafira.util.CryptoUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DashboardWidgetTest extends ZafiraAPIBaseTest {
    private final static Logger LOGGER = Logger.getLogger(DashboardWidgetTest.class);
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
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        PostWidgetToDashboardMethod postWidgetToDashboardMethod = new PostWidgetToDashboardMethod(rs, dashboardId);
        apiExecutor.expectStatus(postWidgetToDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postWidgetToDashboardMethod);
        apiExecutor.validateResponse(postWidgetToDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
        widgetId = JsonPath.from(rs).get(JSONConstant.ID_KEY);
    }

    @Test
    public void testUpdateWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        PutWidgetDashboardMethod putWidgetDashboardMethod = new PutWidgetDashboardMethod(dashboardId, widgetId);
        apiExecutor.expectStatus(putWidgetDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(putWidgetDashboardMethod);
        apiExecutor.validateResponse(putWidgetDashboardMethod, JSONCompareMode.STRICT, JsonCompareKeywords.ARRAY_CONTAINS.getKey());
    }

    @Test
    public void testUpdateBatchOfWidgetDashboard() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
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
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        DeleteWidgetFromDashboardMethod deleteWidgetFromDashboardMethod = new DeleteWidgetFromDashboardMethod(dashboardId, widgetId);
        apiExecutor.expectStatus(deleteWidgetFromDashboardMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(deleteWidgetFromDashboardMethod);
    }

    @Test
    public void testSendDashboardByEmailSmallPic() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY_PNG));
        String email = R.TESTDATA.get(ConfigConstant.EMAIL_KEY).replace("dashboardName", dashboardName);
        PostDashboardByEmailMethod postAssetMethod = new PostDashboardByEmailMethod(uploadFile, email);
        apiExecutor.expectStatus(postAssetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postAssetMethod);
        verifyIfEmailWasDelivered(dashboardName);
    }

    @Test
    public void testSendDashboardByEmailLargePic() {
        String dashboardName = "TestDashboard_".concat(RandomStringUtils.randomAlphabetic(15));
        int dashboardId = new DashboardServiceImpl().createDashboard(dashboardName);
        String widgetName = "TestWidget_".concat(RandomStringUtils.randomAlphabetic(15));
        WidgetServiceImpl widgetService = new WidgetServiceImpl();
        String rs = widgetService.createWidgetToDashboard(widgetName).replace("\"location\":null", "\"location\":\"location\"");
        widgetId = new DashboardServiceImpl().createWidgetToDashboard(rs, dashboardId);
        File uploadFile = new File(R.TESTDATA.get(ConfigConstant.IMAGE_PATH_KEY_PNG_LARGE));
        String email = R.TESTDATA.get(ConfigConstant.EMAIL_KEY).replace("dashboardName", dashboardName);
        PostDashboardByEmailMethod postAssetMethod = new PostDashboardByEmailMethod(uploadFile, email);
        apiExecutor.expectStatus(postAssetMethod, HTTPStatusCodeType.OK);
        apiExecutor.callApiMethod(postAssetMethod);
        verifyIfEmailWasDelivered(dashboardName);
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


