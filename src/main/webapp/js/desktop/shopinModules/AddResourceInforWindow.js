Ext.define("ShopinDesktop.AddResourceInforWindow", {
	extend: "Ext.window.Window",
	panel: null,
	id: "addResourceInforWindow",
	baseUrl: "",
	sid :null,
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
			title: "添加资源信息"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var sid = this.sid;
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
				xtype: "hiddenfield",
				fieldLabel: "SID",
				name: "parentSid",
				value:sid
			},{
				xtype: "textfield",
				fieldLabel: "资源名称",
				name: "rsName"
			},{
				xtype: "textfield",
				fieldLabel: "资源编码",
				name: "rsCode"
				
			},{
				xtype: "combo",
				fieldLabel: "叶子节点",
				editable:false,
				store: Ext.create("Ext.data.Store", {
					fields: ["show", "value"],
					data: [{
						"show": "是",
						"value": "1"
					},{
						"show": "否",
						"value": "0"
					}]
				}),
				queryMode: "local",
				displayField: "show",
				valueField: "value",
				name: "isLeaf",
			//	value: "1",
				allowBlank: false
			}
			
			],
			
			region: "center"
		});
	},
	saveEdit: function() {
		var thisWindow = this;
		var caller = this.caller;
		var editForm = this.panel.getForm();
		if (editForm.isValid()) {
			editForm.submit({
				url: _appctx + "/resources/saveResources.json",
				success: function(form, action) {
					var response = Ext.JSON.decode(action.response.responseText);
					if("true"== response.success){
						Ext.MessageBox.alert("提示", "保存成功", function() {
						Ext.getCmp('navTree').getStore().load();
						thisWindow.close();
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