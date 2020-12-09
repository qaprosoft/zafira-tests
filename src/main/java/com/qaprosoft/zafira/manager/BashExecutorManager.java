package com.qaprosoft.zafira.manager;

import com.qaprosoft.carina.core.foundation.utils.R;
import com.qaprosoft.zafira.constant.ConfigConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;

public class BashExecutorManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static BashExecutorManager instance;

    public static BashExecutorManager getInstance() {
        if (instance == null) {
            synchronized (BashExecutorManager.class) {
                if (instance == null) {
                    instance = new BashExecutorManager();
                }
            }
        }
        return instance;
    }

    private BashExecutorManager() {

    }

    public void initJenkinsMockServerWithData() {
        String scriptPath = "src/main/mock/jenkins_mock/initJenkinsMockServer.sh";
        makeScriptExecutableForAllUsersAndGroups(scriptPath);
        String absoluteScriptPath = new File(scriptPath).getAbsolutePath();
        String[] cmd = {"bash", absoluteScriptPath, R.CONFIG.get(ConfigConstant.JENKINS_MOCK_HOST_KEY)};
        Process p = null;
        exec(p, cmd);
    }

    private void makeScriptExecutableForAllUsersAndGroups(String scriptPath) {
        String absoluteScriptPath = new File(scriptPath).getAbsolutePath();
        String[] cmd = {"chmod", "a+x", absoluteScriptPath};
        Process p = null;
        exec(p, cmd);
    }

    private void exec(Process p, String[] cmd) {
        try {
            p = Runtime.getRuntime().exec(cmd);
            getProcessOutput(p);
        } catch (IOException e) {
            LOGGER.info(String.valueOf(e));
        }
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            LOGGER.info(String.valueOf(e));
        }
    }

    private void getProcessOutput(Process p) throws IOException {
        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        getOutFromReader(stdoutReader);
        getOutFromReader(stderrReader);
    }

    private static void getOutFromReader(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            LOGGER.info(line);
        }
    }


}