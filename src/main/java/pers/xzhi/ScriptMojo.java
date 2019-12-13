package pers.xzhi;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

/**
 * 创建启动脚本
 */
@Mojo(name = "script", defaultPhase = LifecyclePhase.PACKAGE)
public class ScriptMojo extends AbstractMojo {

	private static final String APP_NAME_TEMPLATE = "#appName#";
	private static final String XMS_TEMPLATE = "#Xms#";
	private static final String XMX_TEMPLATE = "#Xmx#";

	/**
	 * 程序名称
	 */
	@Parameter(defaultValue = "${project.artifactId}")
	private String appName;
	/**
	 * JVM最小内存，默认32
	 */
	@Parameter(defaultValue = "32")
	private String xms;
	/**
	 * JVM最大内存，默认64
	 */
	@Parameter(defaultValue = "64")
	private String xmx;

	@Parameter(defaultValue = "${project.build.directory}", readonly = true)
	private String outDir;
	/**
	 * 程序版本
	 */
	@Parameter(defaultValue = "${project.version}")
	private String appVersion;
	/**
	 * 多个脚本是否合并成一个，默认一个
	 */
	@Parameter(defaultValue = "true")
	private boolean mergeShell = true;
	/**
	 * 脚本输出路径（顶级目录是target），默认路径：target/appName-appVersion/appName
	 */
	@Parameter
	private String shellOutputPath = "";

	private static final String BIN_PATH = "/bin";
	private static final String RESTART_STR = "/restart.sh";
	private static final String START_STR = "/start.sh";
	private static final String STOP_STR = "/stop.sh";
	private static final String START_BAT_STR = "/start.bat";
	private static final String MERGE_SH_STR = "/merge.sh";

	private static final InputStream RESTART_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + RESTART_STR);
	private static final InputStream START_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_STR);
	private static final InputStream STOP_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + STOP_STR);
	private static final InputStream START_BAT_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_BAT_STR);
	private static final InputStream MERGE_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + MERGE_SH_STR);

	public void execute() throws MojoExecutionException, MojoFailureException {
		System.out.println("开始创建脚本……");

		String tarPath = outDir + File.separator + appName + "-" + appVersion + File.separator + appName;

		if (!"".equals(shellOutputPath)) {
			tarPath = outDir + File.separator + shellOutputPath;
		}

		String restartContent = IoUtil.read(RESTART_SH_IS, StandardCharsets.UTF_8);
		String startContent = IoUtil.read(START_SH_IS, StandardCharsets.UTF_8);
		String stopContent = IoUtil.read(STOP_SH_IS, StandardCharsets.UTF_8);
		String startBatContent = IoUtil.read(START_BAT_IS, StandardCharsets.UTF_8);
		String mergeContent = IoUtil.read(MERGE_SH_IS, StandardCharsets.UTF_8);

        startContent = startContent.replace(APP_NAME_TEMPLATE, appName)
                .replace(XMS_TEMPLATE, xms)
                .replace(XMX_TEMPLATE, xmx);
        restartContent = restartContent.replace(APP_NAME_TEMPLATE, appName);
        stopContent = stopContent.replace(APP_NAME_TEMPLATE, appName);
        startBatContent = startBatContent.replace(APP_NAME_TEMPLATE, appName);
        mergeContent = mergeContent.replace(APP_NAME_TEMPLATE, appName)
                .replace(XMS_TEMPLATE, xms)
                .replace(XMX_TEMPLATE, xmx);

		if (mergeShell) {
			FileUtil.writeUtf8String(mergeContent, FileUtil.touch(tarPath + "/" + appName + ".sh"));
		} else {
			FileUtil.writeUtf8String(restartContent, FileUtil.touch(tarPath + RESTART_STR));
			FileUtil.writeUtf8String(startContent, FileUtil.touch(tarPath + START_STR));
			FileUtil.writeUtf8String(stopContent, FileUtil.touch(tarPath + STOP_STR));
		}
		FileUtil.writeUtf8String(startBatContent, FileUtil.touch(tarPath + START_BAT_STR));
		System.out.println("创建脚本成功！");
        System.out.println("脚本输出路径：" + tarPath);
    }
}
