package pers.xzhi;

import lombok.Data;

/**
 * @author Xzhi
 * @desc: 脚本配置信息
 * @date 2020-05-21 09:27
 */
@Data
public class ShellConf {
    /**
     * bat 脚本名称，默认使用start.bat命名
     */
    private String winShellName;
    /**
     * Linux 脚本名称，默认使用${project.artifactId}命名
     */
    private String linuxShellName;
    /**
     * 启动类路径
     */
    private String mainClass;
    /**
     * 针对Linux的start、stop、restart三个脚本是否合并成一个，默认合并
     */
    private boolean mergeLinuxShell = true;
    /**
     * 核心jar所在的根路径
     */
    private String basePath = "$(pwd)";
    /**
     * 脚本名称批处理
     */
    private String shellNameBat;

}
