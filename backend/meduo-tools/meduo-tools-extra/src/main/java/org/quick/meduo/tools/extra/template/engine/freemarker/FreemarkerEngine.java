package org.quick.meduo.tools.extra.template.engine.freemarker;

import java.io.IOException;

import org.quick.meduo.tools.core.io.FileUtil;
import org.quick.meduo.tools.core.io.IORuntimeException;
import org.quick.meduo.tools.core.util.ClassUtil;
import org.quick.meduo.tools.extra.template.Template;
import org.quick.meduo.tools.extra.template.TemplateConfig;
import org.quick.meduo.tools.extra.template.TemplateEngine;
import org.quick.meduo.tools.extra.template.TemplateException;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;

/**
 * Beetl模板引擎封装
 * 
 * @author looly
 */
public class FreemarkerEngine implements TemplateEngine {

	private Configuration cfg;

	// --------------------------------------------------------------------------------- Constructor start
	/**
	 * 默认构造
	 */
	public FreemarkerEngine() {}

	/**
	 * 构造
	 * 
	 * @param config 模板配置
	 */
	public FreemarkerEngine(TemplateConfig config) {
		init(config);
	}

	/**
	 * 构造
	 * 
	 * @param freemarkerCfg {@link Configuration}
	 */
	public FreemarkerEngine(Configuration freemarkerCfg) {
		init(freemarkerCfg);
	}
	// --------------------------------------------------------------------------------- Constructor end

	@Override
	public TemplateEngine init(TemplateConfig config) {
		if(null == config){
			config = TemplateConfig.DEFAULT;
		}
		init(createCfg(config));
		return this;
	}

	/**
	 * 初始化引擎
	 * @param freemarkerCfg Configuration
	 */
	private void init(Configuration freemarkerCfg){
		this.cfg = freemarkerCfg;
	}

	@Override
	public Template getTemplate(String resource) {
		if(null == this.cfg){
			init(TemplateConfig.DEFAULT);
		}
		try {
			return FreemarkerTemplate.wrap(this.cfg.getTemplate(resource));
		} catch(IOException e) {
			throw new IORuntimeException(e);
		}catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	/**
	 * 创建配置项
	 * 
	 * @param config 模板配置
	 * @return {@link Configuration }
	 */
	private static Configuration createCfg(TemplateConfig config) {
		if (null == config) {
			config = new TemplateConfig();
		}

		final Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		cfg.setLocalizedLookup(false);
		cfg.setDefaultEncoding(config.getCharset().toString());

		switch (config.getResourceMode()) {
		case CLASSPATH:
			cfg.setTemplateLoader(new ClassTemplateLoader(ClassUtil.getClassLoader(), config.getPath()));
			break;
		case FILE:
			try {
				cfg.setTemplateLoader(new FileTemplateLoader(FileUtil.file(config.getPath())));
			} catch (IOException e) {
				throw new IORuntimeException(e);
			}
			break;
		case WEB_ROOT:
			 try {
				 cfg.setTemplateLoader(new FileTemplateLoader(FileUtil.file(FileUtil.getWebRoot(), config.getPath())));
			 } catch (IOException e) {
				 throw new IORuntimeException(e);
			 }
			break;
		case STRING:
			cfg.setTemplateLoader(new SimpleStringTemplateLoader());
			break;
		default:
			break;
		}
		
		return cfg;
	}
}
