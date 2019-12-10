package pers.xzhi;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

@Mojo(name = "script")
public class ScriptMojo extends AbstractMojo {

    private static final String APP_NAME_TEMPLATE = "#appName#";
    private static final String XMS_TEMPLATE = "#Xms#";
    private static final String XMX_TEMPLATE = "#Xmx#";

    @Parameter(defaultValue = "${project.artifactId}")
    private String appName;

    @Parameter(defaultValue = "32")
    private String xms;

    @Parameter(defaultValue = "64")
    private String xmx;

    @Parameter( defaultValue = "${project.build.directory}")
    private String outDir;

    @Parameter(defaultValue = "${project.version}")
    private String appVersion;
    
    private static final String BIN_PATH = "/bin";
    private static final String RESTART_STR = "/restart.sh";
    private static final String START_STR = "/start.sh";
    private static final String STOP_STR = "/stop.sh";
    private static final String START_BAT_STR = "/start.bat";

	private static final InputStream RESTART_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + RESTART_STR);
    private static final InputStream START_SH_IS =  ScriptMojo.class.getResourceAsStream(BIN_PATH + START_STR);
    private static final InputStream STOP_SH_IS =  ScriptMojo.class.getResourceAsStream(BIN_PATH + STOP_STR);
    private static final InputStream START_BAT_IS =  ScriptMojo.class.getResourceAsStream(BIN_PATH + START_BAT_STR);

    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("开始创建脚本……");

        String tarPath = outDir + File.separator + appName +"-"+ appVersion + File.separator + appName;

        String restartContent = IoUtil.read(RESTART_SH_IS, StandardCharsets.UTF_8);
        String startContent = IoUtil.read(START_SH_IS, StandardCharsets.UTF_8);
        String stopContent = IoUtil.read(STOP_SH_IS, StandardCharsets.UTF_8);
        String startBatContent = IoUtil.read(START_BAT_IS, StandardCharsets.UTF_8);

        startContent = startContent.replace(APP_NAME_TEMPLATE, appName)
                .replace(XMS_TEMPLATE, xms)
                .replace(XMX_TEMPLATE, xmx);
        restartContent = restartContent.replace(APP_NAME_TEMPLATE, appName);
        stopContent = stopContent.replace(APP_NAME_TEMPLATE, appName);
        startBatContent = startBatContent.replace(APP_NAME_TEMPLATE, appName);

        FileUtil.writeUtf8String(restartContent, FileUtil.touch(tarPath + RESTART_STR));
        FileUtil.writeUtf8String(startContent, FileUtil.touch(tarPath + START_STR));
        FileUtil.writeUtf8String(stopContent, FileUtil.touch(tarPath + STOP_STR));
        FileUtil.writeUtf8String(startBatContent, FileUtil.touch(tarPath + START_BAT_STR));

        System.out.println("创建脚本成功！");
    }
}
