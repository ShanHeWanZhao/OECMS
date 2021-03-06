package com.trd.oecms.runner;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Trd
 * @date 2020-04-05 15:57
 */
public class MybatisGeneratorRunner {
	public static void main(String[] args) {
		try {
			List<String> warnings = new ArrayList<>();
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream("generatorConfig.xml");
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(is);
			DefaultShellCallback callback = new DefaultShellCallback(true);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			// 输出警告
			for (String warning : warnings) {
				System.out.println(warning);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
