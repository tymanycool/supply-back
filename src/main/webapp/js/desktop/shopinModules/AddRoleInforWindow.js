Ext.define("ShopinDesktop.AddRoleInforWindow", {
	extend: "Ext.window.Window",
	panel: null,
	id: "addRoleInforWindow",
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
			height: 260,
			width: 300,
			modal: true,
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
			id:'memberRoleInforPanel',
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
			items: [{
				xtype: "textfield",
				fieldLabel: "角色名称",
				name: "roleName",
			},{
				xtype: "textfield",
				fieldLabel: "角色编码",
				name: "roleCode",
				
			}],
			
			region: "center"
		});
//		if (this.sid != "") {
//			this.icon = this.baseUrl + "/images/edit.gif";
//			var form = this.panel.getForm();
//			form.load({
//				url: thisWindow.baseUrl + "/thirdChannel/detail.json",
//				method: "POST",
//				params: {
//					sid: this.sid,
//					_ts: Date.now()
//				},
//				success: function(form, action) {
//					form.findField("status").setValue("" + action.result.data.status);
//				},
//				failure: function(form, action) {
//					Ext.MessageBox.alert("Error", "Failed to loading channel detail.\r\nTry again or contect your adminstrator.", function() {
//						thisWindow.cancel();
//					});
//				}
//			});
//		} else {
//			this.icon = this.baseUrl + "/images/add.gif";
//		}
	},
	saveEdit: function() {
		var thisWindow = this;
		var caller = this.caller;
		var editForm = this.panel.getForm();
		if (editForm.isValid()) {
			editForm.submit({
				url: _appctx + "/role/saveRole.json",
				success: function(form, action) {
					var response = Ext.JSON.decode(action.response.responseText);
					if("true"== response.success){
						Ext.MessageBox.alert("提示", "保存成功", function() {
						Ext.getCmp('AdminInforView1').getStore().load();
						thisWindow.close();
						caller.pageBar.doRefresh();
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