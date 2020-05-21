package pers.xzhi;

import lombok.Data;

/**
 * @author Xzhi
 * @desc: 主要配置信息
 * @date 2020-05-21 10:00
 */
@Data
public class MajorConf {
    /**
     * 输出根目录
     */
    private String outDir;
    /**
     * JVM最小内存，默认32
     */
    private String xms = "32";
    /**
     * JVM最大内存，默认64
     */
    private String xmx = "64";
    /**
     * 脚本输出路径（顶级目录是target），默认路径：target/appName-appVersion/appName
     */
    private String shellOutputPath;
    /**
     * 依赖包路径
     */
    private String dependencyPath;
    /**
     * 是否使用内置依赖包classPath，true内置，false外置
     */
    private boolean useInlayLib = true;
    /**
     * classPath前缀
     */
    private String classPathPrefix;
}
