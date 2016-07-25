/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.App', {
	extend: 'Ext.ux.desktop.App',

	requires: [
		'Ext.window.MessageBox',
		'Ext.ux.desktop.ShortcutModel',
		'MyDesktop.SystemStatus',
		'MyDesktop.VideoWindow',
		'MyDesktop.GridWindow',
		'MyDesktop.TabWindow',
		'MyDesktop.AccordionWindow',
		'MyDesktop.Notepad',
		'MyDesktop.BogusMenuModule',
		'MyDesktop.BogusModule',
		'MyDesktop.Settings',
		
		'ShopinDesktop.PadAndGuideManageWindow',
		'ShopinDesktop.UserAndAuthViewWindow',
		'ShopinDesktop.GuideinfoRegisterWindowDel',
		'ShopinDesktop.GuideinfoRegisterWindow',
		'ShopinDesktop.GuideInfoManageWindow',
		'ShopinDesktop.PadBaseinfoManageWindow',
		'ShopinDesktop.ExistingGuideinfoRegisterWindow',
		'ShopinDesktop.GuideLogininfoManageWindow',
		'ShopinDesktop.SupplierViewWindow',
		'ShopinDesktop.StatisticalQueryViewWindow',
		'ShopinDesktop.PersonalCenterWindow',
		'ShopinDesktop.LedgerManagementWindow',
		'ShopinDesktop.ExpenseReportsWindow',
		'ShopinDesktop.PadInfoManageWindow'
	],

	init: function() {
		// custom logic before getXYZ methods get called...

		this.callParent();
	},

	getModules : function(){
		return [
			new ShopinDesktop.PadAndGuideManageWindow(),
			new ShopinDesktop.UserAndAuthViewWindow(),
			new ShopinDesktop.GuideinfoRegisterWindowDel(),
			new ShopinDesktop.GuideinfoRegisterWindow(),
			new ShopinDesktop.GuideInfoManageWindow(),
//			new ShopinDesktop.PadBaseinfoManageWindow(),
			new ShopinDesktop.ExistingGuideinfoRegisterWindow(),
			new ShopinDesktop.GuideLogininfoManageWindow(),
			new ShopinDesktop.SupplierViewWindow(),
			new ShopinDesktop.StatisticalQueryViewWindow(),
			new ShopinDesktop.PersonalCenterWindow(),
			new ShopinDesktop.LedgerManagementWindow(),
			new ShopinDesktop.ExpenseReportsWindow(),
			new ShopinDesktop.PadInfoManageWindow()
		];
	},

	getDesktopConfig: function () {
		var me = this, ret = me.callParent();

		var dataStore = Ext.create('Ext.data.Store', {
				model: 'Ext.ux.desktop.ShortcutModel',
				data: [
//					{ name: 'PAD与导购信息管理', iconCls: 'msg-shortcut', module: 'padAndGuideManageWindow'},
//					{ name: '导购信息注册', iconCls: 'msg-shortcut', module: 'guideinfoRegisterWindowDel'},
//					{ name: '用户权限管理', iconCls: 'brand-shortcut', module: 'userAndAuthViewWindow'},
					{ name: '现有导购信息注册', iconCls: 'msg-shortcut', module: 'existingGuideinfoRegisterWindow'},
					{ name: '导购信息注册', iconCls: 'msg-shortcut', module: 'guideinfoRegisterWindow'},
					{ name: '导购信息管理', iconCls: 'msg-shortcut', module: 'guideInfoManageWindow'},
//					{ name: 'PAD信息管理', iconCls: 'msg-shortcut', module: 'padBaseinfoManageWindow'},
//					{ name: '导购登录信息管理', iconCls: 'msg-shortcut', module: 'guideLogininfoManageWindow'},
//					{ name: '品牌Logo管理', iconCls: 'msg-shortcut', module: 'supplierViewWindow'},
					{ name: '权限管理', iconCls: 'brand-shortcut', module: 'userAndAuthViewWindow'},
					{ name: '导购统计查询', iconCls: 'msg-shortcut', module: 'statisticalQueryViewWindow'},
					{ name: '个人中心', iconCls: 'msg-shortcut', module: 'personalCenterWindow'},
					{ name: '费用报表', iconCls: 'msg-shortcut', module: 'expenseReportsWindow'},
					{ name: '台账管理', iconCls: 'msg-shortcut', module: 'ledgerManagementWindow'},
					//pad系统新需求，之前的pad管理系统会称为改系统的子功能
					{ name: 'PAD管理', iconCls: 'msg-shortcut', module: 'padInfoManageWindow'}
				]
		});
		
		//要显示的资源
		var resourceStore = Ext.create('Ext.data.Store', {
				model: 'Ext.ux.desktop.ShortcutModel',
				data: [
//				       { name: 'PAD管理', iconCls: 'msg-shortcut', module: 'padInfoManageWindow'}
				]
		});
		var arr = allResource.split(',');
		for(var i =0 ;i<arr.length;i++){
			dataStore.each(function(record){
				if(arr[i]==record.data.module){
					var number = dataStore.find("module",record.data.module);
					var newRecord = dataStore.getAt(number);
					resourceStore.addSorted(newRecord);
				}
			});
		}
		
		return Ext.apply(ret, {
			//cls: 'ux-desktop-black',

			contextMenuItems: [
				{ text: '设置', handler: me.onSettings, scope: me }
			],

			shortcuts: resourceStore,

//			wallpaper: 'wallpapers/sky.jpg',
			wallpaperStretch: true
		});
		
	},

	// config for the start menu
	getStartConfig : function() {
		var me = this, ret = me.callParent();

		return Ext.apply(ret, {
			title: '供应商管理平台',
			iconCls: 'user',
			height: 300,
			toolConfig: {
				width: 100,
				items: [
					{
						text:'设置',
						iconCls:'settings',
						handler: me.onSettings,
						scope: me
					},
					'-',
					{
						text:'注销',
						iconCls:'logout',
						handler: me.onLogout,
						scope: me
					}
				]
			}
		});
	},

	getTaskbarConfig: function () {
		var ret = this.callParent();

		return Ext.apply(ret, {
			quickStart: [
			],
			trayItems: [
				{ xtype: 'trayclock', flex: 1 }
			]
		});
	},

	onLogout: function () {
		Ext.Msg.confirm('注销', '您确定要退出吗?',function(btn){
			if(btn == 'yes'){
				//-------------清空session
//				Ext.Ajax.request({
//					url: "request.getContextPath()" +"/logout.json",
//					method: "POST"
//				});
//				this.desktop.html="http://172.16.103.165:8090/cas3/logout?service=http://www.shopin.net";
				window.location = _appctx + "/logout.json";
//				window.location.href=_appctx + "/";
//				this.desktop.close();
			}
		},this);
	},

	onSettings: function () {
		var dlg = new MyDesktop.Settings({
			desktop: this.desktop
		});
		dlg.show();
	}
});
