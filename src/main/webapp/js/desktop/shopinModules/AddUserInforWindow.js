Ext.define("ShopinDesktop.AddUserInforWindow", {
	extend: "Ext.window.Window",
	panel: null,
	id: "addUserInforWindow",
	baseUrl: "",
	record: null,
	icon: null,
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id : 'addUserInforWindowId',
			height: 320,
			width: 300,
			layout:"fit",
			modal: true,
			maximizable:true,
			constrain: true,
			layout: "border",
			items: [this.panel],
			title: "添加用户信息"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var record = this.record;
		
		this.panel = Ext.create("Ext.form.Panel", {
			bodyPadding: "25 15 25 15",
			id:'memberUserInforPanel',
			autoScroll:true,
			autoWidth:true,
			manageHeight:true,
			buttonAlign: "center",
			buttons: [{
				text: "保存",
				handler: function(button, e) {
					thisWindow.saveEdit();
				}
			},{
				text: "取消",
				handler: function(button, e) {
					thisWindow.cancel();
				}
			}],
			defaultType: "textfield",
			items: [
				{
					xtype: "textfield",
					fieldLabel: "登陆名",
					name: "userCode"
					
				},{
					inputType:'password', 
					fieldLabel: "密码",
					name: "userPssword"
					
				},{
					xtype: "textfield",
					fieldLabel: "真实姓名",
					name: "username"
					
				},{	
					xtype:"combo",
					fieldLabel : '用户所属门店',
					id:"shopinfo",
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
					allowBlank : false
				},{
					xtype: "combo",
					fieldLabel: "性别",
					editable:false,
					store: Ext.create("Ext.data.Store", {
						fields: ["show", "value"],
						data: [{
							"show": "男",
							"value": "0"
						},{
							"show": "女",
							"value": "1"
						}]
					}),
					queryMode: "local",
					displayField: "show",
					valueField: "value",
					name: "sex"
				}],
			
			region: "center"
		});
	},
	saveEdit: function() {
		var thisWindow = this;
		var caller = this.caller;
		var shopName = Ext.getCmp("shopinfo").getRawValue();
		var shopSid = Ext.getCmp("shopinfo").getValue();
		if (this.panel.getForm().isValid()) {
			this.panel.getForm().submit({
				url: _appctx + "/systemUser/saveSystemUser.json",
				params:{
					shopName:shopName,
					shopSid : shopSid
				},
				success: function(form, action) {
					var response = Ext.JSON.decode(action.response.responseText);
					if("true"== response.success){
						Ext.MessageBox.alert("提示", response.obj, function() {
						Ext.getCmp("addUserInforWindowId").close();
						Ext.getCmp("UserInforView1").store.reload();
						});
					}else{
						Ext.MessageBox.alert("提示",response.memo);
					}
					
				},
				failure: function(form, action) {
					switch (action.failureType) {
						case Ext.form.action.Action.CONNECT_FAILURE:
							Ext.MessageBox.alert("Error", "保存失败。请检查网络连接或确认服务器是否运行");
							break;
						case Ext.form.action.Action.SERVER_INVALID:
							var response = Ext.JSON.decode(action.response.responseText);
							Ext.MessageBox.alert("Error", "保存失败。" + response.errorMsg);
							break;
						case Ext.form.action.Action.LOAD_FAILURE:
							Ext.MessageBox.alert("Error", "保存失败。远程服务器返回了不能识别的数据格式，请联系管理员");
					}
				}
			});
		}
	},
	cancel: function() {
		this.close();
	}
});