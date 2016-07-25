/**
 * 个人中心
 */
Ext.define('ShopinDesktop.PersonalCenterWindow', {
	extend : 'Ext.ux.desktop.Module',
	id : 'personalCenterWindow',
	sm :null,
	formPanel : null,
	init : function(){
		this.launcher = {
			text: '个人中心',
			iconCls:'icon-grid'
		};
	},
	createWindow : function() {
		var me = this;
		this.initCmps(me);
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(me.id);
		if (!win) {
			win = desktop.createWindow({
				id : me.id,
				title : "个人中心",
				width : 350,
				height : 310,
				layout : 'fit',
				autoscroll: true,
				items : [ this.formPanel],
				buttons : [ 
						{
							text : '提交',
							id : 'guideinfoSave',
							handler : function() {
										me.save();
							}
						},{
							text : '取消',
							handler : function() {
								win.close();
							}
						}
					]
			});
		}
		return win;
	},
	
	initCmps : function(me) {
		this.formPanel = Ext.create('Ext.form.Panel', {
				url : _appctx + '/systemUser/saveSystemUser',
				defaults : {
					anchor : '100%',
					labelAlign : 'right'
				},
				layout : 'anchor',
				autoscroll: true,
				bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
				height : 550,
				bodyPadding: "15 15 15 15",
                items: [
	                    {
		                    xtype: 'container',
		                    layout: 'hbox',
		                    items: [
	                            {
			        				xtype: "hiddenfield",
			        				name: "sid",
			        				value: user.sid
			        			},{
			        				xtype: "textfield",
			        				fieldLabel: "真实姓名",
			        				name: "username",
			        				value: user.username
			        			}]
	                    },{
	                    	xtype: 'container',
		                    layout: 'hbox',
		                    height:5
		                },{
		                    xtype: 'container',
		                    layout: 'hbox',
		                    items: [{
		        				xtype: "textfield",
		        				fieldLabel: "登录名",
		        				name: "userCode",
		        				value: user.userCode
		        			}]
		                },
		                {
	                    	xtype: 'container',
		                    layout: 'hbox',
		                    height:5
		                },{
		                    xtype: 'container',
		                    layout: 'hbox',
		                    items: [{
		        				inputType:'password', 
		        				xtype: "textfield",
		        				fieldLabel: "密码",
		        				name: "userPssword",
//		        				value: user.userPssword
		        				value: "111111"
		                    }]
		                },{
	                    	xtype: 'container',
		                    layout: 'hbox',
		                    height:5
		                },{
		                    xtype: 'container',
		                    layout: 'hbox',
		                    items: [{	
		                    	xtype: 'textfield',
								name : 'shopname',
								fieldLabel : '用户所属门店',
								readOnly : true,
								value: user.shopName
		        			},{
		        				xtype: "hiddenfield",
		        				name: "shopSid",
		        				value: user.shopSid
		        			},{
		        				xtype: "hiddenfield",
		        				name: "validBit",
		        				value: "1"
		        			}]
                }]
			})
	},
	
	save : function(){
			if (this.formPanel.getForm().isValid()) {
				
				this.formPanel.getForm().submit({
					success : function(form, action) {
						if(action.result.success == "true"){
							Ext.MessageBox.alert('提示', action.result.obj);  
							Ext.getCmp("personalCenterWindow").close();
						}else if(action.result.success == "false"){
								Ext.Msg.alert('提示',action.result.memo);
						}
					},
					failure : function(form, action) {
						 Ext.Msg.alert('',action.result.obj);
				     	 return
					}
				});
			}
	}
});
