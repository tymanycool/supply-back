package net.shopin.supply.util;



import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;


/**
 * ˵ ��     : ϵͳ�����ļ�
 * author: ½����
 * data  : 2012-12-12
 * email : xiangxingchina@163.com
 **/
public class SystemConfig {
	
		//ϵͳ�����ļ�
		public final static String SYSTEM_PROPERTIES_FILE_NAME="system.properties";
		//��ʱ���������ļ�
		public final static String CRON_CONFIG_FILE_NAME="cron.xml";
		//�ַ�ָ���
		public final static String  STRING_SPLIT_TAG = "|"; 
		//ldap��������ַ
		public  static String WSCLIENT_URL = "";
		public static String UAC_URL = "";
					
		public static String SYSTEM_ROLE = "";	
		public static String SUPER_ADMIN = "";
		public static String SYSTEM_WMS = "SYSTEM_WMS";
		public static String SYSTEM_OMS_BACK = "SYSTEM_OMS_BACK";
		public static String ROLE_WMS_ADMIN = "";
		
		public static String ROLE_JIRA_ADMIN = "";
		public static String SSD_SYSTEM_URL = "";
		
		 //��վƷ��������channelSid=2
	    public static String WEB_PROCAT_TREE = "";
	     //erpƷ�������� channelSid=1
	    public static String ERP_PROCAT_TREE = "";
	    public  static  String SEARCH_URL = "";
		public  static  String FTP_HOST = "";
		public static  String FTP_USERNAME = "";
		public static  String FTP_PASSWORD = "";
		public static  String PROMOTION_PATH = "";
		public static  String SALEMSG_PATH = "";
		public static String CATEGORY_AD_PATH ="";
		public static String BRAND_LOGO_PATH ="";
		public static  String PROBESTDETAIL_PATH = "";
		public static String FTP_PORT = "";
		public static String FLASH_PLAN = "";
		public static String WSG_SYN="";
		public static String PRODUCT_KEY = "";
		//��վ��ַ
		public static String WEBADDRESS = "";
		//��ȡproduct��Ʒ���������������ַ
		public static String PRODUCT_COUNT_URL = "";
		//��ȡproduct��Ʒ������ݵ������ַ
		public static String PRODUCT_SEO_URL = "";
		//���sitemap_index.xml �ļ��ĵ�ַ
		public static String GEN_PATH = "";
		/*****************************ϵͳ�����ļ�����*********************************/
		//#Debugģʽ 1:Ĭ�ϣ���ݲ�����ȷ���� 2.debug 3.info  4.ws 5.warn 6.fatal 7.error 8.fatal 9.nolog
		public final static String SYSTEM_PROPERTIES_DEBUG_LEVEL = "debug";
		public static String SYSTEM_PROPERTIES_DEBUG_LEVEL_VALUE = "2";
		//#��������   1:���� ��Ĭ�ϣ�  2:����   3:����web 4.����Ϊweb����� -- ���� ��־��ʾ
		public final static String SYSTEM_PROPERTIES_DEPLOYTYPE_NAME = "deploy";
		public  static String SYSTEM_PROPERTIES_DEPLOYTYPE_VALUE = "3";
		public  static String SYSTEM_PROPERTIES_CRONTYPE_VALUE = "4";
		/*****************************ϵͳ�����ļ�����*********************************/
		static {
			InputStream in = Constants.class.getClassLoader().getResourceAsStream(SYSTEM_PROPERTIES_FILE_NAME);
			Properties p = new Properties();
			try {
				p.load(in);
				SYSTEM_PROPERTIES_DEBUG_LEVEL_VALUE = p.getProperty(SYSTEM_PROPERTIES_DEBUG_LEVEL);
				SYSTEM_PROPERTIES_DEPLOYTYPE_VALUE =  p.getProperty(SYSTEM_PROPERTIES_DEPLOYTYPE_NAME);
				WSCLIENT_URL = p.getProperty("wsClientUrl");
				UAC_URL = p.getProperty("uacValidateUrl");
				SYSTEM_ROLE = p.getProperty("system_role");
				SUPER_ADMIN = p.getProperty("super_admin");
				ROLE_WMS_ADMIN = p.getProperty("orle_wms_admin");
				ROLE_JIRA_ADMIN = p.getProperty("role_jira_admin");
				SSD_SYSTEM_URL = p.getProperty("ssd_system");
				WEB_PROCAT_TREE= p.getProperty("web_procat_tree");
				ERP_PROCAT_TREE = p.getProperty("erp_procat_tree");
				FTP_HOST = p.getProperty("ftp_host");
				SEARCH_URL=p.getProperty("search_url");
				FTP_USERNAME = p.getProperty("ftp_username");
				FTP_PASSWORD = p.getProperty("ftp_password");
				FTP_PORT = p.getProperty("ftp_port");
				//�ͼƬ����·��
				PROMOTION_PATH = p.getProperty("image_path");
				SALEMSG_PATH = p.getProperty("saleMsg_image_path");
				CATEGORY_AD_PATH = p.getProperty("category_ad_path");
				PROBESTDETAIL_PATH = p.getProperty("probestdetail_path");
				FLASH_PLAN = p.getProperty("flash_plan");
				WSG_SYN = p.getProperty("wsg_synchro");
				PRODUCT_KEY = p.getProperty("product_key");
				WEBADDRESS = p.getProperty("webaddress");
				BRAND_LOGO_PATH = p.getProperty("supply_path");
				PRODUCT_COUNT_URL = SSD_SYSTEM_URL+"/web/seoCount.html";
				PRODUCT_SEO_URL = SSD_SYSTEM_URL+"/web/seoProducts.html";
				GEN_PATH = p.getProperty("gen_path")+File.separator;
			} catch (Exception e) {
				Logger logger = Logger.getLogger(Constants.class);
				logger.error(e.getLocalizedMessage(), e);
			}
		}
		
}
