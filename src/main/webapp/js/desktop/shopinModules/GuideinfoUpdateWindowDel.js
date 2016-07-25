/**
 * 修改导购基本信息
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.GuideinfoUpdateWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.tab.Panel'],
	formPanel : null,
	sid :null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		this.initComponents();
		var me = this;

		this.superclass.constructor.call(this, {
			title :'修改导购基本信息',
			height : 380,
			id:'guideinfoUpdateId',
			width  : 420,
//			constrain : true,
//			plain : true,
//			items : [ this.formPanel],
			xtype : 'tabpanel',
			activeTab : 0,
			bodyStyle : 'padding: 5px;',
			closeAction : 'hide',
			items : [ {
					xtype : 'tabpanel',
					activeTab : 0,
					bodyStyle : 'padding: 5px;',
					closeAction : 'hide',
					items : me.tabs
				} ],
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
		var spell = this.spell;
		var name = this.name;
		var mobile = this.mobile;
		var email = this.email;
		var guideBit = this.guideBit;
		
		this.tabs = [  
			{
				xtype : 'panel',
				closable : false,
				height : 380,
				width  : 420,
//				id : 'baseinfoId',
				title : '基本信息',
				items : [ 
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
										name : 'name',
										width : 300,
										fieldLabel : '姓名',
										value: name
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
										name : 'spell',
										width : 300,
										fieldLabel : '姓名拼音',
										value: spell
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
										fieldLabel:"性别",
										xtype:"combo",
										width : 300,
//										id:"sexId",
										store:new Ext.data.ArrayStore({
											fields: ['sexCode','value'],
											data : [
											['0',"男"],['1',"女"]
											]
										}),
										valueField: 'sexCode',
										displayField:'value',
										hiddenName:'sexCode',
										triggerAction : 'all',
										name:'sex',
										mode:'local'
//											,
//										value: spell
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
										name : 'phone',
										width : 300,
										fieldLabel : '电话'
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
										name : 'mobile',
										width : 300,
										fieldLabel : '手机号码',
										value : mobile
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
										name : 'address',
										width : 300,
										fieldLabel : '家庭住址'
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
										name : 'email',
										width : 300,
										fieldLabel : '邮箱',
										value : email
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
										fieldLabel:"是否是导购",
										xtype:"combo",
										width : 300,
//										id:"guideBitId",
										store:new Ext.data.ArrayStore({
											fields: ['guideBitCode','value'],
											data : [
											['0',"不是"],['1',"是"]
											]
										}),
										valueField: 'guideBitCode',
										displayField:'value',
										hiddenName:'guideBitCode',
										triggerAction : 'all',
										name:'guideBit',
										mode:'local'
									}
			                    ]
			                }
					]
			}, {
				xtype : 'panel',
				closable : false,
//				id :'mobileId',
				title : '证件信息',
				width  : 420,
				height : 380,
				items : [ 
							{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
					                	xtype: 'container',
					                    layout: 'hbox',
					                    height:5
					                },{
					                	xtype: 'container',
					                    layout: 'hbox',
					                    items: [
					                    	{
				                    			xtype: 'textfield',
												name : 'guideCard',
												width : 300,
												fieldLabel : '身份证号',
												allowBlank : false
											}
					                    ]
					                }]
					          },
					          {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'educationCartNum',
										width : 300,
										fieldLabel : '学历证编号'
									}
			                    ]
			                },
			                {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'kitasNum',
										width : 300,
										fieldLabel : '暂住证编号'
									}
			                    ]
			                },
			                {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'datefield',
										name : 'kitasValidityStart',
										width : 300,
										fieldLabel : '暂住证开始时间',
										format:'Y-m-d H:i:s'
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
		                    			xtype: 'datefield',
										name : 'kitasValidityEnd',
										width : 300,
										fieldLabel : '暂住证结束时间',
										format:'Y-m-d H:i:s'
									}
			                    ]
			                },
			                {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'healthCartNum',
										width : 300,
										fieldLabel : '健康证编号'
									}
			                    ]
			                },
			                {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'datefield',
										name : 'healthCartValidityStart',
										width : 300,
//										labelWidth:100,
										fieldLabel : '健康证开始时间',
										format:'Y-m-d H:i:s'
									}
			                    ]
			                },
			                {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'datefield',
										name : 'healthCartValidityEnd',
										width : 300,
										fieldLabel : '健康证结束时间',
										format:'Y-m-d H:i:s'
									}
			                    ]
			                }
					]
			},
			{
				xtype : 'panel',
				closable : false,
//				id :'mobileId',
				title : '补充信息',
				width  : 420,
				height : 380,
				items : [ 
							{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										fieldLabel:"是否已交押金",
										xtype:"combo",
										width : 300,
										id:"depositBitId",
										store:new Ext.data.ArrayStore({
											fields: ['depositBitCode','value'],
											data : [
											['0',"未交"],['1',"已交"]
											]
										}),
										valueField: 'depositBitCode',
										displayField:'value',
										hiddenName:'depositBitCode',
										triggerAction : 'all',
										name:'depositBit',
										mode:'local'
//											,
//										value : guideBit
									}
			                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [{
										xtype: 'textfield',
										name : 'depositNum',
										width : 300,
										fieldLabel : '押金单据编号'
//											,
//										value : email
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
										fieldLabel:"领取胸卡",
										xtype:"combo",
										width : 300,
										id:"chestBitId",
										store:new Ext.data.ArrayStore({
											fields: ['chestBitCode','value'],
											data : [
											['0',"未领取"],['1',"临时胸卡"],['2',"正式胸卡"]
											]
										}),
										valueField: 'chestBitCode',
										displayField:'value',
										hiddenName:'chestBitCode',
										triggerAction : 'all',
										name:'chestBit',
										mode:'local'
//											,
//										value : guideBit
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
		                    			xtype: 'datefield',
										name : 'entryTime',
										width : 300,
										fieldLabel : '入职时间'
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
		                    			xtype: 'datefield',
										name : 'departureTime',
										width : 300,
										fieldLabel : '离职时间'
									}
			                    ]
			                }
			                
			              
					]
			} ];
		
		
//		this.formPanel = Ext.create('Ext.form.Panel', {
//				url : _appctx + '/padGuideinfo/save',
//				layout : 'anchor',
//				defaults : {
//					anchor : '100%',
//					labelAlign : 'right'
//				},
//				height : 510,
//				id:"editForm",
//				bodyPadding: "15 15 15 15",
//				items: [
//	                	{
//	                    xtype: 'container',
//	                    layout: 'hbox',
//	                    items: [
//	                    	{
//	                    		xtype : 'textfield',
//								name : 'sid',
//								fieldLabel : 'SID',
//								hidden:true,
//								value: sid
//							},
//	                    	{
//                    			xtype: 'textfield',
//								name : 'name',
//								width : 300,
//								fieldLabel : '姓名',
//								value : name
//								}]
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                    xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//										xtype: 'textfield',
//										name : 'spell',
//										width : 300,
//										fieldLabel : '姓名拼音',
//										value : spell
//									}
//				                    ]
//			                },
//			                {
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                    xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//											xtype: 'textfield',
//										name : 'mobile',
//										width : 300,
//										fieldLabel : '手机号码',
//										value : mobile
//									}
//				                    ]
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                    xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [{
//										xtype: 'textfield',
//										name : 'email',
//										width : 300,
//										fieldLabel : '邮箱',
//										value : email
//									}
//			                    ]
//			                }
//			                ,{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//										fieldLabel:"是否是导购",
//										xtype:"combo",
//										width : 300,
//										id:"guideBitId",
//										store:new Ext.data.ArrayStore({
//											fields: ['guideBitCode','value'],
//											data : [
//											['0',"不是"],['1',"是"]
//											]
//										}),
//										valueField: 'guideBitCode',
//										displayField:'value',
//										hiddenName:'guideBitCode',
//										triggerAction : 'all',
//										name:'guideBit',
//										mode:'local',
//										value : guideBit
//									}
//			                    ]
//			                },
//			                {
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//										fieldLabel:"是否已交押金",
//										xtype:"combo",
//										width : 300,
//										id:"depositBitId",
//										store:new Ext.data.ArrayStore({
//											fields: ['depositBitCode','value'],
//											data : [
//											['0',"未交"],['1',"已交"]
//											]
//										}),
//										valueField: 'depositBitCode',
//										displayField:'value',
//										hiddenName:'depositBitCode',
//										triggerAction : 'all',
//										name:'depositBit',
//										mode:'local'
////											,
////										value : guideBit
//									}
//			                    ]
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                    xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [{
//										xtype: 'textfield',
//										name : 'depositNum',
//										width : 300,
//										fieldLabel : '押金单据编号'
////											,
////										value : email
//									}
//			                    ]
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//										fieldLabel:"领取胸卡",
//										xtype:"combo",
//										width : 300,
//										id:"chestBitId",
//										store:new Ext.data.ArrayStore({
//											fields: ['chestBitCode','value'],
//											data : [
//											['0',"未领取"],['1',"临时胸卡"],['2',"正式胸卡"]
//											]
//										}),
//										valueField: 'chestBitCode',
//										displayField:'value',
//										hiddenName:'chestBitCode',
//										triggerAction : 'all',
//										name:'chestBit',
//										mode:'local'
////											,
////										value : guideBit
//									}
//			                    ]
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//		                    			xtype: 'datefield',
//										name : 'entryTime',
//										width : 300,
//										fieldLabel : '入职时间'
//									}
//			                    ]
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//		                    			xtype: 'datefield',
//										name : 'departureTime',
//										width : 300,
//										fieldLabel : '离职时间'
//									}
//			                    ]
//			                }
//						]
//				});
	},

	cancel : function() {
		this.close();
	},
	update : function(){
			if (this.formPanel.getForm().isValid()) {

				this.formPanel.getForm().submit({
					success : function(form, action) {
						Ext.getCmp("guideinfoUpdateId").close();
						Ext.getCmp('guideManageId').getStore().load();
					},
					failure : function(form, action) {
						Ext.create('Ext.ux.window.Notification', {
							position: 'tr',
							useXAxis: true,
							cls: 'ux-notification-light',
							iconCls: 'ux-notification-icon-error',
							closable: true,
							title: '',
							html: '导购基本信息更新失败，请稍后重试！',
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
