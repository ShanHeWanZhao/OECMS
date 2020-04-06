package com.trd.oecms.config;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 自定义的mybatis的逆向工程注释生成格式
 * @author Trd
 * @date 2020-04-05 14:37
 */
public class MyMybatisCommentGenerator implements CommentGenerator {
	/** The properties. */
	private Properties properties;

	/** The suppress date. */
	private boolean suppressDate;

	/** The suppress all comments. */
	private boolean suppressAllComments;

	/** The addition of table remark's comments.
	 * If suppressAllComments is true, this option is ignored*/
	private boolean addRemarkComments;

	/**
	 * 自定义的doc作者
	 */
	private String author;

	private SimpleDateFormat dateFormat;

	/**
	 * Instantiates a new default comment generator.
	 */
	public MyMybatisCommentGenerator() {
		properties = new Properties();
		suppressDate = false;
		suppressAllComments = false;
		addRemarkComments = false;
	}

	/**
	 * 从逆向工程文件commentGenerator标签里读取的属性名
	 * @param properties
	 */
	@Override
	public void addConfigurationProperties(Properties properties) {
		this.properties.putAll(properties);

		suppressDate = isTrue(properties
				.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

		suppressAllComments = isTrue(properties
				.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

		addRemarkComments = isTrue(properties
				.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
		// 自定义解析author属性值
		author = properties.getProperty("author");

		String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
		if (StringUtility.stringHasValue(dateFormatString)) {
			dateFormat = new SimpleDateFormat(dateFormatString);
		}
	}


	/**
	 * 添加dao注释
	 * @param compilationUnit
	 */
	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {
		// add no file level comments by default
		if (compilationUnit.isJavaInterface()){
			compilationUnit.addFileCommentLine("/**");
			compilationUnit.addFileCommentLine(" * @author "+author);
			compilationUnit.addFileCommentLine(" * @date "+ getDateString());
			compilationUnit.addFileCommentLine(" */");
		}

	}

	/**
	 * Adds a suitable comment to warn users that the element was generated, and when it was generated.
	 * xml文件注释
	 * @param xmlElement
	 *            the xml element
	 */
	@Override
	public void addComment(XmlElement xmlElement) {
		if (suppressAllComments) {
			return;
		}
		if (getDateString() != null) {
			xmlElement.addElement(new TextElement("<!-- date： "+getDateString()+" -->"));
		}

	}

	@Override
	public void addRootComment(XmlElement rootElement) {
		// add no document level comments by default
	}

	/**
	 * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
	 * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
	 *
	 * @param javaElement
	 *            the java element
	 * @param markAsDoNotDelete
	 *            the mark as do not delete
	 */
	private void addJavadocTag(JavaElement javaElement,
								 boolean markAsDoNotDelete) {
		javaElement.addJavaDocLine(" *"); 
		StringBuilder sb = new StringBuilder();
		sb.append(" * "); 
		sb.append(MergeConstants.NEW_ELEMENT_TAG);
		if (markAsDoNotDelete) {
			sb.append(" do_not_delete_during_merge"); 
		}
		String s = getDateString();
		if (s != null) {
			sb.append(' ');
			sb.append(s);
		}
		javaElement.addJavaDocLine(sb.toString());
	}

	/**
	 * This method returns a formated date string to include in the Javadoc tag
	 * and XML comments. You may return null if you do not want the date in
	 * these documentation elements.
	 *
	 * @return a string representing the current timestamp, or null
	 */
	private String getDateString() {
		if (suppressDate) {
			return null;
		} else if (dateFormat != null) {
			return dateFormat.format(new Date());
		} else {
			return new Date().toString();
		}
	}

	/**
	 * 添加内部类的doc注释
	 * @param innerClass
	 * @param introspectedTable
	 */
	@Override
	public void addClassComment(InnerClass innerClass,
								IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
		innerClass.addJavaDocLine("/**");
		innerClass.addJavaDocLine(" * @author "+author);
		innerClass.addJavaDocLine(" * @date "+ getDateString());

	//	addJavadocTag(innerClass, false);
		innerClass.addJavaDocLine(" */");
	}

	/**
	 *  添加类的doc注释
	 * @param topLevelClass 最外层类
	 * @param introspectedTable 内省表
	 */
	@Override
	public void addModelClassComment(TopLevelClass topLevelClass,
									 IntrospectedTable introspectedTable) {
		if (suppressAllComments  || !addRemarkComments) {
			return;
		}

		topLevelClass.addJavaDocLine("/**");
		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		if (tableName.startsWith("`") && tableName.endsWith("`")){
			tableName = tableName.substring(1, tableName.length() - 1);
		}
		topLevelClass.addJavaDocLine(" * 对应的数据库表名: "+ tableName);
		topLevelClass.addJavaDocLine(" * @author "+author);
		topLevelClass.addJavaDocLine(" * @date "+ getDateString());
		String remarks = introspectedTable.getRemarks();
		if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
			topLevelClass.addJavaDocLine(" * Database Table Remarks:");
			String[] remarkLines = remarks.split(System.getProperty("line.separator"));  
			for (String remarkLine : remarkLines) {
				topLevelClass.addJavaDocLine(" *   " + remarkLine);  
			}
		}
	//	topLevelClass.addJavaDocLine(" *");

	//	addJavadocTag(topLevelClass, true);

		topLevelClass.addJavaDocLine(" */"); 
	}


	@Override
	public void addEnumComment(InnerEnum innerEnum,
							   IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
		StringBuilder sb = new StringBuilder();

		innerEnum.addJavaDocLine("/**");
		innerEnum.addJavaDocLine(" * @author "+author);
		innerEnum.addJavaDocLine(" * @date "+ getDateString());

		sb.append(" * This enum corresponds to the database table "); 
		sb.append(introspectedTable.getFullyQualifiedTable());
		innerEnum.addJavaDocLine(sb.toString());

	//	addJavadocTag(innerEnum, false);

		innerEnum.addJavaDocLine(" */");
	}

	/**
	 * 重写的方法，将数据库表列的comment加到字段doc上
	 * @param field
	 * @param introspectedTable
	 * @param introspectedColumn
	 */
	@Override
	public void addFieldComment(Field field,
								IntrospectedTable introspectedTable,
								IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		String comment = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(comment)){
			field.addJavaDocLine("/**");
			// 核心代码 introspectedColumn.getRemarks() 就是获取字段注释
			field.addJavaDocLine(" * "+comment);
			field.addJavaDocLine(" */");
		}
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}

		String comment = introspectedTable.getRemarks();
		if (StringUtility.stringHasValue(comment)){
			field.addJavaDocLine("/**");
			// 核心代码 introspectedColumn.getRemarks() 就是获取字段注释
			field.addJavaDocLine(" * "+comment);
			//	addJavadocTag(field, false);
			field.addJavaDocLine(" */");
		}
	}

	@Override
	public void addGeneralMethodComment(Method method,
										IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
	}

	@Override
	public void addGetterComment(Method method,
								 IntrospectedTable introspectedTable,
								 IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}

//		method.addJavaDocLine("/**");
//		method.addJavaDocLine(" * " + introspectedColumn.getRemarks());
//		method.addJavaDocLine(" */");
	}

	@Override
	public void addSetterComment(Method method,
								 IntrospectedTable introspectedTable,
								 IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
//		method.addJavaDocLine("/**");
//		method.addJavaDocLine(" * " + introspectedColumn.getRemarks());
//		method.addJavaDocLine(" */");
	}

	/**
	 * 添加内部类的注释
	 * @param innerClass 内部类
	 * @param introspectedTable 内省表
	 * @param markAsDoNotDelete 标记为不删除
	 */
	@Override
	public void addClassComment(InnerClass innerClass,
								IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
		if (suppressAllComments) {
			return;
		}
		innerClass.addJavaDocLine("/**");
		innerClass.addJavaDocLine(" * @author "+author);
		innerClass.addJavaDocLine(" * @date "+ getDateString());

	//	addJavadocTag(innerClass, markAsDoNotDelete);

		innerClass.addJavaDocLine(" */"); 
	}
}
