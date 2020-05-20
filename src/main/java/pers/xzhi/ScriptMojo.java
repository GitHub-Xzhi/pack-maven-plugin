package pers.xzhi;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
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
	private static final String DEPENDENCYLIBLIST_TEMPLATE = "#dependencyLibList#";
	private static final String MAINCLASS_TEMPLATE = "#mainClass#";

	/**
	 * 依赖包相对路径
	 */
	private String libRelativePath;
	/**
	 * libList-win路径
	 */
	private static final String  LIBLIST_WIN_PATH = "/libList/libList-win";
	/**
	 * libList-linux路径
	 */
	private static final String  LIBLIST_LINUX_PATH = "/libList/libList-linux";
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
	/**
	 * 输出根目录
	 */
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
	private String shellOutputPath;
	/**
	 * 依赖包路径
	 */
	@Parameter
	private String dependencyPath;
	/**
	 * 启动类列表
	 */
	@Parameter
	private List<String> mainClasses;
	/**
	 * classpath前缀
	 */
	@Parameter
	private String classpathPrefix;

	private static final String BIN_PATH = "/bin";
	private static final String RESTART_STR = "/restart.sh";
	private static final String START_STR = "/start.sh";
	private static final String STOP_STR = "/stop.sh";
	private static final String START_BAT_STR = "/start.bat";
	private static final String MERGE_SH_STR = "/merge.sh";
	private static final String MERGE_LIB_SH_STR = "/merge-lib.sh";
	private static final String START_LIB_BAT_STR = "/start-lib.bat";

	private static final InputStream RESTART_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + RESTART_STR);
	private static final InputStream START_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_STR);
	private static final InputStream STOP_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + STOP_STR);
	private static final InputStream START_BAT_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_BAT_STR);
	private static final InputStream MERGE_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + MERGE_SH_STR);

	public void execute() throws MojoExecutionException, MojoFailureException {
		System.out.println("开始创建脚本……");

		String tarPath = outDir + File.separator + appName + "-" + appVersion + File.separator + appName;

		if (StringUtils.isNotBlank(shellOutputPath)) {
			tarPath = outDir + File.separator + shellOutputPath;
		}
		
		if (StringUtils.isBlank(dependencyPath)) {
			inlayDependencyLib(tarPath);
		} else {
			externalDependencyLib(tarPath);
		}

		System.out.println("创建脚本成功！");
		System.out.println("脚本输出路径：" + tarPath);

	}

	/**
	 * 内置依赖包
	 */
	private void inlayDependencyLib(String tarPath) {
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
	}

	/**
	 * 外置依赖包
	 */
	private void externalDependencyLib(String tarPath) {
		createDependencyLibList(tarPath);
		if(CollUtil.isEmpty(mainClasses)) {
		    throw new RuntimeException("启动类不能为空");
		}
		String libRelativePathForLinux = libRelativePath + LIBLIST_LINUX_PATH;
		String libRelativePathForWin = (libRelativePath + LIBLIST_WIN_PATH).replaceAll("/", "//");

		for (String mainclass : mainClasses) {
			InputStream MERGE_LIB_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + MERGE_LIB_SH_STR);
			InputStream START_LIB_BAT_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_LIB_BAT_STR);
			String mergeLibContent = IoUtil.read(MERGE_LIB_SH_IS, StandardCharsets.UTF_8);
			String startLibBatContent = IoUtil.read(START_LIB_BAT_IS, StandardCharsets.UTF_8);

			String className = appName;
			if(mainClasses.size() > 1) {
				String[] split = mainclass.split("\\.");
				className = split[split.length - 1].replace("Application", "");
			}
			mergeLibContent = mergeLibContent.replaceAll(DEPENDENCYLIBLIST_TEMPLATE, libRelativePathForLinux)
					.replaceAll(APP_NAME_TEMPLATE, appName)
					.replaceAll(XMS_TEMPLATE, xms)
					.replaceAll(XMX_TEMPLATE, xmx)
					.replaceAll(MAINCLASS_TEMPLATE, mainclass);

			startLibBatContent = startLibBatContent.replaceAll(DEPENDENCYLIBLIST_TEMPLATE, libRelativePathForWin)
					.replaceAll(APP_NAME_TEMPLATE, appName)
					.replaceAll(MAINCLASS_TEMPLATE, mainclass)
					.replaceAll("//", "\\\\");

			FileUtil.writeUtf8String(mergeLibContent, FileUtil.touch(tarPath + "/" + className + ".sh"));
			FileUtil.writeUtf8String(startLibBatContent, FileUtil.touch(tarPath + "/" + className + ".bat"));
		}
	}

	/**
	 * 替换反斜杠
	 */
	private String replaceBackslash(String str) {
		if(StringUtils.isNotBlank(str)) {
		    str = str.replaceAll("\\\\", "/");
		}
		return str;
	}
	
	/**
	 * 创建依赖包清单
	 */
	private void createDependencyLibList(String tarPath) {
		File[] ls = FileUtil.ls(dependencyPath);
		if(StringUtils.isBlank(dependencyPath) || ls.length == 0) {
			return;
		}
		System.out.println("开始创建依赖包清单");

		dependencyPath = replaceBackslash(dependencyPath);
		String[] dependencyPathSplit = dependencyPath.split("/");
		// 依赖包上级目录
		String parent = replaceBackslash(FileUtil.getParent(dependencyPath, 1));
		tarPath = replaceBackslash(tarPath).replaceAll("//","");

		libRelativePath = dependencyPathSplit[dependencyPathSplit.length - 1];

		if(!parent.equals(tarPath)) {
			libRelativePath = "../" + libRelativePath;
		}

		StringBuilder sb = new StringBuilder();
		String winContent;
		String linuxContent;
		if (StringUtils.isNotBlank(classpathPrefix)) {
			libRelativePath = classpathPrefix;
		}
		for (File l : ls) {
			sb.append(libRelativePath).append("/").append(l.getName()).append(":");
		}
		linuxContent = sb.toString();
		winContent = linuxContent.replaceAll("/","\\\\").replaceAll(":", ";");

		File win = FileUtil.touch(dependencyPath + LIBLIST_WIN_PATH);
		File linux = FileUtil.touch(dependencyPath + LIBLIST_LINUX_PATH);
		FileUtil.writeUtf8String(winContent, win);
		FileUtil.writeUtf8String(linuxContent, linux);
		System.out.println("依赖包清单创建成功");
	}
}
