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

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

/**
 * 创建启动脚本
 */
@Mojo(name = "script", defaultPhase = LifecyclePhase.PACKAGE)
public class ScriptMojo extends AbstractMojo {

	private static final String BIN_PATH = "/bin";
	private static final String RESTART_STR = "/restart.sh";
	private static final String START_STR = "/start.sh";
	private static final String STOP_STR = "/stop.sh";
	private static final String START_BAT_STR = "/start.bat";
	private static final String MERGE_SH_STR = "/merge.sh";
	private static final String MERGE_LIB_SH_STR = "/merge-lib.sh";
	private static final String START_LIB_BAT_STR = "/start-lib.bat";
	private static final String START_LIB_SH_STR = "/start-lib.sh";

	private static final InputStream RESTART_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + RESTART_STR);
	private static final InputStream START_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_STR);
	private static final InputStream STOP_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + STOP_STR);
	private static final InputStream START_BAT_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_BAT_STR);
	private static final InputStream MERGE_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + MERGE_SH_STR);

	private static final String APP_NAME_TEMPLATE = "#appName#";
	private static final String XMS_TEMPLATE = "#Xms#";
	private static final String XMX_TEMPLATE = "#Xmx#";
	private static final String DEPENDENCYLIBLIST_TEMPLATE = "#dependencyLibList#";
	private static final String MAINCLASS_TEMPLATE = "#mainClass#";
	/**
	 * libList-win路径
	 */
	private static final String  LIBLIST_WIN_PATH = "/libList/libList-win";
	/**
	 * libList-linux路径
	 */
	private static final String  LIBLIST_LINUX_PATH = "/libList/libList-linux";
	/**
	 * 依赖包相对路径
	 */
	private String libRelativePath;
	/**
	 * JVM最小内存，默认32
	 */
	private String xms;
	/**
	 * JVM最大内存，默认64
	 */
	private String xmx;
	/**
	 * 依赖包路径
	 */
	private String dependencyPath;
	/**
	 * 目标路径
	 */
	private String tarPath;
	/**
	 * 程序名称
	 */
	@Parameter(defaultValue = "${project.artifactId}")
	private String appName;
	/**
	 * 输出根目录
	 */
	@Parameter(defaultValue = "${project.build.directory}", readonly = true)
	private String outDirTemp;
	/**
	 * 程序版本
	 */
	@Parameter(defaultValue = "${project.version}")
	private String appVersion;
	/**
	 * 脚本配置列表
	 */
	@Parameter
	private List<ShellConf> shells;
	/**
	 * 主要配置信息
	 */
	@Parameter
	private MajorConf majorConf;

	/**
	 * 初始化
	 */
	private void init() {
		if (majorConf == null) {
			throw new RuntimeException("majorConf节点不能为空");
		}
		xms = majorConf.getXms();
		xmx = majorConf.getXmx();
		dependencyPath = majorConf.getDependencyPath();
		for (ShellConf shell : shells) {
			if (StringUtils.isBlank(shell.getMainClass())) {
				throw new RuntimeException("启动类不能为空");
			}
		}
		String outDir = majorConf.getOutDir();
		String shellOutputPath = majorConf.getShellOutputPath();

		if (StringUtils.isBlank(outDir)) {
			outDir = outDirTemp;
		}
		tarPath = outDir + File.separator + appName + "-" + appVersion + File.separator + appName;

		if (StringUtils.isNotBlank(shellOutputPath)) {
			tarPath = outDir + File.separator + shellOutputPath;
		}
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		System.out.println("开始创建脚本……");
		init();
		if (majorConf.isUseInlayLib()) {
			inlayDependencyLib();
		} else {
			externalDependencyLib();
		}

		System.out.println("创建脚本成功！");
		System.out.println("脚本输出路径：" + tarPath);

	}

	/**
	 * 内置依赖包
	 */
	private void inlayDependencyLib() {
		String startContent = IoUtil.read(START_SH_IS, StandardCharsets.UTF_8);
		String startBatContent = IoUtil.read(START_BAT_IS, StandardCharsets.UTF_8);
		String mergeContent = IoUtil.read(MERGE_SH_IS, StandardCharsets.UTF_8);

		startContent = startContent.replace(APP_NAME_TEMPLATE, appName)
				.replace(XMS_TEMPLATE, xms)
				.replace(XMX_TEMPLATE, xmx);
		startBatContent = startBatContent.replace(APP_NAME_TEMPLATE, appName);
		mergeContent = mergeContent.replace(APP_NAME_TEMPLATE, appName)
				.replace(XMS_TEMPLATE, xms)
				.replace(XMX_TEMPLATE, xmx);

		ShellConf shellConf = shells.get(0);
		dealShellNameAndContent(startContent, startBatContent, mergeContent, shellConf, shells.size());
	}

	/**
	 * 外置依赖包
	 */
	private void externalDependencyLib() {
		createDependencyLibList(tarPath);
		String libRelativePathForLinux = libRelativePath + LIBLIST_LINUX_PATH;
		String libRelativePathForWin = (libRelativePath + LIBLIST_WIN_PATH).replaceAll("/", "//");

		for (ShellConf shellConf : shells) {
			String mainClass = shellConf.getMainClass();
			InputStream MERGE_LIB_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + MERGE_LIB_SH_STR);
			InputStream START_LIB_BAT_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_LIB_BAT_STR);
			InputStream START_LIB_SH_IS = ScriptMojo.class.getResourceAsStream(BIN_PATH + START_LIB_SH_STR);
			String mergeLibContent = IoUtil.read(MERGE_LIB_SH_IS, StandardCharsets.UTF_8);
			String startLibBatContent = IoUtil.read(START_LIB_BAT_IS, StandardCharsets.UTF_8);
			String startLibShContent = IoUtil.read(START_LIB_SH_IS, StandardCharsets.UTF_8);

			mergeLibContent = mergeLibContent.replaceAll(DEPENDENCYLIBLIST_TEMPLATE, libRelativePathForLinux)
					.replaceAll(APP_NAME_TEMPLATE, appName)
					.replaceAll(XMS_TEMPLATE, xms)
					.replaceAll(XMX_TEMPLATE, xmx)
					.replaceAll(MAINCLASS_TEMPLATE, mainClass);

			startLibBatContent = startLibBatContent.replaceAll(DEPENDENCYLIBLIST_TEMPLATE, libRelativePathForWin)
					.replaceAll(APP_NAME_TEMPLATE, appName)
					.replaceAll(MAINCLASS_TEMPLATE, mainClass)
					.replaceAll("//", "\\\\");

			startLibShContent = startLibShContent.replaceAll(DEPENDENCYLIBLIST_TEMPLATE, libRelativePathForLinux)
					.replace(APP_NAME_TEMPLATE, appName)
					.replace(XMS_TEMPLATE, xms)
					.replace(XMX_TEMPLATE, xmx)
					.replaceAll(MAINCLASS_TEMPLATE, mainClass);

			dealShellNameAndContent(startLibShContent, startLibBatContent, mergeLibContent, shellConf, shells.size());
		}
	}

	/**
	 * 处理脚本名称和内容
	 */
	private void dealShellNameAndContent(String startContent, String startBatContent, String mergeContent,
										 ShellConf shellConf, int shellSize) {
		String mainClass = shellConf.getMainClass();

		String restartContent = IoUtil.read(RESTART_SH_IS, StandardCharsets.UTF_8);
		String stopContent = IoUtil.read(STOP_SH_IS, StandardCharsets.UTF_8);

		restartContent = restartContent.replace(APP_NAME_TEMPLATE, appName);
		stopContent = stopContent.replace(APP_NAME_TEMPLATE, appName);

		String className = appName;
		String startBatName = START_BAT_STR;
		if (shellSize > 1) {
			String[] split = mainClass.split("\\.");
			className = split[split.length - 1].replace("Application", "");
			startBatName = File.separator + className + ".bat";
		}

		if (shellSize > 1 || shellConf.isMergeLinuxShell()) {
			String linuxShellName = shellConf.getLinuxShellName();
			if (StringUtils.isNotBlank(linuxShellName)) {
				className = linuxShellName;
			}
			FileUtil.writeUtf8String(mergeContent, FileUtil.touch(tarPath + "/" + className + ".sh"));
		} else {
			FileUtil.writeUtf8String(startContent, FileUtil.touch(tarPath + START_STR));
			FileUtil.writeUtf8String(restartContent, FileUtil.touch(tarPath + RESTART_STR));
			FileUtil.writeUtf8String(stopContent, FileUtil.touch(tarPath + STOP_STR));
		}
		String winShellName = shellConf.getWinShellName();
		if (StringUtils.isNotBlank(winShellName)) {
			startBatName = File.separator + winShellName + ".bat";
		}
		FileUtil.writeUtf8String(startBatContent, FileUtil.touch(tarPath + startBatName));
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
		String classPathPrefix = majorConf.getClassPathPrefix();
		String libRelativePathTemp = libRelativePath;
		if (StringUtils.isNotBlank(classPathPrefix)) {
			libRelativePathTemp = classPathPrefix;
		}
		for (File l : ls) {
			sb.append(libRelativePathTemp).append("/").append(l.getName()).append(":");
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
