/**
 * PAD调拨确认面板
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadAllocatePanel', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		this.initComponents();
		var me = this;
		padIds = config.padIds;
		this.superclass.constructor.call(this, {
			title :'PAD调拨确认',
			height : 310,
			id:'PadAllocatePanelId',
			width  : 420,
			constrain : true,
			plain : true,
			layout:"fit",
			modal: true,
			maximizable:true,
			items : [ this.formPanel],
			buttons :[
				{
					text : '保存',
					handler : function() {
						me.save();
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
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		this.formPanel = Ext.create('Ext.form.Panel', {
				url : _appctx + '/padBaseinfo/savePadAllocationInfo?userAuthName='+username+"&userAuthId="+userSid,
				layout : 'anchor',
				defaults : {
					anchor : '100%',
					labelAlign : 'right'
				},
				height : 310,
				id:"addForm",
				bodyPadding: "15 15 15 15",
				items: [
	                	{
	                    xtype: 'container',
	                    layout: 'hbox',
	                    items: [
	                    	{
	                    		xtype : 'textfield',
								name : 'padIds',
								fieldLabel : 'SID',
								hidden:true,
//								id:'padIds'
							},{
                    			xtype : 'textfield',
								name : 'padTargetShopName',
								hidden:true
							}
							]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [{//选择面板中的目的门店
				                	xtype:"combo",
									fieldLabel:'目的门店',
									labelAlign:"left",
									width :300,
									id:"panelPadTargetShopId",
									afterLabelTextTpl: required,
									allowBlank : false,
									store:new Ext.data.Store({
							        	autoLoad : true,
										proxy : {
											type : 'ajax',
											api : {
												read : _appctx + '/guideSupply/getShopsList2',
											},
											reader : {
												type: 'json',
							 					root: 'result'
											}
										},
										fields: ["sid", "shopName"]
							    	}),
									valueField: 'sid',//具体值
									displayField:"shopName",//显示的值
									name:'padTargetShop',
									triggerAction : 'all',
									typeAhead: true,
									queryMode: "local"
				                }]
			                }
					]
					});
	},
	cancel : function() {
		this.close();
	},
	save : function(){
			if (this.formPanel.getForm().isValid()) {
				var padTargetShop = Ext.getCmp("panelPadTargetShopId").getValue();
				this.formPanel.form.findField('padTargetShop').setValue(padTargetShop);
				var padTargetShopName = Ext.getCmp("panelPadTargetShopId").getRawValue();
				this.formPanel.form.findField('padTargetShopName').setValue(padTargetShopName);
				this.formPanel.form.findField('padIds').setValue(padIds);
				this.formPanel.getForm().submit({
					success : function(form, action) {
						if(action.result.success == "true"){
							Ext.Msg.alert('提示',"PAD调拨成功！");
							Ext.getCmp("PadAllocatePanelId").close();
							Ext.getCmp("PadAllocateView1").store.reload();
						}else if(action.result.success == "false"){
							if(action.result.errorCode == "10000"){
								Ext.Msg.alert('提示',action.result.memo);
							}else if(action.result.errorCode == "00000"){
								Ext.Msg.alert('提示',action.result.memo);
							}
							else{
								Ext.Msg.alert('提示',"pad调拨失败！");
							}
						}
					},
					failure : function(form, action) {
						Ext.create('Ext.ux.window.Notification', {
							position: 'tr',
							useXAxis: true,
							cls: 'ux-notification-light',
							iconCls: 'ux-notification-icon-error',
							closable: true,
							title: '',
							html: '添加PAD调拨信息失败，请稍后重试！',
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
