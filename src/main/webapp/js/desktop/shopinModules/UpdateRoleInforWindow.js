Ext.define("ShopinDesktop.UpdateRoleInforWindow", {
	extend: "Ext.window.Window",
	panel: null,
	id: "updateRoleInforWindow",
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
			title: "角色信息修改"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var record = this.record;
		
		this.panel = Ext.create("Ext.form.Panel", {
			bodyPadding: "25 15 25 15",
			id:'memberInforPanel',
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
				xtype: "hiddenfield",
				name: "sid",
				value: record.data.sid
			},{
				xtype: "textfield",
				fieldLabel: "角色名称",
				name: "roleName",
				value: record.data.roleName
			},{
				xtype: "textfield",
				fieldLabel: "角色编码",
				name: "roleCode",
				value: record.data.roleCode
			},{
				xtype: "combo",
				fieldLabel: "是否有效",
				editable:false,
				store: Ext.create("Ext.data.Store", {
					fields: ["show", "value"],
					data: [{
						"show": "有效",
						"value": "0"
					},{
						"show": "无效",
						"value": "1"
					}]
				}),
				queryMode: "local",
				displayField: "show",
				valueField: "value",
				value:record.data.delFlag,
				name: "delFlag",
			//	value: "1",
				allowBlank: false
			}],
			
			region: "center"
		});
	},
	saveEdit: function() {
		var thisWindow = this;
		var caller = this.caller;
		var editForm = this.panel.getForm();
		if (editForm.isValid()) {
			editForm.submit({
				url: _appctx + "/role/updateRole.json",
				success: function(form, action) {
					Ext.MessageBox.alert("提示", "保存成功", function() {
						Ext.getCmp('AdminInforView1').getStore().load();
						thisWindow.close();
						caller.pageBar.doRefresh();
					});
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