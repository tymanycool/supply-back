/**
 * 修改导购登录信息
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.GuideLogininfoUpdateWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	sid :null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		this.initComponents();
		var me = this;

		this.superclass.constructor.call(this, {
			title :'修改导购登录信息',
			height : 310,
			id:'guideLogininfoUpdateId',
			width  : 420,
			constrain : true,
			plain : true,
			items : [ this.formPanel],
			buttons :[
				{
					text : '保存',
					handler : function() {
						me.update();
					}
				},{
					text : '取消',
					handler : function() {
						me.cancel();
					}
				} 
			]
		});
	},
	initComponents : function() {
		var sid = this.sid;
		alert(sid);
		var guideNo = this.guideNo;
		var shopName = this.shopName;
		var chestcardShoprule = this.chestcardShoprule;
		var userName = this.userName;
		var password = this.password;
		var mdpassword = "";
		if(password != ""){
			mdpassword = "******";
		}

		this.formPanel = Ext.create('Ext.form.Panel', {
				url : _appctx + '/guideLogininfo/save',
				layout : 'anchor',
				defaults : {
					anchor : '100%',
					labelAlign : 'right'
				},
				height : 310,
				id:"editForm",
				bodyPadding: "15 15 15 15",
				items: [
	                	{
	                    xtype: 'container',
	                    layout: 'hbox',
	                    items: [
	                    	{
	                    		xtype : 'textfield',
								name : 'sid',
								fieldLabel : 'SID',
								hidden:true,
								value: sid
							},
	                    	{
                    			xtype: 'textfield',
								name : 'guideNo',
								width : 300,
								fieldLabel : '导购编号',
								value : guideNo,
								allowBlank : false
								}]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										xtype:"combo",
										fieldLabel : '门店',
										width:300,
										id:"shop",
										emptyText: '请选择...',
										store:new Ext.data.JsonStore({
											autoLoad:true,
											proxy : {
												type : 'ajax',
												api : {
													read : _appctx + '/guideSupply/getShopList',
												},
												idParam : 'sid',
												reader : {
													type : 'json',
													root : 'result'
												}
											},
											fields: ["sid", "shopName"]
											
										}),
										valueField: 'sid',
										displayField:'shopName',
										hiddenName:'sid',
										triggerAction : 'all',
										name:'shop',
										mode:'local',
										allowBlank : false,
										value : shopName
									}
				                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'chestcardNum',
										width : 300,
										fieldLabel : '胸卡编号',
										allowBlank : false,
										value : chestcardShoprule
									}
			                    ]
			                }
			                ,{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'userName',
										width : 300,
										fieldLabel : '用户名',
										allowBlank : false,
										value : userName
									}
			                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'password',
										width : 300,
										fieldLabel : '密码',
										allowBlank : false,
										value : mdpassword
									}
			                    ]
			                }
						]
				});
	},

	cancel : function() {
		this.close();
	},
	update : function(){
			if (this.formPanel.getForm().isValid()) {

				this.formPanel.getForm().submit({
					success : function(form, action) {
						Ext.getCmp("guideLogininfoUpdateId").close();
						Ext.getCmp("guideLogininfoGridPanel").store.reload();
					},
					failure : function(form, action) {
						Ext.create('Ext.ux.window.Notification', {
							position: 'tr',
							useXAxis: true,
							cls: 'ux-notification-light',
							iconCls: 'ux-notification-icon-error',
							closable: true,
							title: '',
							html: '导购登录信息更新失败，请稍后重试！',
							slideInDuration: 800,
							slideBackDuration: 1500,
							autoCloseDelay: 4000,
							slideInAnimation: 'elasticIn',
							slideBackAnimation: 'elasticIn'
						}).show();
					}
				});
			}
	}
	
});
